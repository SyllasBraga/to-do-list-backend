package com.todolist.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError{

    private Instant timestamp;
    private int status;
    private String error;
    private String path;
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, int status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public void addError(String nomeCampo, String error){
        errors.add(new FieldMessage(nomeCampo, error));
    }
}
