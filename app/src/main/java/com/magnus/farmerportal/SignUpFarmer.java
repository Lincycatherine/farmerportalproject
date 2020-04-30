package com.magnus.farmerportal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;

public class SignUpFarmer extends AppCompatActivity {

    EditText name, user1, pass, cnfrmpass, phone;
    Button signin;
    ImageView rback;
    Cursor cursor;
    SQLiteDatabase db;
    CheckBox rshow;
    DBHelperFarmer dbHelper;
    String userlocation;
    final String[] position = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_consumer);
        dbHelper = new DBHelperFarmer(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }

        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.password);
        cnfrmpass = (EditText) findViewById(R.id.cnfrmpassword);
        user1 = (EditText) findViewById(R.id.user);
        rshow = (CheckBox) findViewById(R.id.rshowPass);
        signin = (Button) findViewById(R.id.signin);
        rback = (ImageView) findViewById(R.id.rback);
        phone = (EditText) findViewById(R.id.phone);

        showPass();


        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                position[0] = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
                Log.i(location.toString(), "Location Changes");

                try {
                    Geocoder geocoder = new Geocoder(SignUpFarmer.this);
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    // txtLat.setText(txtLat.getText()+"\n"+addresses.get(0).getAddressLine(0)+","+
                    //        addresses.get(0).getAddressLine(1)+","+
                    //        addresses.get(0).getAddressLine(2));
                    position[1] = addresses.get(0).getAddressLine(0);
                    position[2] = addresses.get(0).getAddressLine(1);
                    position[3] = addresses.get(0).getAddressLine(2);
                    Log.i(position[0] + position[1] + position[2] + position[3], "onLocationChanged: ");
                    userlocation = position[0] + position[1] + position[2] + position[3];

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


        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        final Looper looper = null;


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestSingleUpdate(criteria, locationListener, looper);

        rback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpFarmer.this, LoginFarmer.class);
                startActivity(intent);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("") ||
                        user1.getText().toString().equals("") ||
                        pass.getText().toString().equals("") || cnfrmpass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Details", Toast.LENGTH_LONG).show();
                    //return;
                }

                // check if both password matches
                else if (!pass.getText().toString().equals(cnfrmpass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                }
                //enter 10 digit customer
                else if (phone.getText().toString().length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter a 10 digit Phone number", Toast.LENGTH_LONG).show();
                } else {


                    dbHelper.addUser(name.getText().toString(),
                            user1.getText().toString(), pass.getText().toString(),
                            cnfrmpass.getText().toString(),phone.getText().toString(),userlocation);


                    Toast.makeText(SignUpFarmer.this, " Successfully Registered", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpFarmer.this, LoginFarmer.class);
                    startActivity(intent);
                    finish();
                }

            }
        });




    }



    public void showPass(){
        rshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    cnfrmpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    cnfrmpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(SignUpFarmer.this, LoginFarmer.class);
        startActivity(intent);
        finish();
    }
}
