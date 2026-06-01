package com.mg.Association_Flows.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionDto {
    private String message;
    private LocalDateTime timestamp;
    private String error;
    private Map<String, Object> errors;
    private int status;
}
