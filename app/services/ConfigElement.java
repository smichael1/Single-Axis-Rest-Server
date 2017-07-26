package services;

public class ConfigElement {
    
    String name;
    String value;

    
    public ConfigElement(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public ConfigElement() {}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

   
}
