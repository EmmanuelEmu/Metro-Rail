package com.example.metrorail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class journey_purchase extends AppCompatActivity {

    Toolbar purchase_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_purchase);



//      Handling the toolbar action for ticket purchasing activity
        purchase_toolbar = findViewById(R.id.purchase_toolbar);
        setSupportActionBar(purchase_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        purchase_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        Intent intent = getIntent();
        String source, destination, journeyDate;

        source = intent.getStringExtra("sourceStation");
        destination = intent.getStringExtra("DestinationStation");
        journeyDate = intent.getStringExtra("Journey Date");



        String jsonData = null;

        try {
            // Open the JSON file from the assets folder
            InputStream inputStream = getAssets().open("data.json");

            // Create a BufferedReader to read the JSON file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Read each line of the JSON file and append it to the StringBuilder
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // Close the reader
            reader.close();

            // Convert the JSON data to a string
            jsonData = stringBuilder.toString();
            System.out.println(jsonData);
            // Now, jsonData contains the JSON content as a string
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Parse the JSON data into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray  = jsonObject.getJSONArray("stations");

//            HashMap<String, String> list  = new HashMap<>();
//            ArrayList< HashMap<String, String>> arrayList = new ArrayList<>();
            Queue<String> stations = new LinkedList<>();
            String travelFair = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject sourceStationNumber = obj.getJSONObject("station-"+(i+1));
                String sourceStationName = sourceStationNumber.getString("st_name");
                if (source.equals(sourceStationName)){
                    JSONArray destinationArray = sourceStationNumber.getJSONArray("destination");
                    for (int j = 0; j < destinationArray.length(); j++) {
                        JSONObject destinationStationSingleArrayElement = destinationArray.getJSONObject(j);
                        JSONObject destinationObject = destinationStationSingleArrayElement.getJSONObject("des-"+(j+1));
                        String destinationStation = destinationObject.getString("des_st");
                        if (destination.equals(destinationStation)){
                            travelFair = destinationObject.getString("fair");
                            System.out.println(travelFair);
                            break;
                        }
                    }

                }
//                list.put("station number","station"+(i+1));
//                list.put("station Name", stationName);
//                arrayList.add(list);

            }
//            System.out.println(arrayList);
//            System.out.println(list);
//            System.out.println(arrayList.get(0).get("station Name"));
            System.out.println(stations);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
}