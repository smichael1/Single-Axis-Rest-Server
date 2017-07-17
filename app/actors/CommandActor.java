package actors;

import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import csw.services.ccs.BlockingAssemblyClient;
import csw.services.ccs.CommandStatus.CommandResult;
import csw.services.ccs.CommandStatus.OverallCommandStatus;
import csw.services.sequencer.SequencerEnv;
import play.libs.Json;
import services.ActorRefStore;
import services.Command;


public class CommandActor extends UntypedActor {

	
	String taName = "singleAxis";

	String obsId = "testObsId";

	BlockingAssemblyClient assemblyClient;
	SingleAxisComponentHelper compHelper;
	
	
    public static Props props() {
        return Props.create(CommandActor.class);
    }
    
    public CommandActor() {
    	
    	System.out.println("Resolving assembly...");
    	assemblyClient = SequencerEnv.resolveAssembly(taName);
		System.out.println("Assembly resolved.");

		String componentPrefix = "org.tmt.aps.ics.singleAxis";
		compHelper = new SingleAxisComponentHelper(componentPrefix);

	
    }
  
	
    public void onReceive(Object message) throws Exception {
    	   	
        if (message instanceof Command) {
        	
   	    	// TODO: this is the integration point to send a command through the command service
        	 
        	Command command = (Command)message;
        	CommandResult commandResult = null;
        	
        	
           	ObjectNode node1 = Json.newObject();
        	node1.put("commandSetupConfig", command.getCommandSetupConfig());
        	node1.put("overallStatus", "Busy");

        	ActorRef websocketActor = ActorRefStore.actorRef;

    		websocketActor.tell(node1.toString(), self());
 
        	
    		System.out.println("commanding assembly to " + command.getCommandSetupConfig());
    		
        	
        	if (command.getCommandSetupConfig().equals("Init")) {
        		
       		commandResult = compHelper.init(assemblyClient, obsId);
        		
 
        	} else if (command.getCommandSetupConfig().equals("Position")) {
        		
         		// TODO: type and units should be part of the command arg struct
        		Double position = new Double(command.getCommandArg("position").getArgValue());
        		
        		commandResult = compHelper.position(assemblyClient, position, obsId);
        		
        	}
        	
       		// send result via websockets to client
    		OverallCommandStatus overall = commandResult.overall();
    		
        	ObjectNode node2 = Json.newObject();
        	node2.put("commandSetupConfig", command.getCommandSetupConfig());
        	node2.put("overallStatus", overall.toString());

     		websocketActor.tell(node2.toString(), self());
    		
    		System.out.println(command.getCommandSetupConfig() + " command completed: status = " + overall);

        	
        	
        	
        }
        
    }
}
