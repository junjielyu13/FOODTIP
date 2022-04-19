package com.example.foodtip.View;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.DataTest.Login_dades;
import com.example.foodtip.View.MainActivity;
import com.example.foodtip.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repeatPassword;
    private Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UserNameREGISTER);
        password = findViewById(R.id.PasswordREGISTER);
        repeatPassword = findViewById(R.id.RepeatPasswordREGISTER);
        //registerBtn = findViewById(R.id.registerButtonREGISTER);
    }

    public void onClickRegisterButtonREGISTER(View view) {
        String user_name = username.getText().toString();
        String pwd = password.getText().toString();
        String rePwd = repeatPassword.getText().toString();

        if((MainActivity.ld.getUsersMap().containsKey(user_name)) && pwd.equals(rePwd)){
            MainActivity.ld.addUsers(user_name,pwd);
            Toast.makeText(this, "Register Sucessful!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
