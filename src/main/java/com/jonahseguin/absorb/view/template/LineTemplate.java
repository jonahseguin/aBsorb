package com.jonahseguin.absorb.view.template;

import com.jonahseguin.absorb.view.View;
import com.jonahseguin.absorb.view.line.LineHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jonah on 4/11/2018.
 * Project: aBsorb
 *
 * @ 9:19 PM
 */
@Getter
@Setter
public abstract class LineTemplate {

    private final int id;
    private final View view;
    private LineHandler lineHandler = null;

    public LineTemplate(View view) {
        this(view.getTemplater().getFreeLineID(), view);
    }

    public LineTemplate(int id, View view) {
        this.id = id;
        this.view = view;

        this.lineHandler = this.bind(view);
    }

    public abstract LineHandler bind(View view);

}
