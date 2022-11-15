package com.todo.entity.dto.request;

import java.util.Date;

public class FindByDateRequest {
    private int mode;
    private Date date;


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
