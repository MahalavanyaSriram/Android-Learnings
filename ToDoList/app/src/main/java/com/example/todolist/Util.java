package com.example.todolist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* a. Assignment : #HW02.
* b. File Name : Util (com.example.todolist).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class Util {
    public static Date dateFormatter(String date) throws ParseException {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date convertedDate = simpleDateFormat.parse(date);
        return convertedDate;
    }
    public static String dateFormatter(Date date) throws ParseException {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String convertedDate = simpleDateFormat.format(date);
        return convertedDate;
    }
}
