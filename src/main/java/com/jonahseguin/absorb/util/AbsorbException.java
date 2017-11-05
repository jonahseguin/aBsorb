package com.jonahseguin.absorb.util;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 11:29 PM
 */
public class AbsorbException extends RuntimeException {

    public AbsorbException() {
    }

    public AbsorbException(String message) {
        super(message);
    }

    public AbsorbException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsorbException(Throwable cause) {
        super(cause);
    }

    public AbsorbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
