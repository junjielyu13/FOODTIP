package com.example.foodtip.Controller;

import java.util.Vector;

public class Likes {
    private Vector<User> liked; //User who liked the post

    public Likes(Vector<User> liked) {
        if(liked != null)this.liked = liked;
        else this.liked = new Vector<>();
    }

    //Getter and Setter
    public Vector<User> getLiked() {
        return liked;
    }

    public void setLiked(Vector<User> liked) {
        this.liked = liked;
    }
}
