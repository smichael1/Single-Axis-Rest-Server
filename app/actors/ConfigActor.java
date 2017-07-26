package actors;

import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Timeout;
import csw.services.ccs.BlockingAssemblyClient;
import csw.services.ccs.CommandStatus.CommandResult;
import csw.services.ccs.CommandStatus.OverallCommandStatus;
import csw.services.loc.LocationService;
import csw.services.sequencer.SequencerEnv;
import play.libs.Json;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import services.Command;


public class ConfigActor extends UntypedActor {

	
	String taName = "singleAxis";

	String obsId = "testObsId";
	
	ActorRef websocketActor;
	
    public static Props props() {
        return Props.create(ConfigActor.class);
    }
    
    public ConfigActor() {
    	
  
	
    }
  
	
    public void onReceive(Object message) throws Exception {
    	   	
    	

        
    }
    
    
    

}
