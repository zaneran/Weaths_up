package zane.weaths_up.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zaneran on 10/16/2016.
 */
public class CurrentLocRecorder {
    private SharedPreferences current_sharedPreferences;
    public final static String FAILED_INFO = "fail";
    public final static String LAT_KEY = "LatRecord";
    public final static String LNG_KEY = "LngRecord";
    public final static String CTNAME_KEY = "Location";

    public CurrentLocRecorder(Context context){
        current_sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setCityName(String CityName){
        SharedPreferences.Editor editor = current_sharedPreferences.edit();
        editor.putString(CTNAME_KEY, CityName);
        editor.apply();
    }

    public void setCoordinate(String lat, String lng){
        SharedPreferences.Editor editor = current_sharedPreferences.edit();
        editor.putString(LAT_KEY, lat);
        editor.putString(LNG_KEY, lng);
        editor.apply();
    }

    public String getLatRecord(){
        return current_sharedPreferences.getString(LAT_KEY, FAILED_INFO);
    }

    public String getLngRecord(){
        return current_sharedPreferences.getString(LNG_KEY, FAILED_INFO);
    }

    public String getCityNameRecord() {
        return current_sharedPreferences.getString(CTNAME_KEY, FAILED_INFO);
    }

    public void clearRecord(){
        SharedPreferences.Editor editor = current_sharedPreferences.edit();
        editor.remove(CTNAME_KEY);
        editor.remove(LNG_KEY);
        editor.remove(LAT_KEY);
        editor.clear();
        editor.commit();
    }
}
