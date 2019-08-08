package shell.command.impl;

import manager.Manager;
import shell.command.Command;

public class CreateCommand implements Command {
    @Override
    public void Execute(Manager manager, String args[]) {
        String id = args[1];
        int priority = Integer.parseInt(args[2]);
        manager.Create(id, priority);
    }
}
