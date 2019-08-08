package shell.command.impl;

import manager.Manager;
import shell.command.Command;

public class TimeoutCommand implements Command {
    @Override
    public void Execute(Manager manager, String args[]) {
        manager.Timeout();
    }
}