package com.jonahseguin.absorb.view.template;

import com.jonahseguin.absorb.scoreboard.Absorboard;
import com.jonahseguin.absorb.view.View;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonah on 4/11/2018.
 * Project: aBsorb
 *
 * @ 9:57 PM
 */
@Getter
public class ViewTemplater {

    private final Map<Integer, LineTemplate> templates = new HashMap<>();
    private final Absorboard absorboard;
    private final View view;
    private int lineIdCounter = 0;

    public ViewTemplater(Absorboard absorboard, View view) {
        this.absorboard = absorboard;
        this.view = view;
    }

    public LineTemplate registerTemplate(LineTemplate lineTemplate) {
        templates.put(lineTemplate.getId(), lineTemplate);
        return lineTemplate;
    }

    public LineTemplate getTemplate(int id) {
        return templates.get(id);
    }

    public int getFreeLineID() {
        return ++lineIdCounter;
    }

}
