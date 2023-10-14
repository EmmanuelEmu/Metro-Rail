package com.example.metrorail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.metrorail.databinding.ActivityMainBinding;
import com.example.metrorail.databinding.ActivityPaymentBinding;
import com.help5g.uddoktapaysdk.UddoktaPay;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class payment extends AppCompatActivity {



    private ActivityPaymentBinding binding;

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

    LinearLayout layout, webLayout;
    WebView paymentView;
    String userFullName, userEmail, userAmount;
    EditText FullName, Email, Amount;
    Button paymentBtn;
    TextView paymentResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
//        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        getSupportActionBar().hide();


        paymentBtn = findViewById(R.id.payButton);
        layout = findViewById(R.id.uiLayout);
        webLayout = findViewById(R.id.webLayout);
        paymentResult = findViewById(R.id.paymentResult);
        paymentView = findViewById(R.id.payWebView);


        Intent intent = getIntent();
        userFullName= intent.getStringExtra("userName");
        userEmail = intent.getStringExtra("email");
        userAmount = intent.getStringExtra("amount");


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.setVisibility(View.GONE);
                webLayout.setVisibility(View.VISIBLE);




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
                        storedFullName = userFullName;
                        storedEmail = userEmail;
                        storedAmount = userAmount;
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
                                if ("COMPLETED".equals(status)) {
                                    // Handle payment completed case
                                    layout.setVisibility(View.VISIBLE);
                                    webLayout.setVisibility(View.GONE);
                                    paymentResult.setText("Payment Status: "+ "Complete" + "\n"+"Name: "+storedFullName + "\n"+"Amount:"+storedAmount);
                                } else if ("PENDING".equals(status)) {
                                    // Handle payment pending case
                                    layout.setVisibility(View.VISIBLE);
                                    webLayout.setVisibility(View.GONE);
                                    paymentResult.setText("Payment Status: "+ "PENDING" + "\n"+"Name: "+storedFullName + "\n"+"Amount:"+storedAmount);
                                } else if ("ERROR".equals(status)) {
                                    // Handle payment error case
                                }
                            }
                        });
                    }
                };

                UddoktaPay uddoktapay = new UddoktaPay(paymentView, paymentCallback);
                uddoktapay.loadPaymentForm(API_KEY, userFullName, userEmail, userAmount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);
            }
        });
    }
}