package com.jonahseguin.absorb.view.timer.formatters;

import com.jonahseguin.absorb.view.timer.TimerFormatter;

import java.text.DecimalFormat;

/**
 * Created by Jonah on 12/11/2017.
 * Project: aBsorb
 *
 * @ 12:09 PM
 */
public class DefaultTimerFormatter implements TimerFormatter {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0");

    @Override
    public String format(double time) {
        return DECIMAL_FORMAT.format(time);
    }
}
