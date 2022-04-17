package com.example.foodtip.View.User;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.databinding.SettingsBinding;

public class MySetting extends AppCompatActivity {
    private SettingsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
