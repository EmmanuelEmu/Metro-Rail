package com.example.metrorail;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {

    Toolbar toolbar;
    EditText DOB;
    int year, month, day;

    Button create;
    EditText mobileNo, NID, dob, email_text, password_text,fName,lName;
    ProgressBar progressBar;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.reg_toolbar);
        DOB = findViewById(R.id.dateOfBirth);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Adding toolbar mechanism
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        DOB.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        create = findViewById(R.id.createbtn);
        mobileNo = findViewById(R.id.mobileNumber);
        NID = findViewById(R.id.NID);
        dob = findViewById(R.id.dateOfBirth);
        email_text = findViewById(R.id.reg_email);
        password_text = findViewById(R.id.reg_password);
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);




        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String email, password,  nid, hbd, firstName, lastName, phone;
                firstName = String.valueOf(fName.getText());
                lastName = String.valueOf(lName.getText());
                nid = String.valueOf(NID.getText());
                hbd = String.valueOf(dob.getText());
                email = String.valueOf(email_text.getText());
                password = String.valueOf(password_text.getText());
                phone = mobileNo.getText().toString().trim();




                //Hooking the progress bar
                progressBar = findViewById(R.id.progress_send_otp);


//                if (!phone.isEmpty()) {
//                    Toast.makeText(Registration.this, "You entered your phone number", Toast.LENGTH_LONG);
//                    if (phone.length() == 11){
//                        progressBar.setVisibility(View.VISIBLE);
//                        create.setVisibility(View.GONE);
//                        Intent intent = new Intent(getApplicationContext(), OTP_verification.class);
//                        intent.putExtra("Phone number", phone);
//                        startActivity(intent);


//                        mAuth = FirebaseAuth.getInstance();
////                        Intent intent = new Intent(getApplicationContext(), OTP_verification.class);
////                        startActivity(intent);
//
//
//                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                                .setPhoneNumber("+88" + phone)
//                                .setTimeout(60L, TimeUnit.SECONDS)
//                                .setActivity(Registration.this)
//                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
////                                        String code = phoneAuthCredential.getSmsCode();
////                                        if (code != null){
////                                            Intent intent = new Intent(getApplicationContext(), OTP_verification.class);
////                                            intent.putExtra("code", code);
////                                            startActivity(intent);
////                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                        Log.w(TAG, "onVerificationFailed", e);
//                                        Toast.makeText(Registration.this, "Verification failed",Toast.LENGTH_SHORT );
//                                    }
//
//                                    @Override
//                                    public void onCodeSent(@NonNull String verificationId,
//                                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                        Log.d(TAG, "onCodeSent:" + verificationId);
//                                        Intent intent = new Intent(Registration.this, OTP_verification.class);
////                                        intent.putExtra("mobile Number", phone);
////                                        intent.putExtra("backendOTP", verificationId);
//                                        startActivity(intent);
//                                    }
//
//                                })
//                                .build();
//
//                        PhoneAuthProvider.verifyPhoneNumber(options);

//                    }
//                    else {
//                        Toast.makeText(Registration.this, "Enter the valid phone number", Toast.LENGTH_SHORT);
//                    }
//                } else {
//                    Toast.makeText(Registration.this, "Please enter your phone number", Toast.LENGTH_LONG);
//                    mobileNo.requestFocus();
//                }


//                if(phone.isEmpty()){
//                    Toast.makeText(Registration.this, "Please enter the phone number", Toast.LENGTH_SHORT);
//
//                }else {
//                    if ((phone.toString().trim()).length() == 11){
//
//                        progressBar.setVisibility(View.VISIBLE);
//                        create.setVisibility(View.GONE);
//
//                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                                .setPhoneNumber(phone.toString().trim())
//                                .setTimeout(60L, TimeUnit.SECONDS)
//                                .setActivity(Registration.this)
//                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                    @Override
//                                    public void onCodeSent(@NonNull String verificationId,
//                                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                        Intent intent = new Intent(Registration.this, OTP_verification.class);
//                                        intent.putExtra("mobile Number", phone.toString().trim());
//                                        intent.putExtra("backendOTP", verificationId);
//                                        startActivity(intent);
//                                    }
//
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                                        progressBar.setVisibility(View.GONE);
//                                        create.setVisibility(View.VISIBLE);
//                                    }
//                                })
//                                .build();
//                        PhoneAuthProvider.verifyPhoneNumber(options);
//
//                    }
//                    else {
//                        Toast.makeText(Registration.this, "Enter the valid phone number", Toast.LENGTH_SHORT);
//                    }
//                }

                if(TextUtils.isEmpty(firstName)){
                    Toast.makeText(Registration.this, "Please enter your first Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(Registration.this, "Please enter your last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(Registration.this, "Please enter your phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(nid)){
                    Toast.makeText(Registration.this, "Please enter your NID number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(hbd)){
                    Toast.makeText(Registration.this, "Please enter your date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Registration.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Registration.this, "Please set your password", Toast.LENGTH_SHORT).show();
                    return;
                }


               // Creating a new user account
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //Saving the data to the realtime database in firebase
                                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(firstName, lastName, nid, hbd, email, phone);
                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
                                    referenceProfile.child(user.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(Registration.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Registration.this, home.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
//
//                                    progressBar.setVisibility(View.GONE);
//                                    Toast.makeText(Registration.this, "Registration successful.",
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(Registration.this, home.class);
//                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Registration.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}