package com.jonahseguin.absorb.view;

import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.line.LineHandler;
import lombok.Data;

import org.bukkit.entity.Player;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:40 PM
 */
@Data
public class ViewContext {

    private final Absorboard absorboard;
    private final View view;
    private final Player player;
    private int line = -1;

    public ViewContext setLine(int line) {
        this.line = line;
        return this;
    }

}
