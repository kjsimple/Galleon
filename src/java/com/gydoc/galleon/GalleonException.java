package com.gydoc.galleon;

/**
 *
 */
public class GalleonException extends RuntimeException {

    private String errorCode;

    public GalleonException() {
        this(Constant.ERROR_COMMON_UNKNOWN);
    }

    public GalleonException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
