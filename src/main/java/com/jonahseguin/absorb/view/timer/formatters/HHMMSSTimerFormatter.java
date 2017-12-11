package com.jonahseguin.absorb.view.timer.formatters;

import com.jonahseguin.absorb.view.timer.TimerFormatter;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jonah on 12/11/2017.
 * Project: aBsorb
 *
 * @ 12:19 PM
 */
public class HHMMSSTimerFormatter implements TimerFormatter {

    @Override
    public String format(double time) {
        int millis = (int) Math.round(time);
        return  String.format("%02d:%02d:%02d",
                TimeUnit.SECONDS.toHours(millis),
                TimeUnit.SECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(millis)),
                TimeUnit.SECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(millis)));
    }
}
