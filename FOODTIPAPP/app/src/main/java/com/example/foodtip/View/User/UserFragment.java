package com.example.foodtip.View.User;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private ImageButton avatar;
    private ActivityResultLauncher<String> get_img;

    private Button my_cousine_but, my_favorite_but, my_history_but, setting_but;

    private User user;
    private Activity activity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = UserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.activity = this.getActivity();
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
        System.out.println(user.getAvatar_uri());
        Glide.with(binding.getRoot())
                .load(user.getAvatar_uri())
                .fitCenter()
                .into(avatar);

        get_img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result != null){
                            Glide.with(binding.getRoot())
                                    .load(result)
                                    .fitCenter()
                                    .into(avatar);
                            FoodTip.getInstance().changeAvatar(activity,result);
                        }
                    }
                }
        );
        avatar.setOnClickListener(l->{
            get_img.launch("image/*");
        });

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
        Intent intent = new Intent(this.getContext(), MySettingActivity.class);
        startActivity(intent);
    }

    private void open_my_history() {
        Intent intent = new Intent(this.getContext(), MyHistoryActivity.class);
        startActivity(intent);
    }

    private void open_my_favorite() {
        Intent intent = new Intent(this.getContext(), MyFavoriteActivity.class);
        startActivity(intent);
    }

    private void open_my_cousine() {
        Intent intent = new Intent(this.getContext(), MyCousineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
