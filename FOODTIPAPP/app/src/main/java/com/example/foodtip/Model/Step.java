package com.example.foodtip.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Step implements Serializable {
    private String title;
    private String text;
    private Bitmap images;

    public Step(String title, String text, Bitmap images) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImages() {
        return images;
    }

    public void setImages(Bitmap images) {
        this.images = images;
    }
}
