package com.swehg.visitormanagement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kavindu
 */

public class DateGenerator {

    public Date setTime(int h, int m, int s, int ms) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,h);
        cal.set(Calendar.MINUTE,m);
        cal.set(Calendar.SECOND,s);
        cal.set(Calendar.MILLISECOND,ms);
        Date d = cal.getTime();
        return d;
    }

}
