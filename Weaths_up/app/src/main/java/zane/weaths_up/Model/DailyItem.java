package zane.weaths_up.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zaneran on 10/1/2016.
 */
public class DailyItem {

    private String date;
    private String weather;
    private String icon;
    private String temperatureMin;
    private String temperatureMax;

    public DailyItem(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null){
            date = jsonObject.getString("time");
            weather = jsonObject.getString("summary");
            icon = jsonObject.getString("icon");
            temperatureMin = jsonObject.getString("temperatureMin");
            temperatureMax = jsonObject.getString("temperatureMax");
        }
    }
    public String getdate(){
        return date;
    }
    public String getWeather(){
        return weather;
    }
    public String getIcon(){
        return icon;
    }
    public String getTemperatureMin() {
        return temperatureMin;
    }
    public String getTemperatureMax() {  return temperatureMax; }
}
