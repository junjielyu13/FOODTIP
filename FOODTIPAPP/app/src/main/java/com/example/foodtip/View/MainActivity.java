package com.example.foodtip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtip.Model.*;
import com.example.foodtip.R;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private FoodTip foodTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        foodTip = FoodTip.getInstance();
        userName = findViewById(R.id.UserNameEditTextLogIn);
        password = findViewById(R.id.PasswordEditTextLogIn);
    }

    /**
     * set Register Button click listener to go to register Page
     * @param view
     */
    public void RegisterButtonOnClickListener(View view) {
        userName.getText().clear();
        password.getText().clear();
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * Login But event
     * @param view
     */
    public void LogInButtonOnClickListener(View view) {
        String email = userName.getText().toString();
        String pwd = password.getText().toString();
        if(!email.equals("") && !pwd.equals("")){
            foodTip.login_event(this,email,pwd);
        }else{
            Toast.makeText(this,"Empty Field",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Enter HomePageActivity
     */
    public void openHomePage(){
        startActivity(new Intent(this, HomePageActivity.class));
    }

}