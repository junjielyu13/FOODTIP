package com.example.foodtip.Model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    /**
     * Ingredient Name => His ID
     */
    private final String nom;

    public Ingredient(String nom) {
        this.nom = nom;
    }

    //Getter and Setter

    public String getNom() {
        return nom;
    }

}
