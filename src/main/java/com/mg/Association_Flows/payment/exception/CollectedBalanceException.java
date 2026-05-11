package com.mg.Association_Flows.payment.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CollectedBalanceException extends BusinessException {
    public CollectedBalanceException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
    public CollectedBalanceException(String message, String error) {
        super(message, error);
    }
}
