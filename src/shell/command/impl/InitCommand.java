package shell.command.impl;

import manager.Manager;
import shell.command.Command;

public class InitCommand implements Command {
    @Override
    public void Execute(Manager manager, String[] args) {
        manager.Init();
    }
}
