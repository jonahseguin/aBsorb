package com.jonahseguin.absorb.dependency;

import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.ViewContext;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 4:41 PM
 */
public interface Provider {

    Label provide(ViewContext context);


}
