package com.example.foodtip.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.MailSender;
import com.example.foodtip.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class forgotPasswordActivity extends AppCompatActivity {

    private FoodTip foodTip;
    private EditText emailEditText;
    private ProgressBar progressBar;
    private Button resetPwdButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpwd);
        foodTip = FoodTip.getInstance();
        emailEditText = findViewById(R.id.EmailEditTextForgotPassword);
        this.resetPwdButton = findViewById(R.id.resetPwdBtn);
        progressBar = findViewById(R.id.progressbarForgotPassword);
    }

    public void resetForgotPasswordOnClickListener(View view) {

        String emailText = emailEditText.getText().toString().trim();
        if(emailText.isEmpty()) {
            this.emailEditText.setError("Email is required!");
            this.emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            this.emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(forgotPasswordActivity.this, "Email doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        timer.schedule(task,500);

    }
}
