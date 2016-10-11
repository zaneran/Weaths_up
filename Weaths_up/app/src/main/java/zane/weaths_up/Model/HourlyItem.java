package zane.weaths_up.Model;

import org.json.JSONException;
import org.json.JSONObject;

import zane.weaths_up.Util.UnixTimeConverter;

/**
 * Created by zaneran on 10/1/2016.
 */
public class HourlyItem {

    private String init_date;
    private String date;
    //private String weather;
    private String icon;
    private String temperature;

    public HourlyItem(String timeZone, JSONObject jsonObject) throws JSONException {
        if (jsonObject != null){
            init_date = jsonObject.getString("time");
            date = new UnixTimeConverter().HourConvert(timeZone, init_date);
            //weather = jsonObject.getString("summary");
            icon = jsonObject.getString("icon");
            icon = icon.replace('-', '_');
            temperature = jsonObject.getString("temperature");
        }
    }
    public String getDate(){
        return date;
    }
    /*public String getWeather(){
        return weather;
    }*/
    public String getIcon(){
        return icon;
    }
    public String getTemperature() {
        return temperature;
    }
}
