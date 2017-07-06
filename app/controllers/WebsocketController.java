package controllers;

import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;
import services.ActorRefStore;
import services.CommandStore;

import com.fasterxml.jackson.databind.JsonNode;

import actors.MyWebsocketActor;
import akka.actor.*;
import play.libs.F.*;
import play.mvc.WebSocket;
import play.mvc.LegacyWebSocket;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;





/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class WebsocketController extends Controller {


    
	public LegacyWebSocket<String> ws() {
		System.out.println("ws method called");
		
	    LegacyWebSocket websocket = WebSocket.withActor(MyWebsocketActor::props);
	    
	    return websocket;
	    
	    
	}
	

}
