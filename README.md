# aBsorb
*user-control-oriented scoreboard library for use with the Spigot API*

# About
this project allows in-depth yet easy-to-use advanced scoreboard creation and manipulation for the Spigot API.

it is important to note that this is a "per-player" scoreboard library;  however updaters can be shared across multiple scoreboards
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
    <version>2.0.0</version>
</dependency>
```
