package com.example.vishal.tipcalculatorchandrasekaran;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpRegister;
    private EditText emailRegister;
    private EditText nameRegister;
    private EditText passwordOneRegister;
    private ProgressBar progressBarRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameRegister = (EditText) findViewById(R.id.regName);
        emailRegister = (EditText) findViewById(R.id.regMail);
        passwordOneRegister = (EditText) findViewById(R.id.regPassword);
        mAuth = FirebaseAuth.getInstance();
        registerUser();
    }
    private void registerUser() {
        signUpRegister = (Button) findViewById(R.id.regBtn);
        progressBarRegister = (ProgressBar) findViewById(R.id.regProgressBarRegister);
        signUpRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();
                String passwordOne = passwordOneRegister.getText().toString().trim();
                if(name.isEmpty()){
                    nameRegister.setError("Name is required");
                    nameRegister.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    emailRegister.setError("Email is required");
                    emailRegister.requestFocus();
                    return;
                }
                if(passwordOne.isEmpty()){
                    passwordOneRegister.setError("Password is required");
                    passwordOneRegister.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailRegister.setError("Please enter a valid email address");
                    emailRegister.requestFocus();
                    return;
                }
                progressBarRegister.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, passwordOne).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarRegister.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "User registration has been successful", Toast.LENGTH_SHORT).show();
                            Intent c = new Intent(SignUpActivity.this, LoginActivity.class);
                            c.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(c);
                        }else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUpActivity.this, "This email is already registered", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

            }
        });
    }
}
