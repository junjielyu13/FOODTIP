package com.example.foodtip.Controller;

public class Comentari {
    private User autor;
    private String comment;

    public Comentari(User autor, String comment) {
        this.autor = autor;
        this.comment = comment;
    }

    //Getter and Setter
    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
