package com.example.metrorail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int year, month, day;
    Button searchButton;
    String sourceStation, DestinationStation, DOJourney;
    EditText journeyDate,JD;
    Spinner spinner_source, spinner_destination;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Navigation button code
        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        searchButton = findViewById(R.id.searchBtn);



        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);






        //Logout from the home page
        //MenuItem logout;
        //logout = findViewById(R.id.nav_logout);
//        EditText logout;
//        logout = findViewById(R.id.nav_logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), login.class);
//                startActivity(intent);
//                finish();
//            }
//        });





        //Spinner 1

        spinner_source = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> src_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.source,
                android.R.layout.simple_spinner_item
        );


        src_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_source.setAdapter(src_adapter);
        spinner_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem1 = parentView.getItemAtPosition(position).toString();
                sourceStation = selectedItem1;
                //Toast.makeText(getApplicationContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


        //Spinner-2
        spinner_destination = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> des_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.destination,
                android.R.layout.simple_spinner_item
        );


        des_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_destination.setAdapter(des_adapter);
        spinner_destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem2 = parentView.getItemAtPosition(position).toString();
                DestinationStation = selectedItem2;
                //Toast.makeText(getApplicationContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });



        //Adding calender for select journey date

        journeyDate = findViewById(R.id.journeyDate);
        final Calendar calendar = Calendar.getInstance();
        journeyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(home.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        journeyDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //Getting the journey date in String format
        JD = findViewById(R.id.journeyDate);
        DOJourney = String.valueOf(JD.getText());




        //action of button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action for spinner-1
//                spinner_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        // Handle the selected item here
//                        String selectedItem1 = parentView.getItemAtPosition(position).toString();
//                        sourceStation = selectedItem1;
//                        //Toast.makeText(getApplicationContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        // Do nothing here
//                    }
//                });
//
//
//                //action for spinner-2
//                spinner_destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        // Handle the selected item here
//                        String selectedItem2 = parentView.getItemAtPosition(position).toString();
//                        DestinationStation = selectedItem2;
//                        //Toast.makeText(getApplicationContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        // Do nothing here
//                    }
//                });
//
                if(sourceStation == "Select Departure point"){
                    Toast.makeText(home.this, "Please select Departure point", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(sourceStation == "Select Destination point"){
                    Toast.makeText(home.this, "Please select Destination point", Toast.LENGTH_SHORT).show();
                    return;
                }



                Intent intent = new Intent(getApplicationContext(), journey_purchase.class);
                intent.putExtra("sourceStation", sourceStation);
                intent.putExtra("DestinationStation", DestinationStation);
                intent.putExtra("Journey Date", DOJourney);
                startActivity(intent);


            }
        });



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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject stationNumber = obj.getJSONObject("station-"+(i+1));
                String stationName = stationNumber.getString("st_name");
                stations.offer(stationName);
//                list.put("station number","station"+(i+1));
//                list.put("station Name", stationName);
//                arrayList.add(list);
                
            }
//            System.out.println(arrayList);
//            System.out.println(list);
//            System.out.println(arrayList.get(0).get("station Name"));
            System.out.println(stations.peek());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}