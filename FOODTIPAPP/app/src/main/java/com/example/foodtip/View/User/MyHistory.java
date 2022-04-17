package com.example.foodtip.View.User;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.databinding.MyHistoryViewBinding;
public class MyHistory extends AppCompatActivity {
    private MyHistoryViewBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MyHistoryViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
