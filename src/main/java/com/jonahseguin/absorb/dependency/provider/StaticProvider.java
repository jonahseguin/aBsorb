package com.jonahseguin.absorb.dependency.provider;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.ViewContext;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 5:03 PM
 */
public class StaticProvider implements Provider {

    private final Label label;

    public StaticProvider(String value) {
        this.label = new Label(value);
    }

    @Override
    public Label provide(ViewContext context) {
        return label;
    }
}
