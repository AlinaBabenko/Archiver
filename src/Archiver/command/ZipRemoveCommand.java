package Archiver.command;

import Archiver.ConsoleHelper;
import Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alina on 13.04.2017.
 */
public class ZipRemoveCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Removing a new file from the archive");
        ZipFileManager fileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the full name of the file or directory to be removed to archive: ");
        Path sourcePath = Paths.get(ConsoleHelper.readString());
        fileManager.removeFile(sourcePath);
        ConsoleHelper.writeMessage("Removing from archive is completed");
    }
}
