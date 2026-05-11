package com.mg.Association_Flows.payment.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PayMonthlyAmountException extends BusinessException {
    public PayMonthlyAmountException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
}
