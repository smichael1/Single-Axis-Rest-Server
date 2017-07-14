package services;

import java.util.HashMap;
import java.util.Map;

public class Command {
    
    String commandName;
    String commandState;
    String commandSetupConfig;
    CommandArg[] commandArgs;
    Map<String, CommandArg> argMap = new HashMap<String, CommandArg>();
    
    public Command(String commandName, String commandState, String commandSetupConfig, CommandArg[] commandArgs) {
        this.commandName = commandName;
        this.commandState = commandState;
        this.commandSetupConfig = commandSetupConfig;
        this.commandArgs = commandArgs;
        
        for (CommandArg commandArg : commandArgs) {
        	argMap.put(commandArg.getArgName(), commandArg);
        }
    }
    
    public Command() {}
    
    public String getCommandName() {
    	return commandName;
    }
    
    public String getCommandState() {
    	return commandState;
    }
    
    public String getCommandSetupConfig() {
    	return commandSetupConfig;
    }

	public CommandArg[] getCommandArgs() {
		return commandArgs;
	}
	
	public CommandArg getCommandArg(String key) {
		return argMap.get(key);
	}

}
