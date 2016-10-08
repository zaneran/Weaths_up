package zane.weaths_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import zane.weaths_up.Events.CityNameEvent;
import zane.weaths_up.Events.CurrentLocationEvent;
import zane.weaths_up.Events.LastLocationEvent;
import zane.weaths_up.Events.WeatherEvent;
import zane.weaths_up.Layout.CustomLayout;
import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.Model.HourlyItem;
import zane.weaths_up.Service.LocationProvider;
import zane.weaths_up.Util.CityNameFetcher;
import zane.weaths_up.Util.ListViewStretcher;
import zane.weaths_up.Util.WeatherAPIFetcher;
import zane.weaths_up.adaptor.DailyAdaptor;
import zane.weaths_up.adaptor.HourlyAdaptor;

public class MainActivity extends AppCompatActivity {

    String lat, lng;
    private TextView CityName;
    private TextView Temperature;
    private TextView Weather;
    private ImageView WeatherIcon;
    private GridView current_data;
    private ListView hourly_data;
    private ListView daily_data;
    private ArrayList<HourlyItem> hourlyItemArrayList;
    private ArrayList<DailyItem> dailyItemArrayList;
    private HourlyAdaptor hourlyAdaptor;
    private DailyAdaptor dailyAdaptor;
    private CustomLayout customLayout;
    private String background_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        customLayout = (CustomLayout) findViewById(R.id.main_layout);
        CityName = (TextView) findViewById(R.id.CityName);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Weather = (TextView) findViewById(R.id.Weather);
        WeatherIcon = (ImageView) findViewById(R.id.WeatherIcon);
        hourly_data = (ListView) findViewById(R.id.hourly_data);
        daily_data = (ListView) findViewById(R.id.daily_data);

        hourlyItemArrayList = new ArrayList<HourlyItem>();
        dailyItemArrayList = new ArrayList<DailyItem>();
    }

    //get last location
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEvent(LastLocationEvent lastLocationEvent) {

        lat = Double.toString(lastLocationEvent.getLocation().getLatitude());
        lng = Double.toString(lastLocationEvent.getLocation().getLongitude());
        //Log.i(lat, lng);

        String url = "https://api.darksky.net/forecast/41ab7fc743a4891c7f684e228c128bcd/" + lat
                + "," + lng;

        Log.i("URL",url);

        WeatherAPIFetcher weatherAPIFetcher = new WeatherAPIFetcher(this);
        weatherAPIFetcher.FetchAPI(url);
        CityNameFetcher cityNameFetcher = new CityNameFetcher(getApplicationContext());
        cityNameFetcher.execute(lastLocationEvent.getLocation());
    }


    //get current location
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEvent(CurrentLocationEvent currentLocationEvent) throws IOException {

        lat = Double.toString(currentLocationEvent.getLocation().getLatitude());
        lng = Double.toString(currentLocationEvent.getLocation().getLongitude());
        //Log.i(lat, lng);
        String url = "https://api.darksky.net/forecast/41ab7fc743a4891c7f684e228c128bcd/" + lat
                + "," + lng;

        Log.i("URL", url);
        WeatherAPIFetcher weatherAPIFetcher = new WeatherAPIFetcher(this);
        weatherAPIFetcher.FetchAPI(url);

        CityNameFetcher cityNameFetcher = new CityNameFetcher(getApplicationContext());
        cityNameFetcher.execute(currentLocationEvent.getLocation());

        Intent intent_service = new Intent(getApplicationContext(), LocationProvider.class);
        intent_service.addCategory(LocationProvider.TAG);
        stopService(intent_service);
    }

    //get current cityname
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CityNameEvent cityNameEvent) {
        CityName.setText(cityNameEvent.getCityName());
        EventBus.getDefault().removeStickyEvent(cityNameEvent);
    }

    //get weather api result
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherEvent weatherEvent) {

        Temperature.setText(weatherEvent.getCurrentItem().getTemperature());
        Weather.setText(weatherEvent.getCurrentItem().getWeather());
        WeatherIconBGSwitcher(weatherEvent.getCurrentItem().getIcon());

        hourlyItemArrayList.clear();
        hourlyItemArrayList.addAll(weatherEvent.getHourlyItemArrayList());
        hourlyAdaptor = new HourlyAdaptor(this, hourlyItemArrayList);
        hourly_data.setAdapter(hourlyAdaptor);
        ListViewStretcher.setListViewHeightBasedOnChildren(hourly_data);

        dailyItemArrayList.clear();
        dailyItemArrayList.addAll(weatherEvent.getDailyItemArrayList());
        dailyAdaptor = new DailyAdaptor(this, dailyItemArrayList);
        daily_data.setAdapter(dailyAdaptor);
        ListViewStretcher.setListViewHeightBasedOnChildren(daily_data);
        EventBus.getDefault().removeStickyEvent(weatherEvent);
    }

    public void WeatherIconBGSwitcher(String weather){

        switch (weather){
            case "clear-day":
                WeatherIcon.setImageResource(R.drawable.clear_day);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2014/02/900-fields-l.jpg";
                break;

            case "clear-night":
                WeatherIcon.setImageResource(R.drawable.clear_night);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Trees-Night-l.jpg";
                break;

            case "cloudy":
                WeatherIcon.setImageResource(R.drawable.cloudy);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Sea-l.jpg";
                break;

            case "fog":
                WeatherIcon.setImageResource(R.drawable.fog);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2015/12/900-Autumn-Scenery-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.fog_bg);
                break;

            case "partly-cloudy-day":
                WeatherIcon.setImageResource(R.drawable.partly_cloudy_day);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-beach-l.jpg";
                break;

            case "partly-cloudy-night":
                WeatherIcon.setImageResource(R.drawable.partly_cloudy_night);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-water-ocean-clouds-l.jpg";
                break;

            case "rain":
                WeatherIcon.setImageResource(R.drawable.rain);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Tokyo-Streets-l.jpg";
                break;

            case "sleet":
                WeatherIcon.setImageResource(R.drawable.sleet);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/10/900-Sunlight-Frost-l.jpg";
                break;

            case "snow":
                WeatherIcon.setImageResource(R.drawable.snow);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/10/900-Winter-Snow-2-l.jpg";
                break;

            case "wind":
                WeatherIcon.setImageResource(R.drawable.wind);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Dusk-l.jpg";
                break;

            default:
                break;
        }
        Picasso.with(this).load(background_url).into(customLayout);
    }

}
