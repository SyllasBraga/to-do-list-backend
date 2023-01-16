package com.todolist.exceptions;

public class TaskNotAcceptableException extends RuntimeException{

    public TaskNotAcceptableException(String msg){
        super(msg);
    }
}
