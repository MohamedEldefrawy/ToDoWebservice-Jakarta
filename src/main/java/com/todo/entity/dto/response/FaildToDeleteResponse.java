package com.todo.entity.dto.response;

public class FaildToDeleteResponse implements FaildResponse {
    String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
