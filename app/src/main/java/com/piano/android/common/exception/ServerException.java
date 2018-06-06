package com.piano.android.common.exception;

/**
 * @author 陈国权
 * @date 2017/6/20.
 */
public class ServerException extends Exception {
    static final long serialVersionUID = -7034897190745766939L;

    public ServerException() {
        super();
    }


    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServerException(Throwable cause) {
        super(cause);
    }


    protected ServerException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
