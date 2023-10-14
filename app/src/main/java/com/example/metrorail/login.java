package com.example.metrorail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class login extends AppCompatActivity {

    TextView regBtn, forgotButton;
    EditText email_Text, password_Text;
    Button submitBtn;
    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_Text = findViewById(R.id.email);
        password_Text = findViewById(R.id.password);
        submitBtn = findViewById(R.id.submit_button);
        regBtn = findViewById(R.id.Register);
        forgotButton = findViewById(R.id.forgotbtn);
        mAuth = FirebaseAuth.getInstance();


        //Initially hide the password
        password_Text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), resetPassword.class);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(email_Text.getText());
                password = String.valueOf(password_Text.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                    email_Text.setError("email is required");
                    email_Text.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(login.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    password_Text.setError("Give password");
                    password_Text.requestFocus();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(login.this, "Login successful.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    //updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });


            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}