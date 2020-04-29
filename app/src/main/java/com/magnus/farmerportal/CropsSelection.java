package com.magnus.farmerportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CropsSelection extends AppCompatActivity {


    private Object ArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_selection);

        Intent intent1 = getIntent();
        final int Farmerid=intent1.getIntExtra("Id",0);
        //Toast.makeText(this,Integer.toString(Farmerid), Toast.LENGTH_SHORT).show();

        final ListView cropNames=(ListView)findViewById(R.id.cropNames);
        final ArrayList<String> crops=new ArrayList<String>(Arrays.asList("Ragi","Rice","Onion","Carrot","Potato"));


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,crops);
        cropNames.setAdapter(arrayAdapter);
        cropNames.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(CropsSelection.this, crops.get(position)+" selected", Toast.LENGTH_SHORT).show();
                String selectedFromList = (String) cropNames.getItemAtPosition(position);
                Intent intent=new Intent(CropsSelection.this, FarmerActivity.class);
                intent.putExtra("CropSelected", selectedFromList);
                intent.putExtra("Id",Farmerid);
                startActivity(intent);
                finish();

            }
        });

        //ArrayList<String> RecievedDetails = (ArrayList<String>) getIntent().getSerializableExtra("key");
        //Toast.makeText(this,RecievedDetails.get(0) , Toast.LENGTH_SHORT).show();

    }
/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if(resultCode==RESULT_OK)
            {
                ArrayList<String> RecievedDetails =( ArrayList<String>) getIntent().getStringArrayListExtra("returnedDetails");

            }
        }
    }*/
}