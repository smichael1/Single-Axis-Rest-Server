package services;

public class User {
    
    String name;
    String dob;
    
    public User(String name, String dob) {
        this.name = name;
        this.dob = dob;
    }
    
    public User() {}
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

}
