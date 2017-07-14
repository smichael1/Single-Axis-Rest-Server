package actors;

import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;

import akka.actor.Props;
import akka.actor.UntypedActor;
import csw.services.ccs.BlockingAssemblyClient;
import csw.services.sequencer.SequencerEnv;
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
        	
        	if (command.getCommandSetupConfig().equals("Init")) {
        		
        		System.out.println("using compHelper to init assembly...");
        		
        		compHelper.init(assemblyClient, obsId);
        		
        		System.out.println("init command sent.");

        	} else if (command.getCommandSetupConfig().equals("Position")) {
        		
        		System.out.println("using compHelper to position assembly...");
        		
        		// TODO: type and units should be part of the command arg struct
        		Double position = new Double(command.getCommandArg("position").getArgValue());
        		
        		compHelper.position(assemblyClient, position, obsId);
        		
        		System.out.println("position command sent.");

        	}
        	
        }
        
    }
}
