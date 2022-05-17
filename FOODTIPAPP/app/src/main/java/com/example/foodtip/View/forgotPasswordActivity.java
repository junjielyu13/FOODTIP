package com.example.foodtip.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;

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
    }

    public void SendCheckNumberOnClickListener(View view) {
        String emailText = emailEditText.getText().toString();
        /*
        Test Version
         */
        String checkNumberText = "123456";
        checkNumberEditText.setText(checkNumberText);
        Toast.makeText(this, "Send Check Number", Toast.LENGTH_SHORT).show();
        this.sendCheckNumberBtn.setVisibility(View.GONE);
        this.checkBtn.setVisibility(View.VISIBLE);

    }

    public void CheckButtonOnClickListener(View view) {
        String checkNumberText = checkNumberEditText.getText().toString();
        if (checkNumberText.equals("123456")){
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }
}
