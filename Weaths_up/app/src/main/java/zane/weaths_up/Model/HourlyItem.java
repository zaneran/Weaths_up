package zane.weaths_up.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zaneran on 10/1/2016.
 */
public class HourlyItem {

    private String date;
    private String weather;
    private String icon;
    private String temperature;

    public HourlyItem(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null){
            date = jsonObject.getString("time");
            weather = jsonObject.getString("summary");
            icon = jsonObject.getString("icon");
            temperature = jsonObject.getString("temperature");
        }
    }
    public String getDate(){
        return date;
    }
    public String getWeather(){
        return weather;
    }
    public String getIcon(){
        return icon;
    }
    public String getTemperature() {
        return temperature;
    }
}
