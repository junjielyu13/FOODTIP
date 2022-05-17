package com.example.foodtip.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class forgotPasswordActivity extends AppCompatActivity {

    private FoodTip foodTip;
    private EditText emailEditText;
    private EditText checkNumberEditText;
    private Button sendCheckNumberBtn;
    private Button checkBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpwd);
        foodTip = FoodTip.getInstance();
        emailEditText = findViewById(R.id.EmailEditTextForgotPassword);
        checkNumberEditText = findViewById(R.id.CheckNumberEditText);
        this.sendCheckNumberBtn = findViewById(R.id.sendCheckNumberBtn);
        this.checkBtn = findViewById(R.id.checkBtn);
        emailEditText.setText("zhihanlin29@gmail.com");
    }

    public void SendCheckNumberOnClickListener(View view){
        String title = "Title";
        String htmlContent = "YCY";

        String emailText = emailEditText.getText().toString();

        List<String> receivers = new ArrayList<>();
        receivers.add(emailText);
        MailSender mailSender = new MailSender();
        mailSender.sendMail(title,htmlContent,receivers);
        this.sendCheckNumberBtn.setVisibility(View.GONE);
        this.checkBtn.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Send Check Number", Toast.LENGTH_SHORT).show();



    }

    public void CheckButtonOnClickListener(View view) {
        //String checkNumberText = checkNumberEditText.getText().toString();

    }
}
