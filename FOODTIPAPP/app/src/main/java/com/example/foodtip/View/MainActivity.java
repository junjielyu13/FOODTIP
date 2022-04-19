package com.example.foodtip.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtip.DataTest.Login_dades;
import com.example.foodtip.Model.*;
import com.example.foodtip.R;

public class MainActivity extends AppCompatActivity {

    private Context context;
    public static FoodTip foodTip;
    public static Login_dades ld;
    private EditText userName;
    private EditText password;
    private Button login_but;
    private Button regi_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getBaseContext();
        ld = new Login_dades();
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.UserNameEditTextLogIn);
        password = findViewById(R.id.PasswordEditTextLogIn);
        login_but = findViewById(R.id.loginButton);
        regi_but = findViewById(R.id.RegisterButton);

        setLogInButtonsOnClickListeners();
        setRegisterButtonsOnClickListeners();
    }

    private void setRegisterButtonsOnClickListeners() {
        regi_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.getText().clear();
                password.getText().clear();
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });
    }

    private void setLogInButtonsOnClickListeners() {

        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String pwd = password.getText().toString();
                User user = ld.getUsersMap().getOrDefault(username, null);

                if (user != null) {
                    if (pwd.equals(user.getPassword())) {
                        Toast.makeText(context, "Log In sucessful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, HomePageActivity.class));
                    } else {
                        userName.getText().clear();
                        password.getText().clear();
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("Error!")
                                .setMessage("Wrong password")
                                .setIcon(R.mipmap.empty_img)
                                .create();
                        alertDialog.show();
                    }
                }else{
                    userName.getText().clear();
                    password.getText().clear();
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Error!")
                            .setMessage("Username does not exist")
                            .setIcon(R.mipmap.empty_img)
                            .create();
                    alertDialog.show();
                }
            }
        });
    }

}