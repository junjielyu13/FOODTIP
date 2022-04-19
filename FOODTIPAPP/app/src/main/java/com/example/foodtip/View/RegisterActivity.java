package com.example.foodtip.View;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.View.MainActivity;
import com.example.foodtip.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repeatPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UserNameREGISTER);
        password = findViewById(R.id.PasswordREGISTER);
        repeatPassword = findViewById(R.id.RepeatPasswordREGISTER);
    }

    public void onClickLoginButtonREGISTER(View view) {
        String user_name = username.getText().toString();
        String pwd = password.getText().toString();
        String rePwd = repeatPassword.getText().toString();

        if((!MainActivity.foodTip.getUsersMap().containsKey(user_name)) && pwd.equals(rePwd)){
            MainActivity.foodTip.addUsers(user_name,pwd);
            Toast.makeText(this, "Register Sucessful!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
