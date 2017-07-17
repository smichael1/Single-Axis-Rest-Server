package services;

import java.util.ArrayList;
import java.util.List;


public class CommandStore {

	private static List<Command> commands = new ArrayList<Command>();
	 

	static {
		
		CommandArg[] args1 = {};
        Command command = new Command("Init Cmd", "Ready", "Init", args1);
        commands.add(command);
        
        CommandArg[] args2 = {new CommandArg("position", "0")};
        command = new Command("Position Cmd", "Ready", "Position", args2);
        commands.add(command);

	}
 
    public static List<Command> getAllCommands() {
        return commands;
    }
	
	
}
