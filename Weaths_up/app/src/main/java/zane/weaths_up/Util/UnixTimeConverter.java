package zane.weaths_up.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zaneran on 10/7/2016.
 */
public class UnixTimeConverter {
    String result;

    public String HourConvert(String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        result = new SimpleDateFormat("h:mm a").format(date);
        return result;

    }

    public String WeekdayConvert(String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        result = new SimpleDateFormat("EEE").format(date);
        return result;
    }
}
