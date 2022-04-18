package com.example.foodtip.View.User;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.databinding.MyRecipeViewBinding;

public class MyCousine extends AppCompatActivity {
    private MyRecipeViewBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MyRecipeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
