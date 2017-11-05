# aBsorb
inversion-of-control oriented scoreboard library for use with the Spigot API

# About
this project allows in-depth or simple easy-to-use simple or advanced scoreboard creation and manipulation for the Spigot API.
it follows the inversion-of-control (IoC) design pattern to make use of the binding of consumers and providers to allow for
fast & dynamic scoreboard values

it is important to note that this is a "per-player" scoreboard library;  however Timer Pools can be shared across multiple scoreboards
so this should not be an issue.

# Adding as a dependency
this project can be compiled into a plugin jar via maven + run on your server, or shaded into your plugin as a maven dependency.

I plan to add this project into it's own CI and public maven repo for easy dependency access in the future... but for now you will
have to do it yourself.

# API Usage
An generic example can be found in the src/main/java/com/jonahseguin/absorb/Absorb.java class in the 'exampleBoard()' method.

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
