package zane.weaths_up;

import android.app.SearchManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import zane.weaths_up.Events.CityNameEvent;
import zane.weaths_up.Events.CoordinateEvent;
import zane.weaths_up.Events.CurrentLocationEvent;
import zane.weaths_up.Events.LastLocationEvent;
import zane.weaths_up.Events.WeatherEvent;
import zane.weaths_up.Layout.CustomLayout;
import zane.weaths_up.Model.CurrentLocItem;
import zane.weaths_up.Model.DBItem;
import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.Model.HourlyItem;
import zane.weaths_up.Service.LocationProvider;
import zane.weaths_up.Util.CityNameDBHelper;
import zane.weaths_up.Util.CityNameFetcher;
import zane.weaths_up.Util.CoordinateFetcher;
import zane.weaths_up.Util.ListViewStretcher;
import zane.weaths_up.Util.WeatherAPIFetcher;
import zane.weaths_up.adaptor.DailyAdaptor;
import zane.weaths_up.adaptor.HourlyAdaptor;

public class MainActivity extends AppCompatActivity {

    public final static String LAST_LOC_TAG = "Last_Loc";
    public final static String CURRENT_LOC_TAG = "Current_Loc";
    public final static String OTHER_LOC_TAG = "Other_Loc";
    public final static String Arraylist_Key = "CityName";
    public final static String INTENT_KEY = "Intent";
    private String lat, lng;
    private  CityNameDBHelper cityNameDBHelper;
    private RelativeLayout primary_layout;
    private LinearLayout hourly_data_full_layout;
    private Toolbar hourly_data_full_toolbar;
    private Toolbar toolbar_layout;
    private TextView CityName;
    private TextView Temperature;
    private TextView Weather;
    private ImageView WeatherIcon;
    private Button SeeMore;
    private ListView hourly_data;
    private ListView hourly_data_full;
    private ListView daily_data;
    private ArrayList<HourlyItem> parthourlyItemArrayList;
    private ArrayList<HourlyItem> hourlyItemArrayList;
    private ArrayList<DailyItem> dailyItemArrayList;
    private ArrayList<DBItem> spinnerArrayList;
    private ArrayList<String> spinnerNameArrayList;
    private ArrayAdapter spinnerAdaptor;
    private SearchView searchView;
    private Spinner spinner;
    private HourlyAdaptor hourlyAdaptor;
    private DailyAdaptor dailyAdaptor;
    private CustomLayout customLayout;
    private String background_url;
    private CityNameFetcher cityNameFetcher;
    private WeatherAPIFetcher weatherAPIFetcher;
    private boolean userIsInteracting = false;
    private CurrentLocItem currentLocItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        weatherAPIFetcher = new WeatherAPIFetcher(getApplicationContext());
        primary_layout = (RelativeLayout) findViewById(R.id.primary_layout);
        hourly_data_full_layout = (LinearLayout) findViewById(R.id.hourly_data_full_layout);
        hourly_data_full_toolbar = (Toolbar) findViewById(R.id.hourly_data_full_toolbar);
        toolbar_layout = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        customLayout = (CustomLayout) findViewById(R.id.main_layout);
        CityName = (TextView) findViewById(R.id.CityName);
        Temperature = (TextView) findViewById(R.id.Temperature);
        Weather = (TextView) findViewById(R.id.Weather);
        WeatherIcon = (ImageView) findViewById(R.id.WeatherIcon);
        SeeMore = (Button) findViewById(R.id.SeeMore);
        hourly_data = (ListView) findViewById(R.id.hourly_data);
        hourly_data_full = (ListView) findViewById(R.id.hourly_data_full);
        daily_data = (ListView) findViewById(R.id.daily_data);

        SeeMore.setOnClickListener(new SeeMoreListener());
        parthourlyItemArrayList = new ArrayList<HourlyItem>();
        hourlyItemArrayList = new ArrayList<HourlyItem>();
        dailyItemArrayList = new ArrayList<DailyItem>();
        spinnerArrayList = new ArrayList<DBItem>();
        spinnerNameArrayList = new ArrayList<String>();
        cityNameDBHelper = new CityNameDBHelper(getApplicationContext());


        handleIntent(getIntent());
    }

    //SPINNER METHOD (SET, CHANGE, ADD)

    //set spinner for last known location
    public void spinnerSetter(DBItem dbItem) {

        spinnerArrayList.add(dbItem);
        Log.i("Database Size", Integer.toString(cityNameDBHelper.CityNameDBGetter().size()));
        spinnerArrayList.addAll(cityNameDBHelper.CityNameDBGetter());
        spinnerNameArrayList.add(dbItem.getCityName());
        for (int i = 1; i < spinnerArrayList.size(); i++){
            spinnerNameArrayList.add(spinnerArrayList.get(i).getCityName());
        }
        spinnerAdaptor = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, spinnerNameArrayList);
        spinner = new Spinner(getSupportActionBar().getThemedContext());
        spinner.setAdapter(spinnerAdaptor);
        spinner.setOnItemSelectedListener(new SpinnerListener());
        spinner.setSelection(0);
        toolbar_layout.addView(spinner, 0);
    }

    //fresh spinner when current location got.
    public void spinnerFresher(DBItem dbItem) {
        spinnerArrayList.set(0, dbItem);
        spinnerNameArrayList.set(0, dbItem.getCityName());
        spinnerAdaptor.notifyDataSetChanged();
    }

    //add new item to spinner.
    public void spinnerAdder(DBItem dbItem){
        if (spinnerArrayList.size() > 9) {
            //0 is current city.
            cityNameDBHelper.CityNameDBDeleter(spinnerNameArrayList.get(1));
            spinnerNameArrayList.remove(1);
            spinnerArrayList.remove(1);
            spinnerNameArrayList.add(dbItem.getCityName());
            spinnerArrayList.add(dbItem);
        }else {
            spinnerArrayList.add(dbItem);
            spinnerNameArrayList.add(dbItem.getCityName());
        }
        spinnerAdaptor.notifyDataSetChanged();
        spinner.setSelection(spinnerArrayList.size() - 1);
    }

    //item already exist.
    public void spinnerChanger(int itemLocation) {
        spinner.setSelection(itemLocation);
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //userIsInteracting.
            if (userIsInteracting){
                lat = spinnerArrayList.get(position).getLat();
                lng = spinnerArrayList.get(position).getLng();
                CityName.setText(spinnerNameArrayList.get(position));
                SpecFetch(lat, lng, OTHER_LOC_TAG, true);
                Log.i("Spinner", "Selected");
                userIsInteracting = false;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    //SearchView methods, receive result in same active activity.
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("Query", "Started!");
            CoordinateFetcher coordinateFetcher = new CoordinateFetcher(getApplicationContext());
            coordinateFetcher.execute(query);
        }
    }


    //Completely show weather prediction for 48 hours.
    private class SeeMoreListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            primary_layout.setVisibility(View.INVISIBLE);
            hourly_data_full_layout.setVisibility(View.VISIBLE);
            setSupportActionBar(hourly_data_full_toolbar);
            hourly_data_full_toolbar.setTitle("Hourly - " + CityName.getText());
            hourly_data_full_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            hourly_data_full_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    primary_layout.setVisibility(View.VISIBLE);
                    hourly_data_full_layout.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    //Specify SearchView in Menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_board, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        return super.onCreateOptionsMenu(menu);
    }

    //Specify Menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // About option clicked.
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_management:
                if (spinnerNameArrayList.size() > 1){
                    Intent intent2 = new Intent(this, LocManageActivity.class);
                    intent2.putStringArrayListExtra(Arraylist_Key, spinnerNameArrayList);
                    startActivity(intent2);
                }else {
                    Toast.makeText(this, "Sorry, Please save favorite cities first.", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_settings:
                // Settings option clicked.
                Intent intent3 = new Intent(this, SettingActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void StopService(){
        //Stop service after get current location.
        Intent intent_service = new Intent(getApplicationContext(), LocationProvider.class);
        intent_service.addCategory(LocationProvider.TAG);
        stopService(intent_service);
    }

    public void SpecFetch(String lat, String lng, String KEY, boolean isCityKnown){
        //save to current loc recorder.
        if (KEY.equals(CURRENT_LOC_TAG) || KEY.equals(LAST_LOC_TAG)){

            CurrentLocItem.lat = lat;
            CurrentLocItem.lng = lng;

            //currentLocItem.setLat(lat);
            //currentLocItem.setLng(lng);
            //new CurrentLocRecorder(getApplicationContext()).setCoordinate(lat, lng);
        }

        String url = "https://api.darksky.net/forecast/41ab7fc743a4891c7f684e228c128bcd/" + lat
                + "," + lng;
        Log.i(KEY, url);

        Location location = new Location("location");
        location.setLatitude(Double.parseDouble(lat));
        location.setLongitude(Double.parseDouble(lng));

        weatherAPIFetcher = new WeatherAPIFetcher(getApplicationContext());
        weatherAPIFetcher.FetchAPI(url);

        if (!isCityKnown){
            CityNameFetcher cityNameFetcher = new CityNameFetcher(getApplicationContext(), KEY);
            cityNameFetcher.execute(location);
        }
    }

    //get last location
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEvent(LastLocationEvent lastLocationEvent) {

        lat = Double.toString(lastLocationEvent.getLocation().getLatitude());
        lng = Double.toString(lastLocationEvent.getLocation().getLongitude());

        SpecFetch(lat, lng, LAST_LOC_TAG, false);

        EventBus.getDefault().removeStickyEvent(lastLocationEvent);
    }


    //get current location
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEvent(CurrentLocationEvent currentLocationEvent) throws IOException {

        Log.i("get", "currentLocation");
        lat = Double.toString(currentLocationEvent.getLocation().getLatitude());
        lng = Double.toString(currentLocationEvent.getLocation().getLongitude());
        SpecFetch(lat, lng, CURRENT_LOC_TAG, false);
        StopService();
        EventBus.getDefault().removeStickyEvent(currentLocationEvent);
    }

    //get cityname
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CityNameEvent cityNameEvent) {

        Log.i("now", "getCityName");
        String cityname = cityNameEvent.getCityName();
        String Lat = cityNameEvent.getLat();
        String Lng = cityNameEvent.getLng();
        EventBus.getDefault().removeStickyEvent(cityNameEvent);
        CityName.setText(cityname);
        DBItem dbItem = new DBItem(cityname, Lat, Lng);

        switch (cityNameEvent.getLoc_type()){
            //Last Loc, Set Spinner
            case LAST_LOC_TAG:
                spinnerSetter(dbItem);
                Log.i("Set", "Spinner!!!!!!!!!!!!!");
                CurrentLocItem.cityname = cityname;
                //new CurrentLocRecorder(getApplicationContext()).setCityName(cityname);
                break;

            //Current Loc, update Spinner
            case CURRENT_LOC_TAG:
                spinnerFresher(dbItem);
                CurrentLocItem.cityname = cityname;
                //currentLocItem.setCityname(cityname);
                //new CurrentLocRecorder(getApplicationContext()).setCityName(cityname);
                break;

            //Other Loc, add to spinner or change spinner selection.
            case OTHER_LOC_TAG:
                int result = cityNameDBHelper.CityNameDBQuerier(cityname);
                if (result == 0){
                    cityNameDBHelper.CityNameDBInserter(dbItem);
                    spinnerAdder(dbItem);
                }else {
                    spinnerChanger(result);
                }
                break;
            default:
                break;
        }
    }

    //get coordinate from cityname
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CoordinateEvent coordinateEvent) {
        lat = Double.toString(coordinateEvent.getLat());
        lng = Double.toString(coordinateEvent.getLng());
        CityName.setText("N/A");
        Temperature.setText("N/A");
        Weather.setText("N/A");

        if (lat != null && lng != null){
            SpecFetch(lat, lng, OTHER_LOC_TAG, false);
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
        EventBus.getDefault().removeStickyEvent(coordinateEvent);
    }

    //get weather api result
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherEvent weatherEvent) {

        Log.i("weather", "get!!!!");
        Temperature.setText(weatherEvent.getCurrentItem().getTemperature());
        Weather.setText(weatherEvent.getCurrentItem().getWeather());
        WeatherIconBGSwitcher(weatherEvent.getCurrentItem().getIcon());

        parthourlyItemArrayList.clear();
        parthourlyItemArrayList.addAll(weatherEvent.getParthourlyItemArrayList());
        hourlyAdaptor = new HourlyAdaptor(this, parthourlyItemArrayList);
        hourly_data.setAdapter(hourlyAdaptor);
        ListViewStretcher.setListViewHeightBasedOnChildren(hourly_data);
        hourlyItemArrayList.clear();
        hourlyItemArrayList.addAll(weatherEvent.getHourlyItemArrayList());
        hourlyAdaptor = new HourlyAdaptor(this, hourlyItemArrayList);
        hourly_data_full.setAdapter(hourlyAdaptor);

        dailyItemArrayList.clear();
        dailyItemArrayList.addAll(weatherEvent.getDailyItemArrayList());
        dailyAdaptor = new DailyAdaptor(this, dailyItemArrayList);
        daily_data.setAdapter(dailyAdaptor);
        ListViewStretcher.setListViewHeightBasedOnChildren(daily_data);
        EventBus.getDefault().removeStickyEvent(weatherEvent);
    }

    //Set Weather Icon and BackGround.
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

    @Override
    protected void onDestroy(){
        StopService();
        //new CurrentLocRecorder(getApplicationContext()).clearRecord();
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume(){
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

       // CurrentLocItem currentLocItem = new CurrentLocItem();
        if (getIntent().hasExtra(INTENT_KEY)) {

        //CurrentLocRecorder currentLocRecorder = new CurrentLocRecorder(getApplicationContext());
        //if (!currentLocRecorder.getLatRecord().equals(CurrentLocRecorder.FAILED_INFO)){
            Log.i("陶然你的智商黑喂狗了吗！！！！！", "你说的真他妈对");
            lat = CurrentLocItem.lat;
            lng = CurrentLocItem.lng;
            SpecFetch(lat, lng, LAST_LOC_TAG, false);}
        super.onResume();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }
}
