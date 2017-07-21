package modules;

import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;
import actors.*;

public class MyModule extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
        bindActor(MyWebsocketActor.class, "websocket-actor");
    }
}
