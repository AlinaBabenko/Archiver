package Archiver.command;

import Archiver.ConsoleHelper;
import Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alina on 14.04.2017.
 */
public class ZipExtractCommand extends ZipCommand {

    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Extracting files from the archive");
        ZipFileManager fileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the full name of the file or directory " +
                "to be extracted: ");
        Path sourcePath = Paths.get(ConsoleHelper.readString());
        fileManager.extractAll(sourcePath);
        ConsoleHelper.writeMessage("Extracting from archive is completed");
    }
}
