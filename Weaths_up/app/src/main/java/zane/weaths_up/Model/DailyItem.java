package zane.weaths_up.Model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import zane.weaths_up.Util.TempConverter;
import zane.weaths_up.Util.UnixTimeConverter;

/**
 * Created by zaneran on 10/1/2016.
 */
public class DailyItem {

    private String date;
    private String icon;
    private String temperatureMin;
    private String temperatureMax;

    public DailyItem(Context context, String timeZone, JSONObject jsonObject) throws JSONException {

        if (jsonObject != null){
            String init_date = jsonObject.getString("time");
            date = new UnixTimeConverter(context).WeekdayConvert(timeZone, init_date);

            icon = jsonObject.getString("icon");
            icon = icon.replace('-', '_');
            String init_tempMin = jsonObject.getString("temperatureMin");
            temperatureMin = new TempConverter(context).TempConvert(init_tempMin);
            String init_tempMax = jsonObject.getString("temperatureMax");
            temperatureMax = new TempConverter(context).TempConvert(init_tempMax);
        }
    }
    public String getdate(){
        return date;
    }
    public String getIcon(){
        return icon;
    }
    public String getTemperatureMin() {
        return temperatureMin;
    }
    public String getTemperatureMax() {  return temperatureMax; }
}
