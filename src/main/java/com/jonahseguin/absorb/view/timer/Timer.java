package com.jonahseguin.absorb.view.timer;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.View;
import com.jonahseguin.absorb.view.ViewContext;
import com.jonahseguin.absorb.view.timer.formatters.DefaultTimerFormatter;
import lombok.Getter;

import org.bukkit.ChatColor;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:44 PM
 */
@Getter
public class Timer implements Provider {

    protected boolean rendered = false;
    private final View view;
    protected String format = "Timer: %s";
    protected boolean paused = false;
    protected long interval = 20L;
    protected double change = 1;
    protected TimerDirection direction = TimerDirection.ADD;
    protected double min = 0;
    protected double max = 10;
    protected double value = 0;
    protected Label label = new Label("");
    protected TimerEventHandler eventHandler = new TimerEventHandler(){};
    protected TimerFormatter formatter = new DefaultTimerFormatter();

    public Timer(View view) {
        this.view = view;
    }

    public Timer hide() {
        this.getLabel().setVisible(false);
        return this;
    }

    public Timer show() {
        this.getLabel().setVisible(true);
        return this;
    }

    public boolean visible() {
        return this.getLabel().isVisible();
    }

    public Timer updateAndMakeVisible() {
        this.getLabel().setVisible(true);
        return this.update();
    }

    public Timer update() {
        if (this.direction.equals(TimerDirection.ADD)) {
            this.value += this.change;
        }
        else {
            this.value -= this.change;
        }
        if (value < min) {
            value = min;
            eventHandler.onReachMin(this);
        }
        else if (value > max) {
            value = max;
            eventHandler.onReachMax(this);
        }
        this.value = eventHandler.onUpdate(this);
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Timer setPaused(boolean paused) {
        if (this.paused != paused) {
            if (paused) {
                eventHandler.onPause(this);
            }
            else {
                eventHandler.onResume(this);
            }
        }
        this.paused = paused;
        return this;
    }

    public Timer setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public Timer setChange(double change) {
        this.change = change;
        return this;
    }

    public Timer setDirection(TimerDirection direction) {
        this.direction = direction;
        return this;
    }

    public Timer setMin(double min) {
        this.min = min;
        return this;
    }

    public Timer setMax(double max) {
        this.max = max;
        return this;
    }

    public Timer setValue(double value) {
        this.value = value;
        return this;
    }

    public Timer setFormatter(TimerFormatter formatter) {
        this.formatter = formatter;
        return this;
    }

    public void setEventHandler(TimerEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    @Override
    public Label provide(ViewContext context) {
        return this.label
                .value(ChatColor.translateAlternateColorCodes('&', String.format(format, formatter.format(value))))
                .timer(true);
    }

}
