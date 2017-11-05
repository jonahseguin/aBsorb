package com.jonahseguin.absorb.view.line;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:36 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LineSettings {

    private boolean update = false;

    public LineSettings update(boolean update) {
        this.update = update;
        return this;
    }

}
