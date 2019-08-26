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

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginBtn);
        signUpButton = (Button) findViewById(R.id.signUpButtonLogin);
        email = (EditText) findViewById(R.id.emailEditTextLogin);
        password = (EditText) findViewById(R.id.passwordEditTextLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressLogin);
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cool = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(cool);
            }
        });

        userLogin();
    }
    private void userLogin(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = email.getText().toString().trim();
                String passwordInfo = password.getText().toString().trim();

                if(emailID.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailID).matches()){
                    email.setError("Please enter a valid email id");
                    email.requestFocus();
                    return;
                }
                if(passwordInfo.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(emailID, passwordInfo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent b = new Intent(LoginActivity.this,TipCalculatorActivity.class);
                            b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(b);
                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
