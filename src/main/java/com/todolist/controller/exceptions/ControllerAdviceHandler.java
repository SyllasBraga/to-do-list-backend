package com.todolist.controller.exceptions;

import com.todolist.exceptions.CodStatusException;
import com.todolist.exceptions.ResourceNotFoundException;
import com.todolist.exceptions.TaskNotAcceptableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundExceptionResponseEntity(ResourceNotFoundException e,
                                                                                             HttpServletRequest request){
        StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(TaskNotAcceptableException.class)
    public ResponseEntity<StandardError> taskNotAcceptableException(TaskNotAcceptableException e,
                                                           HttpServletRequest request){
        StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE.value()).body(error);
    }

    @ExceptionHandler(CodStatusException.class)
    public ResponseEntity<StandardError> codStatusException(CodStatusException e,
                                                           HttpServletRequest request){
        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> argumentValidation(MethodArgumentNotValidException e,
                                                            HttpServletRequest request){
        ValidationError error = new ValidationError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
                request.getRequestURI());

        for (FieldError f :e.getFieldErrors()) {
                error.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(error);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationError> httpMessageNotReadableException(HttpMessageNotReadableException e,
                                                              HttpServletRequest request){
        ValidationError error = new ValidationError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
                request.getRequestURI());

        error.addError("statusTarefa",
                "Insira um status permitido, sendo eles: CONCLUIDA, VENCIDA e PENDENTE.");

        System.out.println(error.toString());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(error);
    }
}
