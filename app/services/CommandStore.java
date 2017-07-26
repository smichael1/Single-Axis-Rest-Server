package services;

import java.util.ArrayList;
import java.util.List;


public class CommandStore {

	private static List<Command> commands = new ArrayList<Command>();
	private static List<ConfigElement> configs = new ArrayList<ConfigElement>();
	 

	static {
		
		CommandArg[] args1 = {};
        Command command = new Command("Init Cmd", "Ready", "Init", args1);
        commands.add(command);
        
        CommandArg[] args2 = {new CommandArg("position", "0")};
        command = new Command("Position Cmd", "Ready", "Position", args2);
        commands.add(command);

	}
 
	static {
		
		configs.add(new ConfigElement("positionScale", "8.0"));
		configs.add(new ConfigElement("stageZero", "90.0"));
		configs.add(new ConfigElement("minStageEncoder", "225"));
		configs.add(new ConfigElement("minEncoderLimit", "200"));
		configs.add(new ConfigElement("maxEncoderLimit", "1200"));

	}
	
	
    public static List<Command> getAllCommands() {
        return commands;
    }
	
    public static List<ConfigElement> getAllConfigs() {
    	return configs;
    }
	
}
