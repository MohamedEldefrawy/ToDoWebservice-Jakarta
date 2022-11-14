package com.todo.entity.dto;

public class NotFoundResponse {
    private final int status = 404;
    private String message;

    public NotFoundResponse(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
