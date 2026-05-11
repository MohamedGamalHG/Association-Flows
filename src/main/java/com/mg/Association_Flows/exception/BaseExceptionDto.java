package com.mg.Association_Flows.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionDto {
    private String message;
    private LocalDateTime timestamp;
    private String error;
    private int status;
}
