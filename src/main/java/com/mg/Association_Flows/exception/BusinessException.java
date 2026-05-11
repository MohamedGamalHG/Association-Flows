package com.mg.Association_Flows.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String error;

    protected BusinessException(String msg,HttpStatus httpStatus, String error) {
        super(msg);
        this.httpStatus = httpStatus;
        this.error = error;
    }

}
