package com.jonahseguin.absorb.scoreboard;

import com.google.common.collect.Maps;
import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.dependency.provider.StaticProvider;
import com.jonahseguin.absorb.view.ViewContext;
import com.jonahseguin.absorb.view.line.LineHandler;
import com.jonahseguin.absorb.view.View;
import lombok.Getter;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:23 PM
 */
@Getter
public class Absorboard {

    private final Plugin plugin;
    private final Map<String, View> views = Maps.newConcurrentMap();
    private volatile View activeView = null;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Player player;

    public Absorboard(Plugin plugin, Player player, String title, boolean override) {
        this.plugin = plugin;
        this.player = player;
        if (override) {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(scoreboard);
        }
        else {
            if (player.getScoreboard() != null) {
                this.scoreboard = player.getScoreboard();
            }
            else {
                this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                player.setScoreboard(scoreboard);
            }
        }
        Objective obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (obj == null) {
            obj = scoreboard.registerNewObjective(player.getName(), "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName(title);
        }
        this.objective = obj;
    }

    public void setTitle(String title) {
        this.objective.setDisplayName(title);
    }

    public void update() {
        this.activeView.render();
    }

    public boolean isActive(String view) {
        return this.activeView != null && this.activeView.getName().equals(view);
    }

    public void activate(String viewName) {
        View view = getView(viewName);
        if (view != null) {
            this.activate(view);
        }
    }

    public void activate(View view) {
        if (this.activeView != null) {
            this.activeView.unrender();
        }
        this.activeView = view;
        this.activeView.render();
    }


    public void registerView(View view) {
        this.views.put(view.getName(), view);
    }

    public View getView(String view) {
        return views.get(view);
    }

    public View newView(String name) {
        View view = new View(name, this);
        this.registerView(view);
        return view;
    }

    public View view(String name) {
        View view = getView(name);
        if (view != null) {
            return view;
        }
        else {
            return newView(name);
        }
    }

    public View view() {
        if (this.activeView != null) {
            return this.activeView;
        }
        return this.views.values().stream().findFirst().orElse(this.newView("default"));
    }

}
