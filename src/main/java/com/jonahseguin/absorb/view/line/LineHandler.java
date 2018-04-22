package com.jonahseguin.absorb.view.line;

import com.jonahseguin.absorb.dependency.provider.EmptyProvider;
import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.util.AbsorbException;
import com.jonahseguin.absorb.util.EntryBuilder;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.ViewContext;
import com.jonahseguin.absorb.view.timer.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:50 PM
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LineHandler {

    private int lineNumber = -1;
    private Provider provider = new EmptyProvider();
    private LineSettings settings = new LineSettings();
    private EntryBuilder entryBuilder = null;
    private final ViewContext context;
    private boolean dynamicLineNumber = false;
    private Label currentValue = null;

    public boolean isVisible() {
        return entryBuilder != null && entryBuilder.getScore() != null && entryBuilder.getScore().isScoreSet();
    }

    public void update() {
        if (this.entryBuilder == null) {
            this.entryBuilder = new EntryBuilder(context.getAbsorboard().getScoreboard(), context.getAbsorboard().getObjective());
        }
        Label value = provider.provide(context);
        if (isDynamicLineNumber()) {
            context.getView().updateDynamicLines();
        }
        else {
            entryBuilder.setValue(lineNumber);
        }
        entryBuilder.update(value);
        this.currentValue = value;
    }

    public LineHandler setDynamicLineNumber(boolean dln) {
        this.dynamicLineNumber = dln;
        return this;
    }

    public void remove() {
        if (this.entryBuilder != null ) {
            this.entryBuilder.remove();
            this.entryBuilder = null;
            if (isDynamicLineNumber()) {
                context.getView().updateDynamicLines();
            }
        }
    }

    public void updateLineNumber(int i) {
        this.lineNumber = i;
        if (this.entryBuilder != null) {
            if (this.entryBuilder.getValue() != i) {
                this.entryBuilder.setValue(i);
            }
        }
    }

    public Timer getProviderAsTimer() {
        if (provider != null) {
            if (provider instanceof Timer) {
                return (Timer) provider;
            }
            else {
                throw new AbsorbException("Cannot cast this provider to Timer because it is not a timer");
            }
        }
        else {
            this.provider = new Timer(this.context.getView());
            return (Timer) this.provider;
        }
    }

}
