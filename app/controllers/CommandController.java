package controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;
import org.tmt.aps.ics.shared.AssemblyClientHelper;

import com.fasterxml.jackson.databind.JsonNode;

import actors.CommandActor;
import actors.TelemetrySubscriberActor;
import akka.actor.ActorRef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.Seq;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import services.ActorRefStore;
import services.CommandStore;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import csw.services.events.TelemetryService;
import csw.services.sequencer.SequencerEnv;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import services.Command;
import services.CommandArg;
import scala.collection.JavaConversions;
import javacsw.services.events.ITelemetryService;
import org.tmt.aps.ics.shared.AssemblyClientHelper;
import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class CommandController extends Controller {

	ActorSystem system;
	ActorRef commandActorRef;

	
	@Inject public CommandController(ActorSystem system) throws Exception {
        	this.system = system;
        
    		commandActorRef = system.actorOf(CommandActor.props());
    		
    		//TelemetryService telemetryService = SequencerEnv.getTelemetryService();

    		String componentPrefix = "org.tmt.aps.ics.singleAxis.*";
    		
    		//AssemblyClientHelper.getStatusEvents(telemetryService, componentPrefix);
    		  		
    		ActorRef telemetrySubscriber = system.actorOf(TelemetrySubscriberActor.props());
    		   		
    		Timeout timeout = new Timeout(FiniteDuration.create(15, TimeUnit.SECONDS));
    		ITelemetryService iTelemetryService =
    		ITelemetryService.getTelemetryService(ITelemetryService.defaultName, system, timeout).get(5, TimeUnit.SECONDS);
    		
    		iTelemetryService.subscribe(telemetrySubscriber, true, componentPrefix);

    		
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
    	    JsonNode argsNode = json.findPath("commandArgs");

    	    List<CommandArg> commandArgs = new ArrayList<CommandArg>();
    	    for (JsonNode commandArgNode : json.withArray("commandArgs")) {
    	        System.out.println("commandArg name -> " + commandArgNode.get("argName").asText());
    	        System.out.println("commandArg value -> " + commandArgNode.get("argValue").asText());
    	        CommandArg commandArg = new CommandArg(commandArgNode.get("argName").asText(), commandArgNode.get("argValue").asText());
    	        commandArgs.add(commandArg);
    	    }
    	    
    	    
    	    
    	    if(setupConfig == null) {
    	      return badRequest("Missing parameter [commandSetupConfig]");
    	    } else {
    	    	
    	    	    		
    			
    			Command command = new Command(null, null, setupConfig, commandArgs.toArray(new CommandArg[0]));
    			
    			commandActorRef.tell(command, null);
    			
    			/*
    			ActorRef telemetryActorRef = system.actorOf(TelemetrySubscriberActor.props());
   			
    			
    			system.scheduler().schedule(
    				    Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
    				    Duration.create(327, TimeUnit.MILLISECONDS),     //Frequency 1/3 second
    				    telemetryActorRef,
    				    "tick",
    				    system.dispatcher(),
    				    null
    				);
    	    	*/
    	    	
    	      return ok("Hello " + setupConfig);
    	    }
    	 }
    	  
    	

    	
    	
    }

}
