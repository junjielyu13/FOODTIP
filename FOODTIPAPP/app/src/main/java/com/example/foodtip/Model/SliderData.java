package com.example.foodtip.Model;

import java.io.Serializable;

public class SliderData implements Serializable {
    private String imgUri;

    public SliderData(String uri){
        this.imgUri = uri;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
