package actors;

import java.util.Vector;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.*;
import akka.japi.Creator;
import akka.japi.pf.ReceiveBuilder;
import csw.util.config.ChoiceKey;
import csw.util.config.DoubleKey;
import csw.util.config.ChoiceItem;
import csw.util.config.IntKey;
import csw.util.config.IntItem;
import csw.util.config.DoubleItem;
import csw.util.config.Choice;
import csw.util.config.Events;
import javacsw.util.config.JItems;
import play.libs.Json;
import scala.collection.JavaConversions;
import scala.concurrent.Await;

import static javacsw.util.config.JItems.*;

import javax.inject.Inject;
import javax.inject.Named;

import akka.dispatch.*;
import akka.util.*;
import scala.concurrent.duration.Duration;
import scala.concurrent.Future;

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
	private String posUnits = "unknown";
	private String stagePosition = "unknown";
	private String stagePosUnits = "unknown";
	private String axisStateString = "unknown";
	
	
	public static final ChoiceKey cmdKey = ChoiceKey("cmd");
	public static final ChoiceKey moveKey = ChoiceKey("move");
	public static final IntKey posKey = IntKey("position");
	public static final DoubleKey stagePosKey = DoubleKey("stagePosition");
	public static final ChoiceKey axisStateKey = ChoiceKey("axisState");
	
    private ActorRef websocketActor;
	
	
    public static Props props() {
    	return Props.create(TelemetrySubscriberActor.class);
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
    
	
	
	public Receive createReceive() {

		return receiveBuilder().
				match(Events.SystemEvent.class, event -> {

					msgs.add(event);
					System.out.println("RECEIVED System " + event.info().source() + " event: " + event);
				}).
				match(Events.StatusEvent.class, event -> {
					
					System.out.println("RECEIVED Status " + event.info().source() + " event: " + event);
					
					websocketActor = resolveWebsocketActor();
					
					if (event.prefix().endsWith(".state")) {
						// update command state telemetry
						
						ChoiceItem cmdItem = jitem(event, cmdKey);
						ChoiceItem moveItem = jitem(event, moveKey);
			
						cmdState = jvalue(cmdItem).toString();
						moveState = jvalue(moveItem).toString();
						
						System.out.println("cmdState: " + cmdState.toString());
						System.out.println("moveState: " + moveState.toString());
						
						String sendString = generateJsonStringFromState();
						
						//ActorRef websocketActor = ActorRefStore.actorRef;
						websocketActor.tell(sendString, self());
						
						
					} else if (event.prefix().endsWith(".axis1State")) {
						// update axis telemetry

						IntItem posItem = jitem(event, posKey);
						DoubleItem stagePosItem = jitem(event, stagePosKey);
						
						ChoiceItem axisStateItem = jitem(event, axisStateKey);
			
						Choice axisState = jvalue(axisStateItem);
						
						axisStateString = axisState.toString();
						posUnits = posItem.units().toString();
						stagePosUnits = stagePosItem.units().toString();
					
						System.out.println("axisState: " + axisState.toString());
					
						
						position = jvalue(posItem).toString();
						stagePosition = jvalue(stagePosItem).toString();
										
						String sendString = generateJsonStringFromState();
						
						//ActorRef websocketActor = ActorRefStore.actorRef;
						websocketActor.tell(sendString, self());

					}
					

				}).
				match(GetResults.class, t -> sender().tell(new Results(msgs), self())).
				matchAny(t -> System.out.println("Unknown message received: " + t)).
				build();
		
	}
	
	
	private ActorRef resolveWebsocketActor() {
		
		if (websocketActor != null) {
			return websocketActor;
		}
		
		 try {
				Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	            // create an ActorSelection based on the path
	            ActorSelection sel = context().actorSelection("../*/flowActor*");
	            // check if a single actor exists at the path
	            Future<ActorRef> fut = sel.resolveOne(timeout);
	            ActorRef ref = Await.result(fut, timeout.duration());
	            System.out.println("ref = " + ref);
	          
	            return ref;
	        } catch (Exception e) {
	        	System.out.println(e);
	           return null;
	        }

	}
	
	
	private String generateJsonStringFromState() {
	        	
    	ArrayNode array = Json.newArray();
    	
    	ObjectNode node1 = Json.newObject();
    	node1.put("source", "SingleAxisAssembly");
    	node1.put("field", "commandState");
    	node1.put("value", cmdState);
    	node1.put("units", "");
    	
    	ObjectNode node2 = Json.newObject();
    	node2.put("source", "SingleAxisAssembly");
    	node2.put("field", "moveState");
    	node2.put("value", moveState);
    	node2.put("units", "");
    	
       	ObjectNode node3 = Json.newObject();
    	node3.put("source", "GalilHCD");
    	node3.put("field", "encPosition");
    	node3.put("value", position);
    	node3.put("units", posUnits);
    	
       	ObjectNode node4 = Json.newObject();
    	node4.put("source", "SingleAxisAssembly");
    	node4.put("field", "stagePosition");
    	node4.put("value", stagePosition);
    	node4.put("units", stagePosUnits);
    	
       	ObjectNode node5 = Json.newObject();
    	node5.put("source", "GalilHCD");
    	node5.put("field", "axisState");
    	node5.put("value", axisStateString);
    	node5.put("units", "");
    	
    	array.add(node1);
    	array.add(node2);
       	array.add(node4);
       	array.add(node3);
       	array.add(node5);
           	    
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
