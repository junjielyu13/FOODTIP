package com.example.foodtip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtip.DataTest.Login_dades;
import com.example.foodtip.Model.*;
import com.example.foodtip.R;

public class MainActivity extends AppCompatActivity {

    public static FoodTip foodTip;
    public static Login_dades ld;
    private EditText userName;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ld = new Login_dades();
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.UserNameEditTextLogIn);
        password = findViewById(R.id.PasswordEditTextLogIn);

    }

    public void RegisterButtonOnClickListener(View view) {
        userName.getText().clear();
        password.getText().clear();
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void LogInButtonOnClickListener(View view) {
        final int minim_pwd_length = 8;
        final int max_pwd_length = 16;
        String username = userName.getText().toString();
        String pwd = password.getText().toString();
        User user = ld.getUsersMap().getOrDefault(username, null);
        if (user != null) {
            if (pwd.length() > max_pwd_length && pwd.length() < minim_pwd_length) {
                showAlertDialog("Error!","Password length must be more than 8 characters or less equal than 16 characters",R.mipmap.ic_launcher);
            } else {
                if (pwd.equals(user.getPassword())) {
                    Toast.makeText(this, "Log In sucessful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomePageActivity.class));
                } else {
                    showAlertDialog("Error!","Wrong password",R.mipmap.empty_img);
                }
            }
        } else {
            showAlertDialog("Error","Username does not exist",R.mipmap.empty_img);

        }
    }

    private void showAlertDialog(String title, String message, int icon){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userName.getText().clear();
                        password.getText().clear();
                    }
                })
                .create();
        alertDialog.show();
    }
}