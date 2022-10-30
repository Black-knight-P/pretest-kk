package com.pretest.dott.common.exception;

import com.pretest.dott.common.enums.IResponseCode;

public class CustomException extends RuntimeException {

    private final IResponseCode responseCode;

    public CustomException(final IResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public CustomException(final IResponseCode responseCode, final String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public IResponseCode getErrorCode() {
        return responseCode;
    }
}
