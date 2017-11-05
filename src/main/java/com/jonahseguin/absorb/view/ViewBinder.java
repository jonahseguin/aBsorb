package com.jonahseguin.absorb.view;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.dependency.provider.StaticProvider;
import com.jonahseguin.absorb.view.line.LineSettings;
import com.jonahseguin.absorb.view.timer.Timer;
import com.jonahseguin.absorb.view.timer.TimerBuilder;
import lombok.Getter;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:27 PM
 */
@Getter
public class ViewBinder {

    private final View view;
    private final int line;
    private Provider provider = null;

    public ViewBinder(View view, int line) {
        this.view = view;
        this.line = line;
    }

    public ViewBinder to(Provider provider) {
        this.provider = provider;
        this.view.registerBinding(this);
        return this;
    }

    public ViewBinder to(String text) {
        this.provider = new StaticProvider(text);
        this.view.registerBinding(this);
        return this;
    }

    public TimerBuilder toTimer() {
        return new TimerBuilder(this);
    }

    public ViewBinder withSettings(LineSettings settings) {
        this.view.handler(line).setSettings(settings);
        return this;
    }

    public View finish() {
        return this.view;
    }

}
