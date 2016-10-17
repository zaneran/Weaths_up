package zane.weaths_up.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zane.weaths_up.Events.WeatherEvent;
import zane.weaths_up.Model.CurrentItem;
import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.Model.HourlyItem;

/**
 * Created by zaneran on 10/1/2016.
 */
public class WeatherAPIFetcher {

    private Context context;
    private CurrentItem currentItem;
    private ArrayList<HourlyItem> parthourlyItemArrayList;
    private ArrayList<HourlyItem> hourlyItemArrayList;
    private ArrayList<DailyItem> dailyItemArrayList;
    private String timeZone;


    public WeatherAPIFetcher(Context context){
        this.context = context;
    }

    public void FetchAPI(String url){
        Log.i("url:", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        parthourlyItemArrayList = new ArrayList<HourlyItem>();
        hourlyItemArrayList = new ArrayList<HourlyItem>();
        dailyItemArrayList = new ArrayList<DailyItem>();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.toString());

                            Log.i("string", jsonObject1.getString("latitude"));
                            Log.i("hey", jsonObject1.getJSONObject("currently").toString());

                            timeZone = jsonObject1.getString("timezone");
                            JSONObject currently = jsonObject1.getJSONObject("currently");

                            currentItem = new CurrentItem(context, currently);
                            //Log.i("test", currently.toString());

                            JSONObject hourly = jsonObject1.getJSONObject("hourly");
                            JSONArray hourlyJSONArray = hourly.getJSONArray("data");

                            for (int i = 0; i < hourlyJSONArray.length(); i++){
                                JSONObject hourly_data = hourlyJSONArray.getJSONObject(i);
                                HourlyItem hourlyItem = new HourlyItem(context, timeZone, hourly_data);
                                hourlyItemArrayList.add(hourlyItem);
                                if (i < 5) {
                                    parthourlyItemArrayList.add(hourlyItem);
                                }
                            }

                            JSONObject daily = jsonObject1.getJSONObject("daily");
                            JSONArray dailyJSONArray = daily.getJSONArray("data");
                            for (int i = 0; i < dailyJSONArray.length(); i++){
                                JSONObject daily_data = dailyJSONArray.getJSONObject(i);
                                DailyItem dailyItem = new DailyItem(context, timeZone, daily_data);
                                dailyItemArrayList.add(dailyItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      //  Log.i("get result of", currentItem.getDewPoint());
                        EventBus.getDefault().postSticky(new WeatherEvent(currentItem,parthourlyItemArrayList,
                                hourlyItemArrayList, dailyItemArrayList));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "text/html";
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
