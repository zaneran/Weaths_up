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
        private String lat = "";
        private String lng = "";
        private boolean isGPSEnabled = false;
        private boolean isNetworkEnabled = false;
        private Location gps_last_loc;
        private Location net_last_loc;
        private Location final_loc;


        @Override
        public void onCreate() {
            Log.i("service", "start");
            super.onCreate();
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
        boolean updatedGPS = true;
        //for Android version lower than 6.0
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Log.i("Network_Status::::::::::::", String.valueOf(isNetworkEnabled));
        if (isGPSEnabled){
            gps_last_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (isNetworkEnabled){
            net_last_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (gps_last_loc != null && net_last_loc != null){
            if (gps_last_loc.getAccuracy() > net_last_loc.getAccuracy()){
                final_loc = gps_last_loc;
            } else {
                final_loc = net_last_loc;
                updatedGPS = false;
            }
        } else {
            if (gps_last_loc != null){
                final_loc = gps_last_loc;
            } else if (net_last_loc != null){
                final_loc = net_last_loc;
                updatedGPS = false;
            }
        }
        EventBus.getDefault().postSticky(new LastLocationEvent(final_loc));
        String location_type;
        if (updatedGPS){
            location_type = LocationManager.GPS_PROVIDER;
            Log.i("Update GPS ooooooooooo", "yes!!!!!!!");
        } else {
            Log.i("Update Network ooooo", "yes!!!!!!!");
            location_type = LocationManager.NETWORK_PROVIDER;
        }
        locationManager.requestLocationUpdates(location_type, 2000000000, 10, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
            Log.i("Current Location", "get");
            Log.i("Lat", lat);
            Log.i("lng", lng);
            EventBus.getDefault().postSticky(new CurrentLocationEvent(location));
        }
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
