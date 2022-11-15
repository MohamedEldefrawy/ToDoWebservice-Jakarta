package com.todo.entity.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface FaildResponse {
    void setMessage(String message);

}
