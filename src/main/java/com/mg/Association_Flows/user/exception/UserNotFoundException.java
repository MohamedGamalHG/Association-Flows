package com.mg.Association_Flows.user.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }

}
