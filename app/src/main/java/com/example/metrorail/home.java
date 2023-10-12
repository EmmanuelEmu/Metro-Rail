package com.example.metrorail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
    String sourceStation, DestinationStation, DOJourney, selectedItem1, selectedItem2;
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
                selectedItem1 = parentView.getItemAtPosition(position).toString();
                sourceStation = selectedItem1;
//                Toast.makeText(getApplicationContext(), "Selected: " + selectedItem1, Toast.LENGTH_SHORT).show();
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
                selectedItem2 = parentView.getItemAtPosition(position).toString();
                DestinationStation = selectedItem2;
//                Toast.makeText(getApplicationContext(), "Selected: " + selectedItem2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });



        //Adding calender for select journey date

        journeyDate = findViewById(R.id.journeyDate);
        Calendar calendar = Calendar.getInstance();
        journeyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(home.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        journeyDate.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //Getting the journey date in String format





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
                System.out.println(selectedItem1);
                System.out.println(selectedItem2);
                String msg1 = "Select Departure point";
                String msg2 = "Select Destination point";
                if(sourceStation.equals(msg1)){
                    Toast.makeText(home.this, "Please select Departure point", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(DestinationStation.equals(msg2)){
                    Toast.makeText(home.this, "Please select Destination point", Toast.LENGTH_SHORT).show();
                    return;
                }


                JD = findViewById(R.id.journeyDate);
                DOJourney = String.valueOf(JD.getText());


                Intent getEmail = getIntent();
                String emailData = getEmail.getStringExtra("email");

                Intent intent = new Intent(getApplicationContext(), journey_purchase.class);
                intent.putExtra("sourceStation", sourceStation);
                intent.putExtra("DestinationStation", DestinationStation);
                intent.putExtra("Journey Date", DOJourney);
                intent.putExtra("email", emailData);
                startActivity(intent);


            }
        });



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
        if (item.getItemId() == R.id.nav_rate){
            rate_us_dialouge rateUs = new rate_us_dialouge(home.this);
            rateUs.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent,getTheme())));
            rateUs.setCancelable(false);
            rateUs.show();

        }
        return true;
    }
}