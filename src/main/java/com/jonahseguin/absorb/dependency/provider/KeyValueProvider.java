package com.jonahseguin.absorb.dependency.provider;

import com.jonahseguin.absorb.dependency.Provider;
import com.jonahseguin.absorb.view.Label;
import com.jonahseguin.absorb.view.ViewContext;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jonah on 12/11/2017.
 * Project: aBsorb
 *
 * @ 1:35 PM
 */
@Getter
@Setter
public class KeyValueProvider implements Provider {

    private String key = "Key: ";
    private String value = "Value";
    private boolean visible = true;

    public KeyValueProvider(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Label provide(ViewContext context) {
        return new Label(key + value).visible(this.visible);
    }
}
