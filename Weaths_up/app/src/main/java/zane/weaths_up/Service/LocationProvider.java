package zane.weaths_up.Service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import zane.weaths_up.Events.CurrentLocationEvent;
import zane.weaths_up.Events.LastLocationEvent;

/**
 * Created by zaneran on 10/1/2016.
 */
public class LocationProvider extends Service implements LocationListener {

        public static final String TAG = "LOCATION_SERVICE";
        private LocationManager locationManager;
        private int count = 0;
        private boolean threadDisable=false;
        private String lat, lng;



        @Override
        public void onCreate() {
            Log.i("service", "start");
            super.onCreate();
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            startPosition();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            count=0;
            threadDisable = true;
            Log.v("CountService", "on destroy");
        }

    private void startPosition() {
        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        //for Android version lower than 6.0
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //last known location.
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnownLocation != null) {
            lat = Double.toString(lastKnownLocation.getLatitude());
            lng = Double.toString(lastKnownLocation.getLongitude());
            Log.i("Lat", lat);
            Log.i("lng", lng);
            EventBus.getDefault().postSticky(new LastLocationEvent(lastKnownLocation));
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000000000, 10, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i("Current Location", "get");
        Log.i("Lat", lat);
        Log.i("lng", lng);
        EventBus.getDefault().postSticky(new CurrentLocationEvent(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
