package zane.weaths_up.Model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import zane.weaths_up.Util.TempConverter;

/**
 * Created by zaneran on 9/30/2016.
 */
public class CurrentItem {
    private String weather;
    private String icon;
    private String temperature;
    private String apparentTemperature;
    private String dewPoint;
    private String humidity;
    private String windSpeed;
    private String pressure;

    /*currently[Json Array] -> summary(Weather), icon, temperature, apparentTemperature,
     dewPoint, humidity, windSpeed, visibility, pressure
      */

    public CurrentItem(Context context, JSONObject currently) throws JSONException {

        if (currently != null){

//            JSONObject currently = jsonObject.getJSONObject("currently");
            weather = currently.getString("summary");
            icon = currently.getString("icon");
            String init_temp = currently.getString("temperature");
            temperature = new TempConverter(context).TempConvert(init_temp);
            apparentTemperature = currently.getString("apparentTemperature");
            dewPoint = currently.getString("dewPoint");
            humidity = currently.getString("humidity");
            windSpeed = currently.getString("windSpeed");
            pressure = currently.getString("pressure");
        }
    }

    public String getWeather(){
        return weather;
    }

    public String getIcon(){
        return icon;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getApparentTemperature(){
        return apparentTemperature;
    }

    public String getDewPoint(){
        return dewPoint;
    }

    public String getHumidity(){
        return humidity;
    }

    public String getWindSpeed(){
        return  windSpeed;
    }

    public String getPressure(){
        return pressure;
    }
}
