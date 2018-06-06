package com.piano.android.common.exception;

/**
 * @author 陈国权
 * @date  2017/6/20.
 */
public class EmptyException extends Exception {
    static final long serialVersionUID = -7034897190745766939L;

    public EmptyException() {
        super();
    }


    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(String message, Throwable cause) {
        super(message, cause);
    }


    public EmptyException(Throwable cause) {
        super(cause);
    }


    protected EmptyException(String message, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
