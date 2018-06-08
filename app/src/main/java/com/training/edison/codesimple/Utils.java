package com.training.edison.codesimple;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {
    public static final String BLOG_URL = "http://codesimple.farbox.com/";
    public static final String DATA_FORMAT = "yyyy-MM-dd";

    public static String formatDate(String time) {
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        SimpleDateFormat transToDate = new SimpleDateFormat(DATA_FORMAT, Locale.ENGLISH);
        String localDate = null;
        try {
            localDate = dateFormat.format(transToDate.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }
}
