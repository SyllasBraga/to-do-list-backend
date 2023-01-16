package com.todolist.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class StandardError {

    private Instant timestamp;
    private int status;
    private String error;
    private String path;
}
