package com.example.foodtip.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.foodtip.R;

public class StepBuilder {
    private String title;
    private String text;
    private Bitmap bitmap;

    public StepBuilder(@NonNull Activity activity){
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.empty);
    }
    public Step buildUser(){
        return new Step(title,text,bitmap);
    }
    public StepBuilder title(String title){
        this.title = title;
        return this;
    }
    public StepBuilder text(String text) {
        this.text = text;
        return this;
    }
}
