package zane.weaths_up.Util;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import zane.weaths_up.Preferences.SettingPreferences;

/**
 * Created by zaneran on 10/7/2016.
 */
public class UnixTimeConverter {

    //Different timezone convertion included.
    private Context context;
    private String result;
    private boolean is24Hours;
    private DateFormat dateFormat;

    public UnixTimeConverter(Context context){
        this.context = context;
    }

    public String HourConvert(String timeZone, String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        SettingPreferences settingPreferences = new SettingPreferences(context);
        if (settingPreferences.getTimePreferences()){
            dateFormat = new SimpleDateFormat("HH:mm");
        }else {
            dateFormat = new SimpleDateFormat("h:mm a");
        }
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        result = dateFormat.format(date);
        return result;
    }

    public String WeekdayConvert(String timeZone, String UnixTime){
        long unixtime = Long.valueOf(UnixTime)*1000;// its need to be in milisecond
        Date date = new java.util.Date(unixtime);
        dateFormat = new SimpleDateFormat("EEE");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        result = dateFormat.format(date);
        return result;
    }
}
