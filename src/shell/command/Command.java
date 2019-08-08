package shell.command;

import manager.Manager;

public interface Command {
    void Execute(Manager manager, String args[]);
}
