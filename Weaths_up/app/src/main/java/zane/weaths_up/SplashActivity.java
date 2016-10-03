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

        CheckPermission();
        processStartService(LocationProvider.TAG);
        new Timer().schedule(new Task(),5000);
    }
    class Task extends TimerTask {

        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void processStartService(final String tag){
        Intent intent_service = new Intent(getApplicationContext(), LocationProvider.class);
        intent_service.addCategory(tag);
        startService(intent_service);
    }

    public void CheckPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PackageManager.PERMISSION_GRANTED){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED){

                //Permission Denied
                Toast.makeText(SplashActivity.this, "Permission Denied," +
                        "Some functions will not be available!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
