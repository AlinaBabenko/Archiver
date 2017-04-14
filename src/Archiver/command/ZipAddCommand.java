package Archiver.command;

import Archiver.ConsoleHelper;
import Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alina on 13.04.2017.
 */
public class ZipAddCommand extends ZipCommand {

    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Adding a new file to the archive");
        ZipFileManager fileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the full name of the file or directory to be added to archive: ");
        Path sourcePath = Paths.get(ConsoleHelper.readString());
        fileManager.addFile(sourcePath);
        ConsoleHelper.writeMessage("Addition to archive completed");
    }
}
