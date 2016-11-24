package zane.weaths_up;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import zane.weaths_up.Service.LocationProvider;

public class SplashActivity extends AppCompatActivity {
    static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (CheckPermission()){
            processStartService(LocationProvider.TAG);
            new Timer().schedule(new Task(),5000);
        }
    }
    class Task extends TimerTask {

        @Override
        public void run() {
            StartActivity();
        }
    }

    private void StartActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void processStartService(final String tag){
        Intent intent_service = new Intent(getApplicationContext(), LocationProvider.class);
        intent_service.addCategory(tag);
        startService(intent_service);
    }

    public boolean CheckPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_FINE_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_FINE_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                processStartService(LocationProvider.TAG);
                new Timer().schedule(new Task(), 5000);

            }else {

            //Permission Denied
               Toast.makeText(SplashActivity.this, "Permission Denied," +
               "Some functions will not be available!", Toast.LENGTH_LONG).show();
               SplashActivity.this.finish();
            }
        }
    }
}
