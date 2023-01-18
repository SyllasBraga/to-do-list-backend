package com.todolist.service.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Data
public class StandardResponse {

    private Instant timestamp;
    private int status;
    private String response;
    private Object object;

    public StandardResponse() {
        this.timestamp = Instant.now();
        this.status = HttpStatus.OK.value();
        this.response = "Requisição realizada com sucesso!";
    }
}
