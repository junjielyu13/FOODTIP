package com.example.foodtip.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import com.example.foodtip.R;

public class UserBuilder {
    private Bitmap bitmap;
    private String uid;
    private String acc_name;
    private String password;
    private String user_name;


    public UserBuilder(@NonNull Activity activity){
        bitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.user_login);
    };

    public UserBuilder() {
    }

    public User buildUser(){
        return new User(uid,acc_name,password,user_name,bitmap);
    }
    public UserBuilder uid(String uid){
        this.uid = uid;
        return this;
    }
    public UserBuilder bitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        return this;
    }
    public UserBuilder acc_name(String name){
        this.acc_name = name;
        return this;
    }
    public UserBuilder user_name(String name){
        this.user_name = name;
        return this;
    }
    public UserBuilder password(String password){
        this.password = password;
        return this;
    }
}
