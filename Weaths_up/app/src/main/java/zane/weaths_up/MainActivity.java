package zane.weaths_up;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import zane.weaths_up.Events.CurrentLocationEvent;
import zane.weaths_up.Events.LastLocationEvent;
import zane.weaths_up.Events.WeatherEvent;
import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.Model.HourlyItem;
import zane.weaths_up.Service.LocationProvider;
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
    private LinearLayout linearLayout;
    private String background_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        CityName = (TextView) findViewById(R.id.CityName);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Weather = (TextView) findViewById(R.id.Weather);
        WeatherIcon = (ImageView) findViewById(R.id.WeatherIcon);
        current_data = (GridView) findViewById(R.id.current_data);
        hourly_data = (ListView) findViewById(R.id.hourly_data);
        daily_data = (ListView) findViewById(R.id.daily_data);

        hourlyItemArrayList = new ArrayList<HourlyItem>();
        dailyItemArrayList = new ArrayList<DailyItem>();
    }

    //catch last location
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
    }


    //catch current location
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEvent(CurrentLocationEvent currentLocationEvent) {

        lat = Double.toString(currentLocationEvent.getLocation().getLatitude());
        lng = Double.toString(currentLocationEvent.getLocation().getLongitude());
        //Log.i(lat, lng);
        String url = "https://api.darksky.net/forecast/41ab7fc743a4891c7f684e228c128bcd/" + lat
                + "," + lng;

        Log.i("URL",url);
        WeatherAPIFetcher weatherAPIFetcher = new WeatherAPIFetcher(this);
        weatherAPIFetcher.FetchAPI(url);

        Intent intent_service = new Intent(getApplicationContext(), LocationProvider.class);
        intent_service.addCategory(LocationProvider.TAG);
        startService(intent_service);
    }


    //catch weather api result
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherEvent weatherEvent) {

        Temperature.setText(weatherEvent.getCurrentItem().getTemperature());
        Weather.setText(weatherEvent.getCurrentItem().getWeather());
        WeatherIconBGSwitcher(weatherEvent.getCurrentItem().getWeather());

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

       /* String[] current_result = new String[] {
                "ApparentTemperature",  weatherEvent.getCurrentItem().getApparentTemperature(),
                "DewPoint", weatherEvent.getCurrentItem().getDewPoint(),
                "Humidity", weatherEvent.getCurrentItem().getHumidity(),
                "Pressure", weatherEvent.getCurrentItem().getPressure(),
                "WindSpeed", weatherEvent.getCurrentItem().getWindSpeed(),
                "Visibility",   weatherEvent.getCurrentItem().getVisibility()
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.now_items, current_result);
        current_data.setAdapter(arrayAdapter);*/

        EventBus.getDefault().removeStickyEvent(weatherEvent);
    }

    public void WeatherIconBGSwitcher(String weather){

        switch (weather){
            case "clear-day":
                WeatherIcon.setImageResource(R.drawable.clear_day);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2014/02/900-fields-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.clear_day_bg);

                break;

            case "clear-night":
                WeatherIcon.setImageResource(R.drawable.clear_night);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Trees-Night-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.clear_night_bg);
                break;

            case "cloudy":
                WeatherIcon.setImageResource(R.drawable.cloudy);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Sea-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.cloudy);
                break;

            case "fog":
                WeatherIcon.setImageResource(R.drawable.fog);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2015/12/900-Autumn-Scenery-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.fog_bg);
                break;

            case "partly-cloudy-day":
                WeatherIcon.setImageResource(R.drawable.partly_cloudy_day);
                //http://www.mobileswall.com/wp-content/uploads/2013/09/900-beach-l.jpg
                linearLayout.setBackgroundResource(R.drawable.partly_cloudy_day_bg);
                break;

            case "partly-cloudy-night":
                WeatherIcon.setImageResource(R.drawable.partly_cloudy_night);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-water-ocean-clouds-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.partly_cloudy_night_bg);
                break;

            case "rain":
                WeatherIcon.setImageResource(R.drawable.rain);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Tokyo-Streets-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.rain_bg);
                break;

            case "sleet":
                WeatherIcon.setImageResource(R.drawable.sleet);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/10/900-Sunlight-Frost-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.sleet_bg);
                break;

            case "snow":
                WeatherIcon.setImageResource(R.drawable.snow);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/10/900-Winter-Snow-2-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.snow_bg);
                break;

            case "wind":
                WeatherIcon.setImageResource(R.drawable.wind);
                background_url = "http://www.mobileswall.com/wp-content/uploads/2013/09/900-Dusk-l.jpg";
                //linearLayout.setBackgroundResource(R.drawable.wind);
                break;

            default:
                //linearLayout.setBackgroundResource(R.drawable.clear_day_bg);
                break;
        }
        Picasso.with(this).load(background_url).into(new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                linearLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });
    }

}
