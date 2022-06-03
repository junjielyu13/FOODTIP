package com.example.foodtip.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Comentari implements Serializable {
    private final String autor; //id autor
    private final String recepta;//id Recepta
    private String comment;
    private ArrayList<String> liked;
    private final String id;

    public Comentari(String autor, String recepta, String comment) {
        this.id = UUID.randomUUID().toString();
        this.autor = autor;
        this.recepta = recepta;
        this.comment = comment;
        this.liked = new ArrayList<>();
    }
    public Comentari(String id, String autor, String recepta, String comment,ArrayList<String> liked){
        this.id = id;
        this.autor = autor;
        this.recepta = recepta;
        this.comment = comment;
        this.liked = liked;
    }
    //Getter and Setter
    public String getAutor() {
        return autor;
    }

    public String getRecepta() {
        return recepta;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getID(){
        return this.id;
    }

    public ArrayList<String> getLiked(){
        return this.liked;
    }

}
