package actors;

import akka.actor.*;
import play.libs.Json;
import services.ActorRefStore;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MyWebsocketActor extends UntypedActor {

    public static Props props(ActorRef out) {
        return Props.create(MyWebsocketActor.class, out);
    }

    private final ActorRef out;
    

    public MyWebsocketActor(ActorRef out) {
        this.out = out;
    }

    
    @Override
    public void preStart() throws Exception {
        super.preStart();

        ActorRefStore.actorRef = self();
        System.out.println("prestart, self = " + self());
    }
    
    public void onReceive(Object message) throws Exception {
    	
        if (message instanceof String) {
        	
        	String strMessage = (String)message;
	        out.tell(strMessage, self());
	        	

            //out.tell("I received your message: " + message, self());
        }
    }
}