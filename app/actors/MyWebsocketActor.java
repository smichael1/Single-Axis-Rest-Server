package actors;

import services.ActorRefStore;
import akka.actor.*;


public class MyWebsocketActor extends UntypedAbstractActor {

    public static Props props(ActorRef out) {
    
        return Props.create(MyWebsocketActor.class, out);
    }   

    
    private final ActorRef out;

    public MyWebsocketActor(ActorRef out) {
    	
    	ActorRefStore.actorRef = self();  // hack to get most recent websocket actor in case of failure/restart
    	
        this.out = out;
        
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
        	
        	
        	String strMessage = (String)message;
	        out.tell(strMessage, self());

        }
    }
}


    
     
  