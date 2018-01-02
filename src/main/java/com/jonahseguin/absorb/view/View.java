package com.jonahseguin.absorb.view;

import com.google.common.collect.Maps;
import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.line.LineHandler;
import com.jonahseguin.absorb.view.timer.Timer;
import com.jonahseguin.absorb.view.timer.pool.TimerPool;
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
    private final Map<Integer, LineHandler> lines = Maps.newConcurrentMap(); // <Line ID, Handler>
    private final ViewContext context;
    private TimerPool timerPool;

    public View(String name, Absorboard absorboard) {
        this.name = name;
        this.absorboard = absorboard;
        this.context = new ViewContext(absorboard, this, absorboard.getPlayer());
        this.timerPool = DEFAULT_TIMER_POOL;
    }

    public boolean isActive() {
        if (absorboard == null || absorboard.getActiveView() == null) {
            return false;
        }
        return absorboard.getActiveView().getName().equals(this.name);
    }

    public ViewContext createContext(int line) {
        return new ViewContext(this.context.getAbsorboard(), this, this.context.getPlayer())
                .setLine(line);
    }

    public ViewContext getContext(int lineID) {
        return handler(lineID).getContext();
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

    /**
     * Get a LineHandler by it's ID
     * If one does not exist, one is created, with the line number being set to the provided lineID param by default.
     * @param lineID The line ID number; *doesn't have to match the actual line number of the score*
     * @return the Handler
     */
    public LineHandler handler(int lineID) {
        if (lines.containsKey(lineID)) {
            return lines.get(lineID);
        }
        LineHandler lineHandler = new LineHandler(createContext(lineID));
        lineHandler.setLineNumber(lineID);
        lines.put(lineID, lineHandler);
        return lineHandler;
    }

    public boolean hasHandler(int lineID) {
        return lines.containsKey(lineID);
    }

    public ViewBinder bind(int lineID) {
        return new ViewBinder(this, lineID);
    }

    public void registerBinding(ViewBinder binder) {
        this.handler(binder.getLineID()).setProvider(binder.getProvider());
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

    // To be called usually internally by aBsorb (LineHandler)
    public int getDynamicLineNumber(LineHandler lineHandler) {
        int max = 0;
        for (LineHandler handler : this.lines.values()) {
            if (!handler.equals(lineHandler)) {
                if (handler.isVisible()) {
                    if (handler.getEntryBuilder().getValue() > max) {
                        max = handler.getEntryBuilder().getValue();
                    }
                }
            }
        }
        return max + 1;
    }

    public boolean isLineNumberRegistered(int number, LineHandler otherHandler) {
        for (LineHandler lineHandler : this.lines.values()) {
            if (lineHandler.equals(otherHandler)) continue;
            if (lineHandler.getEntryBuilder().getValue() == number) {
                return true;
            }
        }
        return false;
    }

    public void updateDynamicLines() {
        int i = 1;
        for(LineHandler lineHandler : this.lines.values()) {
            if (lineHandler.getCurrentValue() != null && lineHandler.getCurrentValue().isVisible() && lineHandler.isDynamicLineNumber()) {
                while (isLineNumberRegistered(i, lineHandler)) {
                    i++;
                }
                lineHandler.updateLineNumber(i);
            }
        }
    }

    public void clear() {
        this.lines.clear();
    }

}
