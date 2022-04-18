package com.example.foodtip.View.User;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.databinding.MyFavoriteViewBinding;

public class MyFavorite extends AppCompatActivity {
    private MyFavoriteViewBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MyFavoriteViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
