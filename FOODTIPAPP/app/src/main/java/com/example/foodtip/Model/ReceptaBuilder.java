package com.example.foodtip.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Map;

public class ReceptaBuilder {
    private String description;
    private String title;
    private ArrayList<SliderData> images;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private ArrayList<String> likes;
    private Map<String, Comentari> comentaris;

    public ReceptaBuilder(){
        likes = null;
        comentaris = null;
    }
    public Recepta buildRecepta(){
        return new Recepta(description,title,images,ingredients,steps,likes,comentaris);
    }
    public ReceptaBuilder description(String description){
        this.description = description;
        return this;
    }
    public ReceptaBuilder title(String title){
        this.title = title;
        return this;
    }
    public ReceptaBuilder images(ArrayList<SliderData> images){
        this.images = images;
        return this;
    }

    public ReceptaBuilder ingredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
        return this;
    }

    public ReceptaBuilder steps(ArrayList<Step> steps){
        this.steps = steps;
        return this;
    }
}
