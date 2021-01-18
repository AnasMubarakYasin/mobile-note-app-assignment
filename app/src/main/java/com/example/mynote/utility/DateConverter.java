package com.example.mynote.utility;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static Date toDate(String dateString){
        return new Date(dateString);
    }

    public static String toTimestamp(Date date){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy");
        return dateTimeFormat.format(date);
    }
}
