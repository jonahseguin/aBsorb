# aBsorb
*user-control-oriented scoreboard library for use with the Spigot API*

# About
this project allows in-depth yet easy-to-use advanced scoreboard creation and manipulation for the Spigot API.

it is important to note that this is a "per-player" scoreboard library;  however updaters can be shared across multiple scoreboards
so this should not be an issue.

# Key Features
- Lines up to 48 characters
- Choose how often views update automatically
- Context/player-specific
- Multiple views means you can easily swap what your scoreboards look like

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

# How to use

aBsorb uses `View`s to display sets of information.  You can register different views and switch between them seamlessly.
We also use the `ViewProvider` interface to provide said sets of information for a specific view.

## The View Provider

Here is an example of how to implement a simple View Provider
```java
import com.jonahseguin.absorb.view.EntryBuilder;
import com.jonahseguin.absorb.view.ViewContext;
import com.jonahseguin.absorb.view.ViewProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ExampleViewProvider implements ViewProvider {

    @Override
    public String getTitle(ViewContext viewContext) {
        return ChatColor.GOLD + "Example Scoreboard";
    }

    @Override
    public List<String> getLines(ViewContext viewContext) {
        Player p = viewContext.getPlayer();
        EntryBuilder entryBuilder = new EntryBuilder();
        entryBuilder.blank();
        entryBuilder.next("&7Your Name: &b" + p.getName());
        entryBuilder.next("&7Gamemode: &b" + p.getGameMode().name());
        entryBuilder.blank();
        return entryBuilder.build();
    }

    @Override
    public void onUpdate(ViewContext context) {
        // Do something after it updates if you want
    }
}

```

## Register your scoreboard

To register a scoreboard, we need an instance of `Absorb`.  This instance is player-specific and handles their scoreboard.
For most cases, you can use one `ViewUpdater` instance for all scoreboards, and this will async. update scoreboards at the specified frequency:

```java
        Absorb absorb = new Absorb(plugin, player, true);
        View view = absorb.view("example");
        view.provider(new ExampleViewProvider());
        absorb.activate("example");
        absorb.show();
        
        ViewUpdater viewUpdater = new ViewUpdater(plugin, 2L); // Update every 2 ticks
        if (!viewUpdater.isRunning()) {
            viewUpdater.start();
        }
        viewUpdater.registerBoard(absorb);
```

