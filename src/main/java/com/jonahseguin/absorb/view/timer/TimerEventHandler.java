package com.jonahseguin.absorb.view.timer;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 6:05 PM
 */
public abstract class TimerEventHandler {

    public void onReachMax(Timer timer) {

    }

    public void onReachMin(Timer timer) {

    }

    public double onUpdate(Timer timer) {
        return timer.getValue();
    }

    public void onPause(Timer timer) {

    }

    public void onResume(Timer timer) {

    }

}
