package com.example.foodtip.View.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.example.foodtip.View.ChangePasswordActivity;
import com.example.foodtip.View.MainActivity;
import com.example.foodtip.databinding.SettingsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MySetting extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    /**
     * Go to change Password activity page
     * @param view
     */
    public void ChangePasswordButtonOnClick(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    /**
     * Logout and go to the log in page
     * @param view
     */
    public void LogoutButtonOnClickListener(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Sign Out successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
