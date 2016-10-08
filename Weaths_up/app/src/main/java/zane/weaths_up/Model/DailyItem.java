package zane.weaths_up.Model;

import org.json.JSONException;
import org.json.JSONObject;

import zane.weaths_up.Util.UnixTimeConverter;

/**
 * Created by zaneran on 10/1/2016.
 */
public class DailyItem {

    private String init_date;
    private String date;
    //private String weather;
    private String icon;
    private String temperatureMin;
    private String temperatureMax;

    public DailyItem(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null){
            init_date = jsonObject.getString("time");
            date = new UnixTimeConverter().WeekdayConvert(init_date);
            //weather = jsonObject.getString("summary");
            icon = jsonObject.getString("icon");
            icon = icon.replace('-', '_');
            temperatureMin = jsonObject.getString("temperatureMin");
            temperatureMax = jsonObject.getString("temperatureMax");
        }
    }
    public String getdate(){
        return date;
    }
    /*public String getWeather(){
        return weather;
    }*/
    public String getIcon(){
        return icon;
    }
    public String getTemperatureMin() {
        return temperatureMin;
    }
    public String getTemperatureMax() {  return temperatureMax; }
}
