package com.piano.android.common.exception;

/**
 * @author 陈国权
 * @date  2017/6/20.
 * @describe token失效
 */
public class TokenInvalidException extends Exception {
    static final long serialVersionUID = -7034897190745766939L;

    public TokenInvalidException() {
        super();
    }


    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }


    public TokenInvalidException(Throwable cause) {
        super(cause);
    }


    protected TokenInvalidException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
