package services;

import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CommandStore {

	private static Map<Integer, Command> commands = new TreeMap<>();
	 

	static {
		
        Command command = new Command("Init Cmd", "Busy", "Init");
        commands.put(1, command);
        command = new Command("Position Cmd", "Ready", "Position");
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
