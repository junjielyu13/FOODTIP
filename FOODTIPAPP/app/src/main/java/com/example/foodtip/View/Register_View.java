package com.example.foodtip.View;



import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.example.foodtip.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register_View extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EditText user_txt, password_txt, repeat_password_txt;
    private Button register_but;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setting();
    }
    private void setting(){
        user_txt = findViewById(R.id.UserName);
        password_txt = findViewById(R.id.Password);
        repeat_password_txt = findViewById(R.id.RepeatPassword);
        register_but = findViewById(R.id.RegisterButton);

        register_but.setOnClickListener((v)->{
            register(user_txt.getText().toString(),password_txt.getText().toString(),repeat_password_txt.getText().toString(),user_txt.getText().toString());
        });

    }

    private void register(String email, String password,String repeat_password, String user_name) {
        FirebaseUser user = FoodTip.CreatNewUser(Register_View.this,email,password,repeat_password,user_name);
        if(user != null){
            updateUI(user);
        }else{
            Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show();
        }
    }
    private void updateUI(FirebaseUser user) {

    }
}
