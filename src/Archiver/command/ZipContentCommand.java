package Archiver.command;

import Archiver.ConsoleHelper;
import Archiver.FileProperties;
import Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Alina on 14.04.2017.
 */
public class ZipContentCommand extends ZipCommand {

        @Override
        public void execute() throws Exception {
            ConsoleHelper.writeMessage("Viewing the contents of the archive");
            ZipFileManager fileManager = getZipFileManager();

            List<FileProperties> files = fileManager.getFilesList();
            ConsoleHelper.writeMessage("Archieve content:");
            for(FileProperties file: files) {
                ConsoleHelper.writeMessage(file.toString());
            }
            ConsoleHelper.writeMessage("Viewing the contents of the archive is completed");
        }
    }

