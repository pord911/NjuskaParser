package com.webpostparser.parserservice.parsehelpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Domagoj Pordan on 09.07.17..
 */
public class DateFormatHelper {
    private final static SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-mm-dd");
    private final static SimpleDateFormat formatPost = new SimpleDateFormat("dd.mm.yyyy");
    public static String getDateDatabaseFormat(String date) {
        String strDate = null;
        try {
             Date tempDate = formatPost.parse(date);
             strDate = formatDatabase.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return strDate;
        }
    }

    public static String getDatePostFormat(String date) {
        String strDate = null;
        try {
            Date tempDate = formatDatabase.parse(date);
            strDate = formatPost.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return strDate;
        }
    }
}
