package com.jonahseguin.absorb.view;

import com.google.common.collect.Maps;
import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.line.LineHandler;
import com.jonahseguin.absorb.view.timer.Timer;
import com.jonahseguin.absorb.view.timer.TimerPool;
import lombok.Getter;

import java.util.Map;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:23 PM
 */
@Getter
public class View {

    public static final TimerPool DEFAULT_TIMER_POOL = new TimerPool(20L);

    private final Absorboard absorboard;
    private final String name;
    private final Map<Integer, LineHandler> lines = Maps.newConcurrentMap();
    private final ViewContext context;
    private TimerPool timerPool;

    public View(String name, Absorboard absorboard) {
        this.name = name;
        this.absorboard = absorboard;
        this.context = new ViewContext(absorboard, this, absorboard.getPlayer());
        this.timerPool = DEFAULT_TIMER_POOL;
    }

    public boolean isActive() {
        return absorboard.getActiveView().getName().equals(this.name);
    }

    public ViewContext createContext(int line) {
        return new ViewContext(this.context.getAbsorboard(), this, this.context.getPlayer())
                .setLine(line);
    }

    public ViewContext getContext(int line) {
        return handler(line).getContext();
    }

    public View withTimerPool(TimerPool timerPool) {
        this.timerPool = timerPool;
        return this;
    }

    /**
     * To be called by the executing plugin
     * @return this
     */
    public View initTimerPool() {
        if (timerPool == null) {
            timerPool = DEFAULT_TIMER_POOL;
        }
        timerPool.register(absorboard, this);
        if (!timerPool.isRunning()) {
            timerPool.startTimerPool(absorboard.getPlugin());
        }
        return this;
    }

    public LineHandler handler(int line) {
        if (lines.containsKey(line)) {
            return lines.get(line);
        }
        LineHandler lineHandler = new LineHandler(createContext(line));
        lineHandler.setLineNumber(line);
        lines.put(line, lineHandler);
        return lineHandler;
    }

    public void changeLine(int oldLine, int newLine) {
        if (lines.containsKey(oldLine)) {
            LineHandler handler = handler(oldLine);
            handler.setLineNumber(newLine);
            lines.remove(oldLine);
            lines.put(newLine, handler);
        }
    }

    public ViewBinder bind(int line) {
        return new ViewBinder(this, line);
    }

    public void registerBinding(ViewBinder binder) {
        this.handler(binder.getLine()).setProvider(binder.getProvider());
    }

    public void render() {
        this.lines.forEach((integer, lineHandler) ->{
            if (lineHandler.getProvider() instanceof Timer) {
                Timer timer = lineHandler.getProviderAsTimer();
                timer.setRendered(true);
                timer.update();
                lineHandler.update();
            }
            else {
                lineHandler.update();
            }
        });
    }

    public void unrender() {
        this.lines.forEach((integer, lineHandler) -> {
            if (lineHandler.getProvider() instanceof Timer) {
                Timer timer = lineHandler.getProviderAsTimer();
                timer.setRendered(false);
                lineHandler.remove();
            }
            else {
                lineHandler.remove();
            }
        });
    }

    public int getDynamicLineNumber(LineHandler lineHandler) {
        int active = 0;
        for (LineHandler handler : this.lines.values()) {
            if (!handler.equals(lineHandler)) {
                if (handler.isVisible()) {
                    active++;
                }
            }
        }
        return active + 1;
    }

}
