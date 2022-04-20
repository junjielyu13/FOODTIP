package com.example.foodtip.Model;

import java.util.Vector;

public class Likes {
    private Vector<String> liked; //User who liked the post, using user id

    public Likes(Vector<String> liked) {
        if(liked != null)this.liked = liked;
        else this.liked = new Vector<>();
    }

    //Getter and Setter
    public Vector<String> getLiked() {
        return liked;
    }

    public void setLiked(Vector<String> liked) {
        this.liked = liked;
    }
}
