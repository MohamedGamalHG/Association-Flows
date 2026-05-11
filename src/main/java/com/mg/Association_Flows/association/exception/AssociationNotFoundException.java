package com.mg.Association_Flows.association.exception;

import com.mg.Association_Flows.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AssociationNotFoundException extends BusinessException {
    public AssociationNotFoundException(String message, HttpStatus httpStatus, String error) {
        super(message, httpStatus, error);
    }
}
