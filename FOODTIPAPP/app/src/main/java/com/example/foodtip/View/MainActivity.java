package com.example.foodtip.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;

    private Button login_but, regi_but;
    private EditText acc_name, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setting(savedInstanceState);
    }
    private void setting(Bundle savedInstanceState){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        acc_name = findViewById(R.id.UserName);
        password = findViewById(R.id.Password);

        login_but = (Button) findViewById(R.id.loginButton);
        login_but.setOnClickListener((view) -> {
            FoodTip.login_event(this,acc_name.getText().toString(),password.getText().toString());
        });

        regi_but = (Button) findViewById(R.id.RegisterButton);
        regi_but.setOnClickListener((v)->{
            openRegisterPage();
        });
    }

    public void openMainPage(){
        Intent intent = new Intent(this,Main_View.class);
        startActivity(intent);
    }

    public void openRegisterPage(){
        Intent intent = new Intent(this,Register_View.class);
        startActivity(intent);
    }

}