package de.scisertec.core.application.impl.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarHelper {

    public static Calendar stringToCalendar(String string) {
        try {
            Calendar date = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            date.setTime(sdf.parse(string));
            return date;
        } catch(Exception e) {
            return null;
        }
    }

}
