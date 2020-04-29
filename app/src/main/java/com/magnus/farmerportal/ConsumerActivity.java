package com.magnus.farmerportal;

/*import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;*/
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConsumerActivity extends AppCompatActivity {
    /*  Button view, logo;
      int k = 0;
      DBHelper dbHelper;
      TextView show;
      EditText user, pass;*/
    AutoCompleteTextView selectedCrop;
    DBHelperFarmer dbHelperFarmer;
    SQLiteDatabase db;
    TextView listfarmer,priceEntry,quanityEntry,cropID,idConsumer;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        String[] crops={"Ragi","Rice","Onion","Carrot","Potato"};
        dbHelperFarmer = new DBHelperFarmer(this);
        db = dbHelperFarmer.getReadableDatabase();

        listfarmer=(TextView)findViewById(R.id.listNames) ;
        priceEntry=(TextView)findViewById(R.id.priceEntry);
        quanityEntry=(TextView)findViewById(R.id.quantityEntry);
        cropID=(TextView)findViewById(R.id.idValue);
        idConsumer=(TextView)findViewById(R.id.idConsumer);
        submit=(Button)findViewById(R.id.submit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, crops);
        selectedCrop = (AutoCompleteTextView)findViewById(R.id.selectCrop);
        selectedCrop.setThreshold(2);
        selectedCrop.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                selectedCrop.showDropDown();
                return false;
            }
        });
        selectedCrop.setAdapter(adapter);
    }

    public void showFarmerList(View view) {
        String cropName=selectedCrop.getText().toString();
        Cursor res = db.rawQuery("SELECT * FROM " + DBHelperFarmer.FARMER_DETAILS + " WHERE " + DBHelperFarmer.COLUMN_CROP + " =?", new
                String[]{cropName});
        StringBuilder stringB = new StringBuilder();
        StringBuilder stringI=new StringBuilder();
        StringBuilder stringP = new StringBuilder();
        StringBuilder stringQ = new StringBuilder();
        if(res!=null && res.getCount()>0) {
            while (res.moveToNext()) {
                String id = res.getString(4);
                Cursor c = db.rawQuery("SELECT * FROM " + DBHelperFarmer.USER_TABLE + " WHERE " + DBHelperFarmer.ColumnID + " =?", new
                        String[]{id});
                if (c != null && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        stringI.append(c.getString(0));
                        stringB.append(c.getString(1));
                        stringP.append(res.getString(1));
                        stringQ.append(res.getString(2));
                        //String username = c.getString(2);
                        stringI.append("\n");
                        stringB.append("\n");
                        stringP.append("\n");
                        stringQ.append("\n");
                    }
                    cropID.setText(stringI.toString());
                    listfarmer.setText(stringB.toString());
                    priceEntry.setText(stringP.toString());
                    quanityEntry.setText(stringQ.toString());
                    //listfarmer.setText(stringB.toString());
                }
            }
            // show.setText(stringB.toString());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="";
                String a=idConsumer.getText().toString();
                String cropName=selectedCrop.getText().toString();
                if (a==""){
                    Toast.makeText(ConsumerActivity.this,"Enter farmer you want",Toast.LENGTH_LONG);
                }
                else{
                    String number="",farmerUserName="",price="",quantity="",crop="",location="";
                    Cursor details = db.rawQuery("SELECT * FROM " + DBHelperFarmer.USER_TABLE + " WHERE " + DBHelperFarmer.ColumnID + " =?", new
                            String[]{a});
                    if (details != null && details.getCount() > 0) {
                        while (details.moveToNext()) {
                            id = details.getString(0);
                            Cursor c = db.rawQuery("SELECT * FROM " + DBHelperFarmer.FARMER_DETAILS + " WHERE " + DBHelperFarmer.FARMER_ID +  " =? AND " + DBHelperFarmer.COLUMN_CROP + " =?",
                                    new String[]{id,cropName});
                            if (c != null && c.getCount() > 0) {
                                while (c.moveToNext()) {
                                    number= details.getString(5);
                                    farmerUserName=details.getString(2);
                                    crop=c.getString(3);
                                    price=c.getString(1);
                                    quantity=c.getString(2);
                                    location=details.getString(6);

                                }
                            }

                        }
                    }

                    Intent intent=new Intent(ConsumerActivity.this,ResultConsumer.class);
                    intent.putExtra("farmer",farmerUserName);
                    //  Log.d("ed","ssssss");
                    intent.putExtra("id",id);
                    intent.putExtra("number",number);
                    intent.putExtra("price",price);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("crop",crop);
                    intent.putExtra("location",location);
                    startActivity(intent);
                }
            }
        });
    }

}

     /*   dbHelper = new DBHelper(this);

        view = (Button)findViewById(R.id.view);
        logo = (Button) findViewById(R.id.logout);
        show = (TextView)findViewById(R.id.showAll);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.password);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = dbHelper.getData();
                StringBuilder stringB = new StringBuilder();
                if(res!=null && res.getCount()>0){
                    while (res.moveToNext()){
                        stringB.append("Name: "+res.getString(0)+"\n");
                        stringB.append("Username: "+res.getString(1)+"\n");
                        stringB.append("Password: "+res.getString(2)+"\n");
                    }
                    show.setText(stringB.toString());
                    Toast.makeText(ConsumerActivity.this, "Data showed", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ConsumerActivity.this, "Data N/A", Toast.LENGTH_LONG).show();
                }
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerActivity.this);
                builder.setTitle("Info");
                builder.setMessage("Are you sure you want to logout ??");
                builder.setPositiveButton("Yes I'm sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(ConsumerActivity.this, LoginConsumer.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    public void onBackPressed()
    {
        k++;
        if(k == 1)
        {
            Toast.makeText(ConsumerActivity.this, "Press again to go previous activity.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConsumerActivity.this, LoginConsumer.class);
            startActivity(intent);
        }
        else
        {
            finish();
        }*/
