package zane.weaths_up.Util;

import android.content.Context;

import zane.weaths_up.Preferences.SettingPreferences;

/**
 * Created by zaneran on 10/13/2016.
 */
public class TempConverter {

    private Context context;

    public TempConverter(Context context){
        this.context = context;
    }

    public String TempConvert(String init_temp){
        SettingPreferences settingPreferences = new SettingPreferences(context);
        double result = Double.parseDouble(init_temp);
        if (settingPreferences.getCentigradePreferences()){
            result = (result - 32) * 0.5556;
        }
        return Integer.toString((int) Math.round(result));
    }
}
