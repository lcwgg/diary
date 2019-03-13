package com.example.fruitsdiary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    private static final String SERVER_DATE_PATTERN = "yyyy-MM-dd";

    private static final DateFormat SERVER_DATE_FORMAT = new SimpleDateFormat(SERVER_DATE_PATTERN);

    private static final DateFormat APP_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.LONG);

    private DateUtils() {
    }

    public static String convertServerDateToAppDate(String serverDate) {
        Date date = getServerDate(serverDate);
        return getAppStringDate(date);
    }

    public static String getCurrentAppDate() {
        Date date = getCurrentDate();
        return getAppStringDate(date);
    }

    public static String getCurrentServerDate() {
        Date date = getCurrentDate();
        return SERVER_DATE_FORMAT.format(date);

    }

    private static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getServerDate(String serverDate) {
        try {
            return SERVER_DATE_FORMAT.parse(serverDate);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getAppStringDate(Date date){
        return APP_DATE_FORMAT.format(date);
    }
}
