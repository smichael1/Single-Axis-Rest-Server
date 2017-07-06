package actors;

import akka.actor.*;
import services.ActorRefStore;


public class TelemetryActor extends UntypedActor {

    public static Props props() {
        return Props.create(TelemetryActor.class);
    }
    
	// set up telemetry
	ActorRef websocketActor = ActorRefStore.actorRef;

	
    public void onReceive(Object message) throws Exception {
    	
        if (message instanceof String) {
        	
        	// simulate values changing 
        	String sendString = "tick:" + (System.currentTimeMillis() % 1000) / 100.0;
        	
        	websocketActor.tell(sendString, self());
        }
        
    }
}
