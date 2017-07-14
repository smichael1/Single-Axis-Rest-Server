package actors;

import java.util.Vector;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.*;
import akka.japi.Creator;
import akka.japi.pf.ReceiveBuilder;
import csw.util.config.ChoiceKey;
import csw.util.config.ChoiceItem;
import csw.util.config.IntKey;
import csw.util.config.IntItem;
import csw.util.config.Choice;
import csw.util.config.Events;
import javacsw.util.config.JItems;
import play.libs.Json;
import services.ActorRefStore;
import scala.collection.JavaConversions;
import static javacsw.util.config.JItems.*;


//import csw.services.ccs.BlockingAssemblyClient;
//import csw.services.sequencer.SequencerEnv;
//import csw.services.loc.LocationService;

public class TelemetrySubscriberActor extends AbstractActor {

    //public static Props props() {
    //    return Props.create(TelemetrySubscriberActor.class);
    //}
    
	private String cmdState = "unknown";
	private String moveState = "unknown";
	private String position = "unknown";
	
	
	public static final ChoiceKey cmdKey = ChoiceKey("cmd");
	public static final ChoiceKey moveKey = ChoiceKey("move");
	public static final IntKey posKey = IntKey("position");
	
   
    public static Props props() {
    	return Props.create(new Creator<TelemetrySubscriberActor>() {
    		private static final long serialVersionUID = 1L;
    		@Override
    		public TelemetrySubscriberActor create() throws Exception {
    			return new TelemetrySubscriberActor();
    		}
    	});
    }
    
	// --- Actor message classes ---
	static class GetResults {
	}
	
	static class Results {
	
    	public final Vector<Events.EventServiceEvent> msgs;
    	public Results(Vector<Events.EventServiceEvent> msgs) {
    		this.msgs = msgs;
    	}
	}
	
	Vector<Events.EventServiceEvent> msgs = new Vector<>();
    
	
	// set up telemetry
	ActorRef websocketActor = ActorRefStore.actorRef;

	
	public TelemetrySubscriberActor() throws Exception {
		
		receive(ReceiveBuilder.
			match(Events.SystemEvent.class, event -> {

				msgs.add(event);
				System.out.println("RECEIVED System " + event.info().source() + " event: " + event);
			}).
			match(Events.StatusEvent.class, event -> {
				
				System.out.println("RECEIVED Status " + event.info().source() + " event: " + event);
				
				
				if (event.prefix().endsWith(".state")) {
					// update command state telemetry
					
					ChoiceItem cmdItem = jitem(event, cmdKey);
					ChoiceItem moveItem = jitem(event, moveKey);
		
					cmdState = jvalue(cmdItem).toString();
					moveState = jvalue(moveItem).toString();
				
					System.out.println("cmdState: " + cmdState.toString());
					System.out.println("moveState: " + moveState.toString());
					
					String sendString = generateJsonStringFromState();
					
					websocketActor.tell(sendString, self());
					
					
				} else if (event.prefix().endsWith(".axis1State")) {
					// update axis telemetry

					IntItem posItem = jitem(event, posKey);
		
					position = jvalue(posItem).toString();
									
					String sendString = generateJsonStringFromState();
					
					websocketActor.tell(sendString, self());

				}
				
				
				

			}).
			match(GetResults.class, t -> sender().tell(new Results(msgs), self())).
			matchAny(t -> System.out.println("Unknown message received: " + t)).
			build());
	}
	
	
	private String generateJsonStringFromState() {
	        	
    	ArrayNode array = Json.newArray();
    	
    	ObjectNode node1 = Json.newObject();
    	node1.put("field", "commandState");
    	node1.put("value", cmdState);
    	node1.put("units", "");
    	
    	ObjectNode node2 = Json.newObject();
    	node2.put("field", "moveState");
    	node2.put("value", moveState);
    	node2.put("units", "");
    	
    	ObjectNode node3 = Json.newObject();
    	node3.put("field", "stagePosition");
    	node3.put("value", position);
    	node3.put("units", "meters");
    	
    	array.add(node1);
    	array.add(node2);
    	array.add(node3);
    	    
    	return array.toString();
	}
	
	
	
	/*
    public void onReceive(Object message) throws Exception {
    	   	
    	System.out.println("TelemetrySubscriber:: message = " + message);
    	
    	
        if (message instanceof String) {
        	
        	// simulate values changing 
        	String sendString = "tick:" + (System.currentTimeMillis() % 1000) / 100.0;
        	
        	websocketActor.tell(sendString, self());
        }
        
        
    }
	*/
}
