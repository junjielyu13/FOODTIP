package com.example.foodtip.Model;

import java.io.Serializable;

public class Comentari implements Serializable {
    private final String autor; //id autor
    private final String recepta;//id Recepta
    private String comment;

    public Comentari(String autor, String recepta, String comment) {
        this.autor = autor;
        this.recepta = recepta;
        this.comment = comment;
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
}
