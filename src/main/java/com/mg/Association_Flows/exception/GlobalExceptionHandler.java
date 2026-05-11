package com.mg.Association_Flows.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /*

    without make this two method we make just  parent Business class for exception,
    and we just use this method below handleBusinessException

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
    }*/

    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<BaseExceptionDto> handleBusinessException(BusinessException ex) {
        BaseExceptionDto response = initBaseResponse(ex,ex.getError(),ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
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
