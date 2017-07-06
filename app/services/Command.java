package services;

public class Command {
    
    String commandName;
    String commandState;
    String commandSetupConfig;
    CommandArg[] commandArgs;
    
    public Command(String commandName, String commandState, String commandSetupConfig, CommandArg[] commandArgs) {
        this.commandName = commandName;
        this.commandState = commandState;
        this.commandSetupConfig = commandSetupConfig;
        this.commandArgs = commandArgs;
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

}
