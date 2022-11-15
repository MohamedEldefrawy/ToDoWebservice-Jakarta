package com.todo.entity.dto;

public class FaildToDeleteResponse implements FaildResponse {
    String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
