package Archiver;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Alina on 12.04.2017.
 */
public class ZipFileManager {
    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }


    public void createZip(Path sourcePath) throws Exception {
        // If archive doesn't exist, I'll create it.
        Path zipDir = zipFile.getParent();
        if (Files.notExists(zipDir)) {
            Files.createDirectories(zipDir);
        }
        try(ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    addNewZipEntry(zout, file.getParent(), file.getFileName());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    throw new FileNotFoundException("Path is not found");
                }

            });
        }
    }
    public void addFile(Path sourcePath) throws Exception {
        // Check if achieve is existed
        if (!Files.isRegularFile(zipFile)) {
            throw new ZipException("Zip archieve is not exist");
        }

        // Create a temporary file
        Path tempFile = Files.createTempFile(null, null);
        List<Path> archivedFileList = new ArrayList<Path>();
        // Rewrite the contents of an existing archive to a temporary file
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(zipFile));
             ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(tempFile))) {
            ZipEntry zipEntry = null;
            while ((zipEntry = zin.getNextEntry()) != null) {
                zout.putNextEntry(zipEntry);
                copyData(zin, zout);
                archivedFileList.add(Paths.get(zipEntry.getName()));
                zin.closeEntry();
                zout.closeEntry();
            }
            // Archive new files
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(archivedFileList.contains(file.getFileName())) {
                    ConsoleHelper.writeMessage(String.format(
                            "The file %s has already been existed in the archive", file.toString()));
                }
                else {
                    addNewZipEntry(zout, file.getParent(), file.getFileName());
                    ConsoleHelper.writeMessage(String.format(
                            "The file %s was added to the archive", file.toString()));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                throw new FileNotFoundException();
            }

        });
        }
        // Moving the temporary file to the original
        Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }
    public void removeFile(Path sourcePath) throws Exception {
        // Check if achieve is existed
        if(!Files.isRegularFile(zipFile)) {
            throw new ZipException("Zip archieve is not exist");
        }
        List<Path> sourcePathList = new ArrayList<Path>();

        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                sourcePathList.add(file.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });

        // Create a temporary file
        Path tempFile = Files.createTempFile(null, null);

        // Rewrite the contents of an existing archive to a temporary file
        try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(zipFile));
        ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(tempFile))) {
            ZipEntry zipEntry = null;
            while ((zipEntry = zin.getNextEntry()) != null) {
                if (!sourcePathList.contains(Paths.get(zipEntry.getName()))) {
                    zout.putNextEntry(zipEntry);
                    copyData(zin, zout);
                    zin.closeEntry();
                    zout.closeEntry();
                } else {
                    ConsoleHelper.writeMessage(String.format(
                            "Файл %s удален из архива", zipEntry.getName()));
                }

            }
        }
        // Moving the temporary file to the original
        Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }
    public void extractAll(Path outputFolder) throws Exception{
        // Check if achieve is existed
        if(!Files.isRegularFile(zipFile)) {
            throw new ZipException("Zip archieve is not exist");
        }
        // Create an output directory if it does not exist
        if(!Files.notExists(outputFolder)) {
            Files.createDirectories(outputFolder);
        }
        try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(zipFile))){
            ZipEntry zipEntry = null;
            while((zipEntry = zin.getNextEntry())!= null) {
                Path fileFullName = outputFolder.resolve(Paths.get(zipEntry.getName()));
                // Create the required directories
                Path parentName = fileFullName.getParent();
                if(Files.notExists(parentName)) {
                    Files.createDirectories(parentName);
                }
                // Copy unzip data to output directory
                try (OutputStream out = Files.newOutputStream(fileFullName)) {
                    copyData(zin, out);
                }
                zin.closeEntry();
            }
        }
    }
    public List<FileProperties> getFilesList() throws Exception {
        if(!Files.isRegularFile(zipFile)) {
            throw new ZipException("Zip archieve is not exist");
        }
        List<FileProperties> result = new ArrayList<FileProperties>();
        try(ZipInputStream zin = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = null;
            while((zipEntry = zin.getNextEntry())!=null) {
                // The size and compressed size are not known until the item has been read
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                copyData(zin, baos);

                FileProperties fileProps = new FileProperties(zipEntry.getName(),
                        baos.size(), zipEntry.getCompressedSize(), zipEntry.getMethod());
                result.add(fileProps);
                zin.closeEntry();
            }
        }

        return result;
    }


    private void addNewZipEntry(ZipOutputStream zout, Path filePath, Path fileName) throws IOException{
         Path absolutePath = filePath.resolve(fileName);
         try(InputStream in = Files.newInputStream(absolutePath)) {
             ZipEntry zipEntry = new ZipEntry(fileName.toString());
             zout.putNextEntry(zipEntry);
             copyData(in, zout);
             zout.closeEntry();
         }

    }

    private void copyData(InputStream in, OutputStream out) throws IOException {
        byte [] buffer = new byte[8*1024];
        while(in.read(buffer)!=-1) {
            out.write(buffer, 0, buffer.length);
        }
    }

}
