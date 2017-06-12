package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class UserStore {

	private static Map<Integer, User> users = new HashMap<>();
	 

	static {
		
        User user = new User("Scott Michaels", "5/27/1999");
        users.put(1, user);

	}
	
	
 
    public static User getUser(int id) {
        return users.get(id);
    }
 
    public static Set<User> getAllUsers() {
        return new HashSet<User>(users.values());
    }
	
	
}
