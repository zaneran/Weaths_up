package zane.weaths_up.Model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import zane.weaths_up.Util.TempConverter;
import zane.weaths_up.Util.UnixTimeConverter;

/**
 * Created by zaneran on 10/1/2016.
 */
public class HourlyItem {

    private String date;
    private String icon;
    private String temperature;

    public HourlyItem(Context context, String timeZone, JSONObject jsonObject) throws JSONException {

        if (jsonObject != null){
            String init_date = jsonObject.getString("time");
            date = new UnixTimeConverter(context).HourConvert(timeZone, init_date);

            icon = jsonObject.getString("icon");
            icon = icon.replace('-', '_');
            String init_temp = jsonObject.getString("temperature");
            temperature = new TempConverter(context).TempConvert(init_temp);
        }
    }
    public String getDate(){
        return date;
    }
    public String getIcon(){
        return icon;
    }
    public String getTemperature() {
        return temperature;
    }
}
