package Archiver.command;

import Archiver.ConsoleHelper;

/**
 * Created by Alina on 12.04.2017.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Bye-bye!");
    }
}
