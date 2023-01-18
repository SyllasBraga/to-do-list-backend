package com.todolist.controller.exceptions;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

    private Instant timestamp;
    private int status;
    private String error;
    private String path;
}
