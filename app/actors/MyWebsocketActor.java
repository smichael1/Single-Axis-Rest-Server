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
        	if (strMessage.startsWith("tick:")) {
        		String posString = strMessage.substring(strMessage.indexOf(":")+1);
        		float position = (float)(double)(new Double(posString));
        	        	
	        	ArrayNode array = Json.newArray();
	        	
	        	ObjectNode node1 = Json.newObject();
	        	node1.put("field", "commandState");
	        	node1.put("value", "Busy");
	        	node1.put("units", "");
	        	
	        	ObjectNode node2 = Json.newObject();
	        	node2.put("field", "moveState");
	        	node2.put("value", "Moving");
	        	node2.put("units", "");
	        	
	        	ObjectNode node3 = Json.newObject();
	        	node3.put("field", "stagePosition");
	        	node3.put("value", ""+position);
	        	node3.put("units", "meters");
	        	
	        	array.add(node1);
	        	array.add(node2);
	        	array.add(node3);
	        	    
	        	String result = array.toString();
	        	
	        	out.tell(result, self());
	        	
        	}

            //out.tell("I received your message: " + message, self());
        }
    }
}