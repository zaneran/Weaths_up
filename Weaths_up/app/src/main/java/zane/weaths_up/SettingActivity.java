package zane.weaths_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import zane.weaths_up.Preferences.SettingPreferences;

public class SettingActivity extends AppCompatActivity {

    private boolean isCentigrade;
    private boolean is24Hours;
    private SettingPreferences settingPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Weath's Up Setting");
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new NavigationListener());

        Switch temp_switch = (Switch) findViewById(R.id.temp_switch);
        Switch time_switch = (Switch) findViewById(R.id.time_switch);

        settingPreferences = new SettingPreferences(this);

        isCentigrade = settingPreferences.getCentigradePreferences();
        is24Hours = settingPreferences.getTimePreferences();

        temp_switch.setChecked(isCentigrade);
        time_switch.setChecked(is24Hours);
        temp_switch.setOnCheckedChangeListener(new TempChangeListener());
        time_switch.setOnCheckedChangeListener(new TimeChangeListener());

        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new SubmitButtonListener());
    }

    public class TempChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isCentigrade = isChecked;
        }
    }

    public class TimeChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            is24Hours = isChecked;
        }
    }

    public class SubmitButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            settingPreferences.setCentigradePrefernces(isCentigrade);
            settingPreferences.setTimePreferences(is24Hours);
        }
    }

    public class NavigationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY, true);
            startActivity(intent);
        }
    }
}
