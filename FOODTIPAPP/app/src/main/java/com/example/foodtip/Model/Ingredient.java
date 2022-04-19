package com.example.foodtip.Model;

import java.util.UUID;

public class Ingredient {
    private final String id;
    private String nom;


    public Ingredient(String nom) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.nom = nom;
    }

    //Getter and Setter
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
