package com.example.metrorail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.example.metrorail.databinding.ActivityMainBinding;
import com.help5g.uddoktapaysdk.UddoktaPay;

public class journey_purchase extends AppCompatActivity {

    Toolbar purchase_toolbar;
    EditText journeyTime;
    TextView showJourneyTime;
    TextView passengers, totalFair;
    int hours, minutes, estimatedCounter, passengersNumber, totalTravelFair;
    String travelFair = "0",amount;
    String source, destination, journeyDate, email, userName;




    private ActivityMainBinding binding;

    private static final String API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50";
    private static final String CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://uddoktapay.com";
    private static final String CANCEL_URL = "https://uddoktapay.com";

    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;


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


        //Setting the time
        journeyTime = findViewById(R.id.timeInput);
        journeyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hours = calendar.get(Calendar.HOUR_OF_DAY);
                minutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(journey_purchase.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm = (hourOfDay < 12) ? "AM" : "PM";
                        int hour = (hourOfDay < 1) ? 12 : hourOfDay;
                        journeyTime.setText(hour + ":" + minutes + " " + amPm);
                        String time = hour + ":" + minutes + " " + amPm;
                        showJourneyTime = findViewById(R.id.showJourneyTime);
                        showJourneyTime.setText(time);

                    }
                }, hours, minutes, true);

                timePickerDialog.show();
            }
        });


        Intent intent = getIntent();

        source = intent.getStringExtra("sourceStation");
        destination = intent.getStringExtra("DestinationStation");
        journeyDate = intent.getStringExtra("Journey Date");
        email = intent.getStringExtra("email");


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
            JSONArray jsonArray = jsonObject.getJSONArray("stations");

//            HashMap<String, String> list  = new HashMap<>();
//            ArrayList< HashMap<String, String>> arrayList = new ArrayList<>();
            Queue<String> stations = new LinkedList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject sourceStationNumber = obj.getJSONObject("station-" + (i + 1));
                String sourceStationName = sourceStationNumber.getString("st_name");
                if (source.equals(sourceStationName)) {
                    JSONArray destinationArray = sourceStationNumber.getJSONArray("destination");
                    for (int j = 0; j < destinationArray.length(); j++) {
                        JSONObject destinationStationSingleArrayElement = destinationArray.getJSONObject(j);
                        JSONObject destinationObject = destinationStationSingleArrayElement.getJSONObject("des-" + (j + 1));
                        String destinationStation = destinationObject.getString("des_st");
                        if (destination.equals(destinationStation)) {
                            travelFair = destinationObject.getString("fair");
                            estimatedCounter = j;
//                            System.out.println(travelFair);
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

        TextView sourceStation, destinationStation, showJourneyDate;
        EditText timeInput;
        sourceStation = findViewById(R.id.sourceStation);
        destinationStation = findViewById(R.id.destinationStation);
        showJourneyDate = findViewById(R.id.showJourneyDate);

        sourceStation.setText(source);
        destinationStation.setText(destination);
        showJourneyDate.setText(journeyDate);


        //set total travel fair acording to the passenger
        Button increment, decrement;

        increment = findViewById(R.id.incrementPassenger);
        decrement = findViewById(R.id.decrementPassenger);
        passengers = findViewById(R.id.numberOfPassenger);
        totalFair = findViewById(R.id.totalFair);


        String passengerNumberString = passengers.getText().toString().trim();
        passengersNumber = Integer.parseInt(passengerNumberString);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passengersNumber <= 3) {
                    passengersNumber++;
                }
                updatePassenger();

            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passengersNumber > 0) {
                    passengersNumber--;
                    updatePassenger();
                }
            }
        });

        //Calculating total travel fair
        userName = "Hola" + "Amigo";
        Intent paymentIntent = new Intent();
        paymentIntent.putExtra("userName",userName);
        paymentIntent.putExtra("email", email);
        paymentIntent.putExtra("amount", totalTravelFair);



        //Adding payment method
        Button purchaseButton;
        purchaseButton = findViewById(R.id.purchaseBtn);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set your metadata values in the map
                Map<String, String> metadataMap = new HashMap<>();
                metadataMap.put("CustomMetaData1", "Meta Value 1");
                metadataMap.put("CustomMetaData2", "Meta Value 2");
                metadataMap.put("CustomMetaData3", "Meta Value 3");

                UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
                    @Override
                    public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                                String paymentMethod, String senderNumber, String transactionId,
                                                String date, Map<String, String> metadataValues, String fee,String chargeAmount) {
                        // Callback method triggered when the payment status is received from the payment gateway.
                        // It provides information about the payment transaction.
                        storedFullName = userName;
                        storedEmail = email;
                        storedAmount = String.valueOf(totalTravelFair);
                        storedInvoiceId = invoiceId;
                        storedPaymentMethod = paymentMethod;
                        storedSenderNumber = senderNumber;
                        storedTransactionId = transactionId;
                        storedDate = date;
                        storedFee = fee;
                        storedChargedAmount = chargeAmount;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Clear previous metadata values to avoid duplication
                                storedMetaKey1 = null;
                                storedMetaValue1 = null;
                                storedMetaKey2 = null;
                                storedMetaValue2 = null;
                                storedMetaKey3 = null;
                                storedMetaValue3 = null;

                                // Iterate through the metadata map and store the key-value pairs
                                for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                                    String metadataKey = entry.getKey();
                                    String metadataValue = entry.getValue();

                                    if ("CustomMetaData1".equals(metadataKey)) {
                                        storedMetaKey1 = metadataKey;
                                        storedMetaValue1 = metadataValue;
                                    } else if ("CustomMetaData2".equals(metadataKey)) {
                                        storedMetaKey2 = metadataKey;
                                        storedMetaValue2 = metadataValue;
                                    } else if ("CustomMetaData3".equals(metadataKey)) {
                                        storedMetaKey3 = metadataKey;
                                        storedMetaValue3 = metadataValue;
                                    }
                                }

                                // Update UI based on payment status
//                                if ("COMPLETED".equals(status)) {
//                                    // Handle payment completed case
//                                    binding.uiLayout.setVisibility(View.VISIBLE);
//                                    binding.webLayout.setVisibility(View.GONE);
//                                    binding.paymentResult.setText("Payment Status: "+ "Complete" + "\n"+"Name: "+storedFullName + "\n"+"Amount:"+storedAmount);
//                                } else if ("PENDING".equals(status)) {
//                                    // Handle payment pending case
//                                    binding.uiLayout.setVisibility(View.VISIBLE);
//                                    binding.webLayout.setVisibility(View.GONE);
//                                    binding.paymentResult.setText("Payment Status: "+ "PENDING" + "\n"+"Name: "+storedFullName + "\n"+"Amount:"+storedAmount);
//                                } else if ("ERROR".equals(status)) {
//                                    // Handle payment error case
//                                }
                            }
                        });
                    }
                };

                WebView webView = findViewById(R.id.payWebView);
                amount = String.valueOf(totalTravelFair);
                UddoktaPay uddoktapay = new UddoktaPay(webView, paymentCallback);
                uddoktapay.loadPaymentForm(API_KEY, userName, email, amount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);
            }
        });



    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    private void updatePassenger() {
//        passengers = findViewById(R.id.passengerNumber);
        totalTravelFair = Integer.parseInt(travelFair);
        totalTravelFair *= passengersNumber;
        passengers.setText(String.valueOf(passengersNumber));
        totalFair.setText(String.valueOf(totalTravelFair));
    }
}