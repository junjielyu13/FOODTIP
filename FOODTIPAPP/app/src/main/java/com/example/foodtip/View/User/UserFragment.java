package com.example.foodtip.View.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodtip.databinding.UserBinding;
public class UserFragment extends Fragment {
    private UserBinding binding;

    private Button my_cousine_but;
    private Button my_favorite_but;
    private Button my_history_but;
    private Button setting_but;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = UserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setting();
        return root;
    }

    private void setting(){
        my_cousine_but = binding.MyCousine;
        my_cousine_but.setOnClickListener((v)->{
            open_my_cousine();
        });

        my_favorite_but = binding.MyFavorite;
        my_favorite_but.setOnClickListener((v)->{
            open_my_favorite();
        });

        my_history_but = binding.MyHistory;
        my_history_but.setOnClickListener((v)->{
            open_my_history();
        });

        setting_but = binding.Setting;
        setting_but.setOnClickListener((v)->{
            open_my_setting();
        });
    }

    private void open_my_setting() {
        Intent intent = new Intent(this.getContext(),MySetting.class);
        startActivity(intent);
    }

    private void open_my_history() {
        Intent intent = new Intent(this.getContext(),MyHistory.class);
        startActivity(intent);
    }

    private void open_my_favorite() {
        Intent intent = new Intent(this.getContext(),MyFavorite.class);
        startActivity(intent);
    }

    private void open_my_cousine() {
        Intent intent = new Intent(this.getContext(),MyCousine.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
