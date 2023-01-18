package com.todolist.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsuarioCreateValidation.class)
public @interface UsuarioCreateValid {

    String message() default "Valor inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
