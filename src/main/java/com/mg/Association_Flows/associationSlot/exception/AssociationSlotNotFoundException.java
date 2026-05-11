package com.mg.Association_Flows.associationSlot.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AssociationSlotNotFoundException extends BusinessException {
    public AssociationSlotNotFoundException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
}
