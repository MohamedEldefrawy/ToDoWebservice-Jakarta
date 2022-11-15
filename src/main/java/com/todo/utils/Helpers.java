package com.todo.utils;

import com.todo.exception.NoDateAssignedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
    public static Date covertStringToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            throw new NoDateAssignedException("No date has been selected");
        }
    }

    public static String covertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return simpleDateFormat.format(date);
        } catch (NullPointerException ex) {
            throw new NoDateAssignedException("No date assigned");
        }
    }
}
