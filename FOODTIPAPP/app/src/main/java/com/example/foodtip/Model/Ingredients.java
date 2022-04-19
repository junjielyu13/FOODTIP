package com.example.foodtip.Model;

import java.util.Vector;

public class Ingredients {
    private Vector<Ingredient> ingredients;

    public Ingredients(Vector<Ingredient> ingredients) {
        if(ingredients != null) this.ingredients = ingredients;
        else this.ingredients = new Vector<Ingredient>();
    }

    public Vector<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Vector<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
