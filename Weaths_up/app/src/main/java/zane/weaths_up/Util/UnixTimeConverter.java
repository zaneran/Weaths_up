package zane.weaths_up.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zaneran on 10/7/2016.
 */
public class UnixTimeConverter {
    String result;

    public String HourConvert(String timeZone, String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        DateFormat dateFormat = new SimpleDateFormat("h:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        result = dateFormat.format(date);
        return result;

    }

    public String WeekdayConvert(String timeZone, String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        DateFormat dateFormat = new SimpleDateFormat("EEE");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        result = dateFormat.format(date);
        return result;
    }
}
