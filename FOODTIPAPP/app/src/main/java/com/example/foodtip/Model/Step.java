package com.example.foodtip.Model;

import android.graphics.Bitmap;

public class Step {
    private String text;
    private Bitmap images;

    public Step(String text, Bitmap images) {
        this.text = text;
        this.images = images;
    }

    //Setter and Getter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getCollectionImages() {
        return images;
    }

    public void setCollectionImages(Bitmap images) {
        this.images = images;
    }
}
