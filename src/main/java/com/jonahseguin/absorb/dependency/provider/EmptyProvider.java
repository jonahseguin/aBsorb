package com.jonahseguin.absorb.dependency.provider;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.ViewContext;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:02 PM
 */
public class EmptyProvider implements Provider {

    @Override
    public Label provide(ViewContext context) {
        return new Label("");
    }
}
