package controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import actors.TelemetryActor;
import akka.actor.ActorRef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import services.ActorRefStore;
import services.CommandStore;
import akka.actor.ActorSystem;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class CommandController extends Controller {

	ActorSystem system;

	
	@Inject public CommandController(ActorSystem system) {
        this.system = system;
    }
	
    /**
     * An action that returns JSON for a list of commands.
     * The configuration in the <code>routes</code> file maps the 
     * <code>GET</code> request with a path of <code>/command</code> 
     * to this method.
     */
    public Result getCommands()
    {
        return ok(Json.toJson(CommandStore.getAllCommands()));
    }
    
    public Result getCommand(int id) {
    	 
    	// a comment to test project move
    	return ok(Json.toJson(CommandStore.getCommand(id)));
    }
    
    public Result runCommand() {
    	
    	JsonNode json = request().body().asJson();
    	  if(json == null) {
    	    return badRequest("Expecting Json data");
    	  } else {
    	    String setupConfig = json.findPath("commandSetupConfig").textValue();
    	    if(setupConfig == null) {
    	      return badRequest("Missing parameter [commandSetupConfig]");
    	    } else {
    	    	
    	    	// TODO: this is the integration point to send a command through the command service
    	    	
    	      return ok("Hello " + setupConfig);
    	    }
    	 }
    	  
    	/*
		ActorRef actorRef = system.actorOf(TelemetryActor.props());
		
		
		system.scheduler().schedule(
			    Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
			    Duration.create(327, TimeUnit.MILLISECONDS),     //Frequency 1/3 second
			    actorRef,
			    "tick",
			    system.dispatcher(),
			    null
			);
    	*/
    	
    }

}
