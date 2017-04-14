package Archiver.command;

import Archiver.ConsoleHelper;
import Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alina on 12.04.2017.
 */
public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Creating the archive.");
        ZipFileManager fileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the full name of the file or directory to be archived:");
        Path sourcePath = Paths.get(ConsoleHelper.readString());
        fileManager.createZip(sourcePath);
        ConsoleHelper.writeMessage("Archive is created");
    }
}
