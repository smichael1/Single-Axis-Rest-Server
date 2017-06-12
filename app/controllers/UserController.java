package controllers;

import play.mvc.*;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {

    /**
     * An action that returns JSON for a list of users.
     * The configuration in the <code>routes</code> file maps the 
     * <code>GET</code> request with a path of <code>/users</code> 
     * to this method.
     */
    public Result getUsers()
    {
        List<User> users = new ArrayList<User>();
        User user = new User("Scott Michaels", "5/27/1999");
        users.add(user);
        return ok(Json.toJson(users));
    }
    
    public Result getUser(int id) {
    	
    	// TODO: use the passed id to retrieve the correct record
    	User user = new User("Scott Michaels", "5/27/1999");
    	
    	return ok(Json.toJson(user));
    }
    
    private class User {
    
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

}
