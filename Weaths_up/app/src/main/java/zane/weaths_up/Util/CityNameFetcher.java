package zane.weaths_up.Util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import zane.weaths_up.Events.CityNameEvent;

/**
 * Created by zaneran on 10/8/2016.
 */
public class CityNameFetcher extends AsyncTask<Location, Void, String>{

    private Context context;
    private String loc_type;


    public CityNameFetcher(Context context, String loc_type){
        this.context = context;
        this.loc_type = loc_type;
    }

    @Override
    protected String doInBackground (Location... locations) {

        Double lng = locations[0].getLongitude();
        Double lat = locations[0].getLatitude();

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().postSticky(new CityNameEvent(addresses != null ? addresses.get(0).getLocality() : null,
                                            lat.toString(),
                                            lng.toString(), loc_type));
        return null;
    }


}
