package services;

// a total kludge to get access to the actorRef created from the Play LegacyWebsockets
import akka.actor.*;

public class ActorRefStore {

	public static ActorRef actorRef = null;
	
}
