package services;

public class CommandArg {
    
    String argName;
    String argValue;

    
    public CommandArg(String argName, String argValue) {
        this.argName = argName;
        this.argValue = argValue;
    }
    
    public CommandArg() {}

	public String getArgName() {
		return argName;
	}

	public String getArgValue() {
		return argValue;
	}

   
}
