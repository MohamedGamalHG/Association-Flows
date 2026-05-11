package com.mg.Association_Flows.exception;

import com.mg.Association_Flows.association.exception.AssociationAssignedException;
import com.mg.Association_Flows.association.exception.AssociationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {AssociationAssignedException.class })
    public ResponseEntity<Map<String,Object>> handleAssociationAssigned(AssociationAssignedException exception) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg", initBaseResponse(exception,"Association Already Assigned",HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { AssociationNotFoundException.class })
    public ResponseEntity<Map<String,Object>> handleAssociationNotFound(AssociationNotFoundException exception) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg", initBaseResponse(exception,"Association Not Found",HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<BaseExceptionDto> handleException(Exception ex) {
        BaseExceptionDto response = initBaseResponse(ex,"Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    protected BaseExceptionDto initBaseResponse(Exception ex,String error,int status) {
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto();
        baseExceptionDto.setMessage(ex.getMessage());
        baseExceptionDto.setTimestamp(LocalDateTime.now());
        baseExceptionDto.setStatus(status);
        baseExceptionDto.setError(error);
        return baseExceptionDto;
    }
}
