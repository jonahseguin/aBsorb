package com.jonahseguin.absorb.view.timer;

import com.jonahseguin.absorb.view.ViewBinder;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:49 PM
 */
public class TimerBuilder {

    private final ViewBinder viewBinder;
    private final Timer timer;

    public TimerBuilder(ViewBinder viewBinder) {
        this.viewBinder = viewBinder;
        this.timer = new Timer(viewBinder.getView());
    }

    public TimerBuilder format(String format) {
        this.timer.setFormat(format);
        return this;
    }

    public TimerBuilder interval(long interval) {
        this.timer.setInterval(interval);
        return this;
    }

    public TimerBuilder direction(TimerDirection direction) {
        this.timer.setDirection(direction);
        return this;
    }

    public TimerBuilder value(double value) {
        this.timer.setValue(value);
        return this;
    }

    public TimerBuilder paused(boolean paused) {
        this.timer.setPaused(paused);
        return this;
    }

    public TimerBuilder min(double min) {
        this.timer.setMin(min);
        return this;
    }

    public TimerBuilder max(double max) {
        this.timer.setMax(max);
        return this;
    }

    public TimerBuilder change(double change) {
        this.timer.setChange(change);
        return this;
    }

    public TimerBuilder eventHandler(TimerEventHandler timerEventHandler) {
        this.timer.setEventHandler(timerEventHandler);
        return this;
    }


    public ViewBinder build() {
        return viewBinder.to(timer);
    }

}
