package zane.weaths_up.Util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import zane.weaths_up.Events.CoordinateEvent;

/**
 * Created by zaneran on 10/9/2016.
 */
public class CoordinateFetcher extends AsyncTask<String, Void, String> {
    private Context context;

    public CoordinateFetcher(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

            String location = params[0];
            Geocoder geocoder = new Geocoder(context);
            List<Address> addresses = null;

        try {
            addresses= geocoder.getFromLocationName(location, 2); // get the found Address Objects
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && !addresses.isEmpty()) {
            Log.i("yyyyyyyyyy","yunxingdao");
            EventBus.getDefault().postSticky(new CoordinateEvent(addresses.get(0).getLatitude(),
                        addresses.get(0).getLongitude()));
            Log.i("pppppppppp", Double.toString(addresses.get(0).getLatitude()));
            Log.i(Double.toString(addresses.get(0).getLatitude()), Double.toString(addresses.get(0).getLongitude()));
        }
        return null;
    }
}
