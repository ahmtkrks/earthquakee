package com.example.earthquakee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.earthquakee.API.ApiClient;
import com.example.earthquakee.Fragment.FragmentSign;
import com.example.earthquakee.Model.Users;
import com.example.earthquakee.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;

    private ActivityMainBinding mBinding;

    private FragmentManager fm;
    private int permissionController = 0;
    public FusedLocationProviderClient flpc;
    public Task<Location> locationTask;

    public String latitude , longtiude ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportActionBar().hide();


        //KONUM

        flpc = LocationServices.getFusedLocationProviderClient(this);
        permissionController = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (permissionController != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

        } else {

            locationTask = flpc.getLastLocation();
            getLocation();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentHolder, new FragmentSign(),"Fragment Sign");
        ft.commit();


        //SENSOR SERVİSİNE ERİŞİM
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);



    }



    public void getLocation() {
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {

                latitude = String.valueOf(location.getLatitude());
                longtiude = String.valueOf(location.getLongitude());

                Toast.makeText((Context) this, "Enlem :" +latitude+ " Boylam :"+longtiude, Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Konum Bilgisi Alınamadı !", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    @SuppressLint("MissingSuperCall")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Accepted! ", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(MainActivity.this, MainActivity.class);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                locationTask = flpc.getLastLocation();
                getLocation();

            } else {
                Toast.makeText(this, "Permission Denied! ", Toast.LENGTH_LONG).show();
            }


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            
            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);
            if (xValue > 30 || yValue > 30 || zValue > 30) {

                getLocation();


                Toast.makeText(MainActivity.this, "Deprem ! " , Toast.LENGTH_SHORT).show();


                try {
                    ApiClient.earthquake();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }



        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}


