package com.mg.Association_Flows.association.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AssociationNotActiveException extends BusinessException {
    public AssociationNotActiveException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
}
