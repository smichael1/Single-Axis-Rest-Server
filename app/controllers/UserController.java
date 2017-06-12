package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserStore;

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
        return ok(Json.toJson(UserStore.getAllUsers()));
    }
    
    public Result getUser(int id) {
    	   	
    	return ok(Json.toJson(UserStore.getUser(id)));
    }
    


}
