package com.example.personalapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(date);
        return format;
    }

    public static Date string2Date(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date=sdf.parse(s);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
