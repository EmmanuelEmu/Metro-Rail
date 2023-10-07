package com.example.metrorail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class journey_purchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_purchase);


        Intent intent = getIntent();
        String source, destination, journeyDate;

        source = intent.getStringExtra("sourceStation");
        destination = intent.getStringExtra("DestinationStation");
        journeyDate = intent.getStringExtra("Journey Date");


        System.out.println(source);
        System.out.println(destination);
        System.out.println(journeyDate);

    }
}