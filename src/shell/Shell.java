package shell;

import file.FileManager;
import manager.Manager;
import shell.command.Command;
import shell.command.impl.*;

import java.io.IOException;

public class Shell {

    public static void main(String[] args) throws IOException {

        Manager manager = new Manager();

        String command;

        while(true) {
            if(!FileManager.fromFile)
                FileManager.write("shell>\n");
            command = FileManager.reader.readLine();
            if(command == null)
                break;
            if(command.length() == 0) {
                FileManager.write("\n");
                continue;
            }
            String[] parts;
            try {
                parts = command.split(" ");
            } catch (Exception e) {
                FileManager.write("error\n");
                continue;
            }
            String type = parts[0];
            if(type == "")
                continue;
            Commands cInfo = Commands.GetCommand(type);
            if(cInfo == null || parts.length != cInfo.argCount) {
                FileManager.write("error\n");
                continue;
            }
            cInfo.command.Execute(manager, parts);
        }

        FileManager.close();
    }

    enum Commands {

        INIT("init", new InitCommand(), 1),
        CREATE("cr", new CreateCommand(), 3),
        DESTROY("de", new DestroyCommand(), 2),
        REQUEST("req", new RequestCommand(), 3),
        RELEASE("rel", new ReleaseCommand(), 3),
        TIMEOUT("to", new TimeoutCommand(), 1);

        String name;
        Command command;
        int argCount;
        Commands(String name, Command command, int argCount) {
            this.name = name;
            this.command = command;
            this.argCount = argCount;
        }

        static Commands GetCommand(String input) {
            for (Commands c : Commands.values()) {
                if (c.name.equals(input))
                    return c;
            }
            return null;
        }
    }

}
