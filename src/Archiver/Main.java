package Archiver;

import Archiver.command.Command;

import java.io.IOException;

/**
 * Created by Alina on 12.04.2017.
 */
public class Main {
    public static void main(String[] args) {
        Operation operation = null;
        do {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("\n" +
                        "You have not selected a file or selected the wrong file");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("An error has occurred." +
                        " Verify that the data entered is correct");
            }
        }
        while (operation!=Operation.EXIT);

    }
    public static Operation askOperation() throws IOException {
        ConsoleHelper.writeMessage("Select the operation:");
        ConsoleHelper.writeMessage(String.format("%d - Create the archive",
                Operation.CREATE.ordinal()));
        ConsoleHelper.writeMessage(String.format("%d - Add a file to the archive",
                Operation.ADD.ordinal()));
        ConsoleHelper.writeMessage(String.format("%d - Remove file from archive",
                Operation.REMOVE.ordinal()));
        ConsoleHelper.writeMessage(String.format("%d - Extract from the archive",
                Operation.EXTRACT.ordinal()));
        ConsoleHelper.writeMessage(String.format("%d - View archive contents",
                Operation.CONTENT.ordinal()));
        ConsoleHelper.writeMessage(String.format("%d - Exit",
                Operation.EXIT.ordinal()));
        return Operation.values()[ConsoleHelper.readInt()];
    }

}
