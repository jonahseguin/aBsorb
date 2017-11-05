package com.jonahseguin.absorb.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by Jonah on 11/5/2017.
 * Project: aBsorb
 *
 * @ 12:52 AM
 */
@Getter
@Setter
@AllArgsConstructor
public class Label {

    private String value;
    private boolean visible = true;
    private boolean timer = false;

    public Label(String value) {
        this.value = value;
    }

    public Label value(String value) {
        this.value = value;
        return this;
    }

    public Label visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Label timer(boolean timer) {
        this.timer = timer;
        return this;
    }

}
