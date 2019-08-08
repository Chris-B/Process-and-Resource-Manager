package shell.command.impl;

import manager.Manager;
import shell.command.Command;

public class DestroyCommand implements Command {
    @Override
    public void Execute(Manager manager, String args[]) {
        String id = args[1];
        manager.Destroy(id);
    }
}
