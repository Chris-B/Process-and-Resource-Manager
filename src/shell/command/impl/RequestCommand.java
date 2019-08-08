package shell.command.impl;

import manager.Manager;
import shell.command.Command;

public class RequestCommand implements Command {
    @Override
    public void Execute(Manager manager, String args[]) {
        String id = args[1];
        int units = Integer.parseInt(args[2]);
        manager.Request(id, units);
    }
}