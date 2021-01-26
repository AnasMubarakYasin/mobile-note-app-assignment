package com.example.mynote.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConverterDate {

    private static String timePattern = "HH:mm:ss";
    private static String datePattern = "EEE, d MMM yyyy";
    private static String commonPattern = datePattern +" "+ timePattern;
    private static Locale locale = Locale.getDefault();

    public static Date stringToDate(String string) {
        try {
            return new SimpleDateFormat(commonPattern, locale).parse(string);
        } catch (ParseException parseException) {
            parseException.printStackTrace();

            return new Date();
        }
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat(commonPattern, locale)
                .format(date);
    }

    public static String toTimestamp(Date date){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy", locale);
        return dateTimeFormat.format(date);
    }

    public static String toTimeString(Date date) {
        return new SimpleDateFormat(timePattern, locale).format(date);
    }

    public static String toTimeString(int hour, int minute, int second) {
        return (hour > 9 ? hour : "0"+ hour) +":"+
                (minute > 9 ? minute : "0"+ minute) +":"+
                (second > 9 ? second : "0"+ second);
    }

    public static String toDateString(Date date) {
        return new SimpleDateFormat(datePattern, locale).format(date);
    }

    public static String getCommonPattern() {
        return commonPattern;
    }

    public static void setCommonPattern(String commonPattern) {
        ConverterDate.commonPattern = commonPattern;
    }

    public static String getTimePattern() {
        return timePattern;
    }

    public static void setTimePattern(String timePattern) {
        ConverterDate.timePattern = timePattern;
    }

    public static String getDatePattern() {
        return datePattern;
    }

    public static void setDatePattern(String datePattern) {
        ConverterDate.datePattern = datePattern;
    }
}
