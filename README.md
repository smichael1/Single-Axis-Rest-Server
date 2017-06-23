

# Play Java REST Starter

This is a starter application that shows the simplest possible REST implementation in Play using Java.

## Running

Run this using [sbt](http://www.scala-sbt.org/).  
```
sbt run
```

And then go to http://localhost:9000/users to see the JSON output.

## Controllers

One controller is used for the two routes:

- UserController.java:

  getUsers() - Returns a JSON list of User data objects.
  getUser(id) - Returns JSON for a single user data object.

# Single-Axis-Rest-Server
