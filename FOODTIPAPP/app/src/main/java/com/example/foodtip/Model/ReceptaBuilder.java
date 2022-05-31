package com.example.foodtip.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Map;

public class ReceptaBuilder {
    private String id;
    private String description;
    private String title;
    private ArrayList<SliderData> images;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private ArrayList<String> likes;
    private ArrayList<Comentari> comentaris;
    private boolean liked;
    public ReceptaBuilder(){
        id = null;
        likes = null;
        comentaris = null;
        description = null;
        title = null;
        images = null;
        ingredients = null;
        steps = null;
        liked = false;
    }
    public Recepta buildRecepta(){
        if(id != null){
            return new Recepta(id,description,title,images,ingredients,steps,likes,comentaris);
        }
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
    public ReceptaBuilder id(String id){
        this.id = id;
        return this;
    }
    public ReceptaBuilder likes(ArrayList<String> likes){
        this.likes = likes;
        return this;
    }

    public ReceptaBuilder comentaris(ArrayList<Comentari> comentaris){
        this.comentaris = comentaris;
        return this;
    }

}
