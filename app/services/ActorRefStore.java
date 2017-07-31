package services;

import akka.actor.*;

public class ActorRefStore {

	// hack to get the most recent websocket handling actor in the case of a failure and restart
	public static ActorRef actorRef;
	
}
