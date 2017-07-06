

# Single Axis REST Server

This is an implementation of a REST and Websockets server using Java/Play.  The server will provide a client with metadata of setupConfig commands and arguments available (can be used by the client to generate a user interface), accepts setupConifg command execution requests and provides telemetry to clients using WebSockets.

## Running

Run this using [sbt](http://www.scala-sbt.org/).  
```
sbt run
```

And then go to http://localhost:9000/users to see the JSON output.

## Controllers

Two controller are used:

- CommandController.java:

  getCommands() - Returns a JSON metadata for commands and arguments.
  runCommand() - Accepts a JSON POST of setupConfig command name and argument name-value pairs
  
- WebsocketController.java

  ws() - creates a websocket server that accepts connection requests.
  
## Actors

WebSocketActor - broadcasts received akka messages as JSON to websocket clients

TelemetryActor - listens for telemetry from CSW and sends akka messages to the websocketActor for publishing to clients

# Single-Axis-Rest-Server
