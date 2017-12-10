package com.jonahseguin.absorb.view.timer;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.View;
import com.jonahseguin.absorb.view.ViewContext;
import lombok.Getter;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:44 PM
 */
@Getter
public class Timer implements Provider {

    private final View view;
    protected String format = "Timer: %d";
    protected boolean paused = false;
    protected long interval = 20L;
    protected double change = 1;
    protected TimerDirection direction = TimerDirection.ADD;
    protected double min = 0;
    protected double max = 10;
    protected double value = 0;
    protected Label label = new Label("");
    protected TimerEventHandler eventHandler = new TimerEventHandler(){};

    public Timer(View view) {
        this.view = view;
    }

    public void update() {
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
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPaused(boolean paused) {
        if (this.paused != paused) {
            if (paused) {
                eventHandler.onPause(this);
            }
            else {
                eventHandler.onResume(this);
            }
        }
        this.paused = paused;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setDirection(TimerDirection direction) {
        this.direction = direction;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setEventHandler(TimerEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public Label provide(ViewContext context) {
        return this.label.value(String.format(format, value)).timer(true);
    }

}
