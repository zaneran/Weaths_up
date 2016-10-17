package zane.weaths_up.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zaneran on 10/13/2016.
 */
public class SettingPreferences {

        private SharedPreferences sharedPreferences;

        public SettingPreferences(Context context){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        public void setCentigradePrefernces(boolean isCentigrade){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isCentigrade", isCentigrade);
            editor.apply();
        }

        public boolean getCentigradePreferences(){
            return sharedPreferences.getBoolean("isCentigrade", false);
        }

        public void setTimePreferences(boolean is24Hours){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is24Hours", is24Hours);
            editor.apply();
        }

        public boolean getTimePreferences(){
            return sharedPreferences.getBoolean("is24Hours", false);
        }
}

