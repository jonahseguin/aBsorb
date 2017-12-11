package com.jonahseguin.absorb.view.timer.pool;

import com.google.common.collect.Sets;
import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jonah on 11/5/2017.
 * Project: aBsorb
 *
 * @ 12:27 AM
 */
public class TimerPoolContext {

    private final Absorboard absorboard;
    private final Set<View> views = new HashSet<>();

    public TimerPoolContext(Absorboard absorboard) {
        this.absorboard = absorboard;
    }

    public Absorboard getAbsorboard() {
        return absorboard;
    }

    public Set<View> getViews() {
        return views;
    }
}
