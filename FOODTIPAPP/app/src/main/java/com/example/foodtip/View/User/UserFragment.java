package com.example.foodtip.View.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.User;
import com.example.foodtip.databinding.UserBinding;
public class UserFragment extends Fragment {
    private UserBinding binding;
    private TextView user_name, user_id;
    private ImageView avatar;

    private Button my_cousine_but, my_favorite_but, my_history_but, setting_but;

    private User user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = UserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = FoodTip.getInstance().getUser();
        setting();
        return root;
    }

    private void setting(){
        my_cousine_but = binding.MyCousine;
        user_name = binding.UserName;
        user_id = binding.UserID;
        avatar = binding.Avatar;

        user_name.setText(user.getUser_name());
        user_id.setText(user.getId());
        Glide.with(binding.getRoot())
                .load(user.getAvatar_uri())
                .fitCenter()
                .into(avatar);
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
