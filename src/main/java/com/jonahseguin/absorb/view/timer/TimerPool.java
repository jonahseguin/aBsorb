package com.jonahseguin.absorb.view.timer;

import com.google.common.collect.Maps;
import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.util.AbsorbException;
import com.jonahseguin.absorb.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 6:31 PM
 */
@Getter
@Setter
public class TimerPool {

    private final Map<Absorboard, TimerPoolContext> contexts = Maps.newConcurrentMap();
    private final long interval;
    private BukkitTask bukkitTask = null;
    private boolean running = false;
    private boolean paused = false;

    public TimerPool(long interval) {
        this.interval = interval;
    }

    public void register(Absorboard absorboard, View view) {
        if (!contexts.containsKey(absorboard)) {
            contexts.put(absorboard, new TimerPoolContext(absorboard));
        }
        contexts.get(absorboard).getViews().add(view);
    }

    public final void startTimerPool(Plugin plugin) {
        if (!running) {
            bukkitTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::updateTimerPool, interval, interval);
            running = true;
        }
        else {
            throw new AbsorbException("TimerPool is already running");
        }
    }

    public final void stopTimerPool(Plugin plugin) {
        if (running) {
            if (bukkitTask != null) {
                bukkitTask.cancel();
                bukkitTask = null;
            }
            running = false;
        }
        else {
            throw new AbsorbException("TimerPool is not running");
        }
    }

    private void updateTimerPool() {
        if (paused) {
            return;
        }

        contexts.values().forEach(context -> context.getViews().forEach(view -> {
            view.getLines().values().forEach(lineHandler -> {
                Provider provider = lineHandler.getProvider();
                if (provider != null) {
                    if (provider instanceof Timer) {
                        Timer timer = (Timer) provider;
                        timer.update();
                        lineHandler.update();
                    }
                    else {
                        if (lineHandler.getSettings().isUpdate()) {
                            lineHandler.update();
                        }
                    }
                }
            });
        }));

    }

}
