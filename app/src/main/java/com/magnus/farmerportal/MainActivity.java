package com.magnus.farmerportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected Context context;
    //final String[] position=new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }*/
/*
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //mlocation = location;
                position[0] =String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude());
                Log.i( location.toString(),"Location Changes");
                //txtLat.setText(String.valueOf(location.getLatitude()));
                //longitude.setText();
                //Geocoder geocoder=new Geocoder(MainActivity.this);
                try
                {
                    Geocoder geocoder=new Geocoder(MainActivity.this);
                    List<Address> addresses=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                    // txtLat.setText(txtLat.getText()+"\n"+addresses.get(0).getAddressLine(0)+","+
                    //        addresses.get(0).getAddressLine(1)+","+
                    //        addresses.get(0).getAddressLine(2));
                    position[1]=addresses.get(0).getAddressLine(0);
                    position[2]=addresses.get(0).getAddressLine(1);
                    position[3]=addresses.get(0).getAddressLine(2);
                    Log.i(position[0]+position[1]+position[2]+position[3], "onLocationChanged: ");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        Looper looper = null;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestSingleUpdate(criteria, locationListener, looper);
*/
    }





    public void LoginConsumer(View view) {
        Intent intent=new Intent(getApplicationContext(), LoginConsumer.class);
        //intent.putExtra("Location",position);
        startActivity(intent);

    }

    public void farmerLogIn(View view) {
        Intent intent=new Intent(getApplicationContext(), LoginFarmer.class);
        //intent.putExtra("Location",position);
        startActivity(intent);

    }
}
