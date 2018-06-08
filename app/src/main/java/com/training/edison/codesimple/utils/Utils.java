package com.training.edison.codesimple.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {
    public static final String BLOG_URL = "http://codesimple.farbox.com/";
    public static final String DATA_FORMAT = "yyyy-MM-dd";
    // img
    public static final String IMG_URL_REG = "<img.*src=(.*?)[^>]*?>";
    // img src
    public static final String IMG_SRC_REG = "(http|https:)(.*?)(png|jpg|gif)(.*?)(?=\")";

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
