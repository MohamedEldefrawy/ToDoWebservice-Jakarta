package com.todo.entity.dto.response;

public class FaildToUpdateResponse implements FaildResponse {
    private String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
