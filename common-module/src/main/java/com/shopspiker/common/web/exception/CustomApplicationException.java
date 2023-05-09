package com.shopspiker.common.web.exception;

import org.springframework.http.HttpStatus;

public class CustomApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorDescription;

    public CustomApplicationException(HttpStatus httpStatus, String errorCode, String errorDescription) {
        super(errorDescription);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
