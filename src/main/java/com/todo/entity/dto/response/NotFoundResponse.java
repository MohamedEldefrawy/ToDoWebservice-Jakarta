package com.todo.entity.dto.response;

import com.todo.entity.dto.response.FaildResponse;

public class NotFoundResponse implements FaildResponse {
    private String message;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
