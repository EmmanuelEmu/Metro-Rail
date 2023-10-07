package com.example.metrorail;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.ktx.Firebase;

import java.util.concurrent.TimeUnit;

public class OTP_verification extends AppCompatActivity {

    EditText inputNumber1, inputNumber2, inputNumber3, inputNumber4, inputNumber5, inputNumber6;
    TextView userPhoneNumber;
    Button verify;
    ProgressBar progressBar_verify;
    //String getBackendOTP;
    String backendOTP;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        phone = getIntent().getStringExtra("Phone number");

        FirebaseAuth mAuth1;
        mAuth1 = FirebaseAuth.getInstance();
        PhoneAuthOptions options1 = PhoneAuthOptions.newBuilder(mAuth1)
                .setPhoneNumber("+88" + phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTP_verification.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null){
                            verifyCode(code);
                        }
                        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.w(TAG, "onVerificationFailed", e);
                        Toast.makeText(OTP_verification.this, "Verification failed",Toast.LENGTH_SHORT );
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            backendOTP = verificationId;
                            Toast.makeText(OTP_verification.this, "Code sent successfully", Toast.LENGTH_SHORT);
                    }

                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options1);





        // Capturing the input field by their id
        inputNumber1 = findViewById(R.id.inputotp1);
        inputNumber2 = findViewById(R.id.inputotp2);
        inputNumber3 = findViewById(R.id.inputotp3);
        inputNumber4 = findViewById(R.id.inputotp4);
        inputNumber5 = findViewById(R.id.inputotp5);
        inputNumber6 = findViewById(R.id.inputotp6);

        userPhoneNumber = findViewById(R.id.showPhone);
        verify = findViewById(R.id.submitOTP);
        progressBar_verify = findViewById(R.id.progress_OTP_verify);
        userPhoneNumber.setText(String.format("+88%s",phone));
        //getBackendOTP = getIntent().getStringExtra("backendOTP");



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputNumber1.getText().toString().trim().isEmpty() && !inputNumber2.getText().toString().trim().isEmpty() && !inputNumber3.getText().toString().trim().isEmpty() && !inputNumber4.getText().toString().trim().isEmpty() && !inputNumber5.getText().toString().trim().isEmpty() && !inputNumber6.getText().toString().trim().isEmpty()){

                    String enterCodeOTP = inputNumber1.getText().toString().trim()+
                                    inputNumber2.getText().toString().trim()+
                                    inputNumber3.getText().toString().trim()+
                                    inputNumber4.getText().toString().trim()+
                                    inputNumber5.getText().toString().trim()+
                                    inputNumber6.getText().toString().trim();


                    if (backendOTP!=null){
                        progressBar_verify.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.INVISIBLE);
                        verifyCode(enterCodeOTP);

                    } else {
                        Toast.makeText(OTP_verification.this, "Please check your internet connection", Toast.LENGTH_SHORT);
                    }

                    //Toast.makeText(OTP_verification.this, "OTP verified", Toast.LENGTH_SHORT);
                } else{
                    Toast.makeText(OTP_verification.this, "Please enter all the number", Toast.LENGTH_SHORT);
                }
            }
        });
        
        numberOTP_move();

        // If user clicked onto the Resend OTP button
        TextView resendOTP;
        resendOTP = findViewById(R.id.textResendOTP);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth3;
                mAuth3 = FirebaseAuth.getInstance();
                PhoneAuthOptions options2 = PhoneAuthOptions.newBuilder(mAuth3)
                        .setPhoneNumber("+88"+phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OTP_verification.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                backendOTP = verificationId;
                            }
                        })
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options2);

            }
        });

    }


    private void verifyCode(String code){

        FirebaseAuth mAuth2;
        mAuth2 = FirebaseAuth.getInstance();

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                backendOTP, code
        );

        mAuth2.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar_verify.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);

                if (task.isSuccessful()){
                    Intent intent = new Intent(OTP_verification.this, userData.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else{
                    Toast.makeText(OTP_verification.this, "Enter the correct OTP", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    private void numberOTP_move() {
        inputNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputNumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputNumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputNumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}