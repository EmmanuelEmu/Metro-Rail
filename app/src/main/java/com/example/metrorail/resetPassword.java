package com.example.metrorail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {


    Button resetButton, backButton;
    EditText resetEmail;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    String reset_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetButton = findViewById(R.id.passwordResetButton);
        backButton = findViewById(R.id.backButton);
        resetEmail = findViewById(R.id.resetEmail);
        progressBar = findViewById(R.id.resetProgressBar);

        mAuth = FirebaseAuth.getInstance();


        progressBar.setVisibility(View.GONE);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_email = resetEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(reset_email)){
                    ResetPassword();
                } else {
                    resetEmail.setError("Please enter the email");
                }
            }
        });






        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });



    }

    private void ResetPassword(){
        progressBar.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.GONE);

        mAuth.sendPasswordResetEmail(reset_email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(resetPassword.this, "Reset password mail sent successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(resetPassword.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}