package controllers;

import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import javax.inject.Inject;
import actors.*;

public class WebsocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public WebsocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public WebSocket ws() {
        return WebSocket.Text.accept(request ->
                ActorFlow.actorRef(MyWebsocketActor::props,
                    actorSystem, materializer
                )
        );
    }
}