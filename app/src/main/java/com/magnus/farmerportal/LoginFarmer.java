package com.magnus.farmerportal;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;

public class LoginFarmer extends AppCompatActivity {

    EditText username1, pass;
    Button login;
    TextView regis;
    Cursor cursor;
    CheckBox show;
    DBHelperFarmer dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_consumer);




        //Intent intent1=getIntent();
        //final String[] position=intent1.getStringArrayExtra("Location");
        dbHelper = new DBHelperFarmer(this);
        db = dbHelper.getReadableDatabase();
        regis = (TextView) findViewById(R.id.regis);
        username1 = (EditText) findViewById(R.id.luser);
        pass = (EditText) findViewById(R.id.lpass);
        show = (CheckBox) findViewById(R.id.showPass);

        login = (Button)findViewById(R.id.login);
        showPass();

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFarmer.this, SignUpFarmer.class);
                //intent.putExtra("Location",position);
                startActivity(intent);
                finish();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor = db.rawQuery("SELECT * FROM " + DBHelper.USER_TABLE + " WHERE "
                                + DBHelper.COLUMN_USERNAME + " =? AND " + DBHelper.COLUMN_PASSWORD + " =?",
                        new String[]{username1.getText().toString(), pass.getText().toString()});

                if(username1.getText().toString().equals("")||
                        pass.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Username and Password can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        int id=cursor.getInt(cursor.getColumnIndex(DBHelperFarmer.ColumnID));
                        Toast.makeText(LoginFarmer.this, "Logged In succesfully!",
                                Toast.LENGTH_LONG).show();

                        if(dbHelper.getCrop(id).getCount()<=0) {
                            Intent intent = new Intent(LoginFarmer.this, CropsSelection.class);
                            intent.putExtra("Id", id);
                            startActivity(intent);
                            finish();
                        }
                        if (dbHelper.getCrop(id).getCount()>0){
                            Intent intent = new Intent(LoginFarmer.this, ResultFarmer.class);
                            intent.putExtra("Id", id);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Toast.makeText(LoginFarmer.this, "Invalid username or password!",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

    public void showPass(){
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    public void onBackPressed()
    {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
