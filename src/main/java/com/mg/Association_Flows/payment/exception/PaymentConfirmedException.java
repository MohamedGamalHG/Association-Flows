package com.mg.Association_Flows.payment.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PaymentConfirmedException extends BusinessException {
    public PaymentConfirmedException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
    public PaymentConfirmedException(String message, String error) {
        super(message, error);
    }
}
