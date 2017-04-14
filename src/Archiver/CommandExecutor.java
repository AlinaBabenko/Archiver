package Archiver;

import Archiver.command.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alina on 13.04.2017.
 */
public class CommandExecutor {
    private CommandExecutor() {
    }

    private static final Map<Operation, Command> commandMap = new HashMap<Operation, Command>();
    static {
        commandMap.put(Operation.CREATE, new ZipCreateCommand());
        commandMap.put(Operation.ADD, new ZipAddCommand());
        commandMap.put(Operation.REMOVE, new ZipRemoveCommand());
        commandMap.put(Operation.EXTRACT, new ZipExtractCommand());
        commandMap.put(Operation.CONTENT, new ZipContentCommand());
        commandMap.put(Operation.EXIT, new ExitCommand());
    }

    public static void execute(Operation operation) throws Exception {
         commandMap.get(operation).execute();
    }
}
