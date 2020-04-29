package com.magnus.farmerportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FarmerActivity extends AppCompatActivity {
    TextView disp;
    Button done;
    Cursor cursor;
    DBHelperFarmer dbHelperFarmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        Intent intent = getIntent();
        //String cropSelected = intent.getStringExtra("cropSelected");
        final TextView crop=findViewById(R.id.textView2);
        String t1=intent.getStringExtra("CropSelected");
        final int id=intent.getIntExtra("Id",0);
        //Toast.makeText(this,Integer.toString(id), Toast.LENGTH_SHORT).show();
        crop.setText(t1);
        disp=(TextView)findViewById(R.id.display);
        done=(Button)findViewById(R.id.done);
        final EditText quantity=(EditText)findViewById(R.id.QuantityEditText);
        final EditText price=(EditText)findViewById(R.id.PriceeditText);
        dbHelperFarmer = new DBHelperFarmer(this);
        Button updateCrop=(Button)findViewById(R.id.updateCrop);

        updateCrop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(quantity.getText().toString().equals("") && price.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(quantity.getText().toString()) > 20 || Integer.parseInt(quantity.getText().toString()) < 0 )
                {
                    Toast.makeText(getApplicationContext(), "Please enter Quantity in the Range 1 and 20", Toast.LENGTH_SHORT).show();
                }

                else {
                    ArrayList<String> returnDetails = new ArrayList<String>();
                    returnDetails.add(quantity.getText().toString());
                    returnDetails.add(price.getText().toString());
                    setTextViewValues(returnDetails,disp);
                    Cursor res=dbHelperFarmer.getData();
                    dbHelperFarmer.addCropDetails(price.getText().toString(),quantity.getText().toString(),crop.getText().toString(),id);
                    Intent resultIntent = new Intent(FarmerActivity.this, CropsSelection.class);
                    resultIntent.putExtra("Id",id);
                    //resultIntent.putExtra("returnedDetails", returnDetails);
                    //setResult(RESULT_OK, resultIntent);
                    startActivity(resultIntent);
                    //finishActivity(2);
                    finish();
                }
            }

        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*    cursor = db.rawQuery("SELECT * FROM " + DBHelperFarmer.FARMER_DETAILS + " WHERE "
                                + DBHelperFarmer.COLUMN_PRICE + " =? AND " + DBHelperFarmer.COLUMN_CROP + " =? AND "+ DBHelperFarmer.COLUMN_QUANTITY+ " =?",
                        new String[]{price.getText().toString(), crop.getText().toString(),quantity.getText().toString()});
*/

              /*  Calendar calendar = Calendar.getInstance();
                //Returns current time in millis
                long timeMilli2 = calendar.getTimeInMillis();
                dbHelperFarmer.addDate(timeMilli2,id);*/
                if(quantity.getText().toString().equals("") && price.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(quantity.getText().toString()) > 20 || Integer.parseInt(quantity.getText().toString()) < 0 )
                {
                    Toast.makeText(getApplicationContext(), "Please enter Quantity in the Range 1 and 20", Toast.LENGTH_SHORT).show();
                }
                else {

                    dbHelperFarmer.addCropDetails(price.getText().toString(), quantity.getText().toString(), crop.getText().toString(),
                            id);
                    Intent resultIntent = new Intent(FarmerActivity.this, ResultFarmer.class);
                    resultIntent.putExtra("Id", id);
                    startActivity(resultIntent);
                    finish();
                }
            }
        });

    }
    public void setTextViewValues (ArrayList<String> vals, TextView text) {
        //Variable to hold all the values
        String output = "";

        for (int i = 0; i < vals.size(); i++) {
            //Append all the values to a string
            output += vals.get(i)+ " ";
        }

        //Set the textview to the output string
        text.setText(output);
    }
}
/*
ArrayList<String> images = new ArrayList<>();
images.add("amit");
Intent i = new Intent(Firstactivity.this, Secondactivity.class);
i.putExtra("key", images);
 */