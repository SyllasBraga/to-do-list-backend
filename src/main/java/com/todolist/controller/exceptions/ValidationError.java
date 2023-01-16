package com.todolist.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant now, int value, String message, String requestURI, Object o) {
    }

    public void addError(String nomeCampo, String error){
        errors.add(new FieldMessage(nomeCampo, error));
    }
}
