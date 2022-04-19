package com.example.foodtip.Model;

import android.media.Image;

import java.util.Vector;

public class CollectionImages {
    private Vector<Image> imatges;

    public CollectionImages(Vector<Image> imatges) {
        if(imatges != null) this.imatges = imatges;
        else this.imatges = new Vector<Image>();
    }

    public Vector<Image> getImatges() {
        return imatges;
    }

    public void setImatges(Vector<Image> imatges) {
        this.imatges = imatges;
    }
}
