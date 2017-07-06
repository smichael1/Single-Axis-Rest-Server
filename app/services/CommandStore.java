package services;

import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CommandStore {

	private static Map<Integer, Command> commands = new TreeMap<>();
	 

	static {
		
		CommandArg[] args1 = {};
        Command command = new Command("Init Cmd", "Busy", "Init", args1);
        commands.put(1, command);
        
        CommandArg[] args2 = {new CommandArg("position", "0")};
        command = new Command("Position Cmd", "Ready", "Position", args2);
        commands.put(2, command);

	}
	
    public static Command getCommand(int id) {
        return commands.get(id);
    }
 
    public static Set<Command> getAllCommands() {
    	// TODO: order these by index
        return new HashSet<Command>(commands.values());
    }
	
	
}
