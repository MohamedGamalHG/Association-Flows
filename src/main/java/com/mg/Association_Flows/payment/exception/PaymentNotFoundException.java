package com.mg.Association_Flows.payment.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PaymentNotFoundException extends BusinessException {
    public PaymentNotFoundException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
    public PaymentNotFoundException(String message, String error) {
        super(message, error);
    }
}
