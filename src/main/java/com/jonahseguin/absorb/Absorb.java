package com.jonahseguin.absorb;

import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.line.LineSettings;
import com.jonahseguin.absorb.view.timer.Timer;
import com.jonahseguin.absorb.view.timer.TimerDirection;
import com.jonahseguin.absorb.view.timer.TimerEventHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 2:53 PM
 */
public class Absorb extends JavaPlugin {


    public Absorboard exampleBoard() {

        Absorboard absorboard = new Absorboard(this, Bukkit.getPlayerExact("Shawckz"), "My Scoreboard", true);

        // .view() uses the 'default' view; or the first found view if the default does not exist (otherwise it creates the default)
        absorboard.view().bind(4).to("Hello there");

        absorboard.view().bind(3).to(context -> {
            Player player = context.getPlayer();
            return new Label("Your name: " + player.getName());
        });

        // Separate view that can be toggled between
        absorboard.newView("general")
                .bind(2).to(context -> new Label("Level: " + context.getPlayer().getLevel()))
                    .withSettings(new LineSettings(true))
                    .finish()
                .getContext().getView()

                .bind(1).to(context -> new Label("Gamemode: " + context.getPlayer().getGameMode().toString()))
                    .withSettings(new LineSettings(true))
                    .finish();

        // Timer example
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

        // Can switch between views seamlessly
        absorboard.activate("default");

        absorboard.activate("general");

        absorboard.activate("timer");

        return absorboard;
    }

    @Override
    public void onEnable() {
        // This library can also be used as a plugin for easy dependency access and no need for shading
    }

    @Override
    public void onDisable() {

    }

}
