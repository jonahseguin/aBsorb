# aBsorb
*inversion-of-control oriented scoreboard library for use with the Spigot API*


# About
this project allows in-depth or simple easy-to-use simple or advanced scoreboard creation and manipulation for the Spigot API.
it follows the inversion-of-control (IoC) design pattern to make use of the binding of consumers and providers to allow for
fast & dynamic scoreboard values

it is important to note that this is a "per-player" scoreboard library;  however Timer Pools can be shared across multiple scoreboards
so this should not be an issue.


# Adding as a dependency
this project can be compiled into a plugin jar via maven + run on your server, or shaded into your plugin as a maven dependency.


## Maven: pom.xml
Add this to your repositories:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
and this to your dependencies:
```xml
<dependency>
    <groupId>com.github.jonahseguin</groupId>
    <artifactId>aBsorb</artifactId>
    <version>0.1</version>
</dependency>
```


# How it works
A generic example can be found in the src/main/java/com/jonahseguin/absorb/Absorb.java class in the 'exampleBoard()' method.

Basically, the main scoreboard class is the Absorboard.  Within Absorboards, there can be unlimited 'View's of which represent an array
of lines.  Which view is active can be swapped seamlessly at any time.  Each "value"/"line"/"label" on a view is provided by a 
Provider, which is bound to that line via the bind() method within the View class.

The timer (as can be seen in the example method) is intuitive and can be made either complex or simple.  Of course all the code is
fairly abstract so it can be overriden and implemented with your own classes if you deem necessary.  However, using the event loop
and other features of the timers and providers, custom implementation should almost rarely be necessary.

You can create 'dynamic' labels or 'static' labels; which the provided default Provider implementations.  When you bind a line to a provider,
you can make a static one using the StaticProvider class, or a dynamic one using any implementation or just using the raw Provider class.

To update your labels (ex so it matches the current value of the context), you can manually call the update() method in a LineHandler, or
you can take advantage of Timer Pooling and set the "update" boolean within the LineSettings to true.  This will call the update method
every time your Timer Pool ticks, even for providers that are not timers as long as "update" is true.

That's the basic rundown.  Of course I plan to keep this update, add javadocs, etc.


# Examples

A few simple examples that can be found in the Absorb.java class within the `exampleBoard()` method

### Create an Absorboard for a player
```java
Absorboard absorboard = new Absorboard(this, Bukkit.getPlayerExact("Shawckz"), "My Scoreboard");
```

### Create a new view with dynamic line values
```java
absorboard.newView("general")
                .bind(2).to(context -> new Label("Level: " + context.getPlayer().getLevel()))
                    .withSettings(new LineSettings(true))
                    .finish()

                .bind(1).to(context -> new Label("Gamemode: " + context.getPlayer().getGameMode().toString()))
                    .withSettings(new LineSettings(true))
                    .finish();
```
Notice the view is named 'general'.  We can toggle between multiple views seamlessly, which will be shown at the bottom.

### Create a dynamic timer
```java
absorboard.newView("timer")
                .bind(1).toTimer()
                    .format("&cPVP Timer&7: &7%s")
                    .direction(TimerDirection.SUBTRACT)
                    .max(60)
                    .min(0)
                    .interval(2L)
                    .change(0.1) // Change value by 0.1 every 2 ticks (0.1 seconds)
                    .eventHandler(new TimerEventHandler() {
                        @Override
                        public void onReachMin(Timer timer) {
                            // Make the timer invisible
                            timer.getLabel().setVisible(false);
                        }

                    })
                    .build();
```
It is bound to line `1` under the view named "timer".  It subtracts down from 60 by 0.1 and stops at 0, and using our custom event handler implementation, it hides the line when it reaches 0.  This is updated every 2 ticks (0.1 seconds).
**The timer implementation is still a WIP and will be updated with features such as asynchronous updating and timer pooling in future versions.**

### Switch between views with ease
```java
absorboard.activate("general"); // Switch to our 'general' view

absorboard.activate("timer"); // Switch to our 'timer' view
```

And that's the basics of this very easy-to-use yet abstract and expandable scoreboard library.
There are lots of ways to customize your implementation, etc.
  Future updates will see more features.
