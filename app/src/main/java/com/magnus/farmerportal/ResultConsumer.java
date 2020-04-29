package com.magnus.farmerportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ResultConsumer extends AppCompatActivity {
    Button confirm;
    TextView name,phone,crop,priceF,quantityF,locationF;
    String quantity;
    DBHelperFarmer dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id;
    String cropSelected,farmerName,phoneNumber,location;
    Button confirmConsumer;
    String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_result);

        dbHelper = new DBHelperFarmer(this);
        db = dbHelper.getReadableDatabase();
        name=(TextView)findViewById(R.id.nameF);
        phone=(TextView)findViewById(R.id.phoneF);
        crop=(TextView)findViewById(R.id.cropC);
        priceF=(TextView)findViewById(R.id.priceF);
        quantityF=(TextView)findViewById(R.id.quantityF);
        locationF=(TextView)findViewById(R.id.locationF);
        confirm=(Button)findViewById(R.id.confirm);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        farmerName=intent.getStringExtra("farmer");
        phoneNumber=intent.getStringExtra("number");
        price=intent.getStringExtra("price");
        quantity=intent.getStringExtra("quantity");
        cropSelected=intent.getStringExtra("crop");
        location=intent.getStringExtra("location");

        name.setText(farmerName);
        phone.setText(phoneNumber);
        crop.setText(cropSelected);
        priceF.setText(price+"/kg");
        quantityF.setText(quantity);
        locationF.setText(location);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String num=phone.getText().toString();

                intent.setData(Uri.parse("tel:"+num));
                startActivity(intent);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quanF=quantityF.getText().toString();
                if(quanF.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(quanF)>Integer.parseInt(quantity)){
                    Toast.makeText(getApplicationContext(), "Please enter quantity less than or equal to "+quantity, Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(quanF)==Integer.parseInt(quantity)){
                 /* SQLiteDatabase db=dbHelper.getWritableDatabase();
                  db.execSQL("DELETE FROM " + DBHelperFarmer.FARMER_DETAILS + " WHERE "
                                  + DBHelperFarmer.FARMER_ID + " =? AND " + DBHelperFarmer.COLUMN_CROP + " =?",
                          new String[]{id, cropSelected});

                  db.close();*/
                    cursor=dbHelper.deleteCrop(Integer.parseInt(id),cropSelected);
                    int computePrice=Integer.parseInt(quantity)*Integer.parseInt(price);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ResultConsumer.this);
                    builder.setTitle("Info");
                    builder.setMessage("Are you sure to confirm price of "+ String.valueOf(computePrice));
                    builder.setPositiveButton("Yes I'm sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext()," Confirmed Payment!",Toast.LENGTH_LONG);
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
                else {
                    int remain = Integer.parseInt(quantity) - Integer.parseInt(quanF);
                    int computePrice=Integer.parseInt(quanF)*Integer.parseInt(price);
                    String remainQuantity= String.valueOf(remain);

                 /* SQLiteDatabase db=dbHelper.getWritableDatabase();
                  db.execSQL("UPDATE " +DBHelperFarmer.FARMER_DETAILS +" SET "+ DBHelperFarmer.COLUMN_QUANTITY +" =?"
                          + " WHERE "+ DBHelperFarmer.FARMER_ID +" =? AND "+ DBHelperFarmer.COLUMN_CROP +
                          " =?",  new String[]{remainQuantity,id,cropSelected});

                  db.close();*/
                    cursor = dbHelper.updateCrop(remainQuantity, id, cropSelected);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ResultConsumer.this);
                    builder.setTitle("Info");
                    builder.setMessage("Are you sure to confirm price of "+ String.valueOf(computePrice));
                    builder.setPositiveButton("Yes I'm sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext()," Confirmed Payment!",Toast.LENGTH_LONG);
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
            }
        });

    }
}