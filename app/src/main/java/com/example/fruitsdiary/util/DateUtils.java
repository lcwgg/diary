package com.example.fruitsdiary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.fruitsdiary.util.StringUtils.EMPTY_STRING;

public final class DateUtils {

    private static final String SERVER_DATE_PATTERN = "yyyy-MM-dd";

    private static final DateFormat SERVER_DATE_FORMAT = new SimpleDateFormat(SERVER_DATE_PATTERN);

    private static final DateFormat APP_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.LONG);

    private DateUtils() {
    }

    public static String convertServerDateToAppDate(String serverDate){
        try {
            Date date = SERVER_DATE_FORMAT.parse(serverDate);
            return APP_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            return EMPTY_STRING;
        }
    }

    public static String getCurrentAppDate(){
        Date date = getCurrentDate();
        return APP_DATE_FORMAT.format(date);
    }

    public static String getCurrentServerDate(){
        Date date = getCurrentDate();
        return SERVER_DATE_FORMAT.format(date);

    }

    private static final Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }
}
