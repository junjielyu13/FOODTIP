package com.example.foodtip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtip.Model.*;
import com.example.foodtip.R;

public class MainActivity extends AppCompatActivity {

    public static FoodTip foodTip;
    private EditText userName;
    private EditText password;
    private Button login_but;
    private Button regi_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.UserNameEditText);
        password = findViewById(R.id.PasswordEditText);
        settingButtonsOnClickListeners();
    }

    private void settingButtonsOnClickListeners() {
        login_but = (Button) findViewById(R.id.loginButton);

        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String pwd = password.getText().toString();

                User user =  foodTip.getUser().getOrDefault(username,null);
                if (user != null){
                    if(password.equals(user.getPassword())){
                        Toast.makeText(this, "Log In sucessful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, HomePageActivity.class));
                    }
                }
            }
        });

        login_but.setOnClickListener((view) -> {
            openMainPage();
        });

        regi_but = (Button) findViewById(R.id.RegisterButton);
        regi_but.setOnClickListener((v) -> {
            openRegisterPage();
        });
    }

    private void openMainPage() {
        Intent intent = new Intent(this, Main_View.class);
        startActivity(intent);
    }

    private void openRegisterPage() {
        Intent intent = new Intent(this, Main_View.class);
        startActivity(intent);
    }
}

/*

public class MainActivity extends AppCompatActivity {

    public static FoodTip foodTip;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        testLogIn();

        userName = findViewById(R.id.UserNameEditText);
        password = findViewById(R.id.PasswordEditText);

    }

    /**
     * Log in Button Click Listener
     * @param view log in view
     */
    /*
public void onClickLoginButton(View view) {
    String username = userName.getText().toString();
    String password = this.password.getText().toString();

    User user =  foodTip.getUsersMap().getOrDefault(username,null);
    if (user != null){
        if(password.equals(user.getPassword())){
            Toast.makeText(this, "Log In sucessful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}

    /**
     * Register button Click Listener
     * @param view Log in View
     */
    /*
    public void OnClickRegisterButton(View view) {
        userName.getText().clear();
        password.getText().clear();
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void testLogIn(){

    }


}
 */