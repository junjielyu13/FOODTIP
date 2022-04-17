package com.example.foodtip.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.foodtip.R;

public class MainActivity extends AppCompatActivity {

    private Button login_but;
    private Button regi_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setting(savedInstanceState);
    }
    private void setting(Bundle savedInstanceState){
        login_but = (Button) findViewById(R.id.loginButton);
        login_but.setOnClickListener((view) -> {
            openMainPage();
        });

        regi_but = (Button) findViewById(R.id.RegisterButton);
        regi_but.setOnClickListener((v)->{
            openRegisterPage();
        });
    }

    private void openMainPage(){
        Intent intent = new Intent(this,Main_View.class);
        startActivity(intent);
    }

    private void openRegisterPage(){
        Intent intent = new Intent(this,Main_View.class);
        startActivity(intent);
    }
}