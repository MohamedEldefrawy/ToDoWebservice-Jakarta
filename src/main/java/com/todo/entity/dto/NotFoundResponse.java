package com.todo.entity.dto;

public class NotFoundResponse implements FaildResponse {
    private String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
