package services;

public class Command {
    
    String commandName;
    String commandState;
    String commandSetupConfig;
    
    public Command(String commandName, String commandState, String commandSetupConfig) {
        this.commandName = commandName;
        this.commandState = commandState;
        this.commandSetupConfig = commandSetupConfig;
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

}
