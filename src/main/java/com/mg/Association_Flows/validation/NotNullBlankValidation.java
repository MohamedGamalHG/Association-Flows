package com.mg.Association_Flows.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotNull
@NotBlank
public @interface NotNullBlankValidation {

    String message() default "Field Should not be null or empty";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
