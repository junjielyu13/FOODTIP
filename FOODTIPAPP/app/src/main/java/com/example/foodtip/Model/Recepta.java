package com.example.foodtip.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Recepta {
    private final String id;
    private String description;
    private String title;
    private Map<String, Bitmap> images;
    private Map<String, Ingredient> ingredients;
    private ArrayList<Step> steps;
    private ArrayList<String> likes;
    private Map<String, Comentari> comentaris;


    public Recepta(String description, String title, Map<String, Bitmap> images, Map<String, Ingredient> ingredients, ArrayList<Step> steps, ArrayList<String> likes, Map<String, Comentari> comentaris) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.description = description;
        this.title = title;
        this.images = images;
        this.ingredients = ingredients;
        this.steps = steps;
        this.likes = likes;
        this.comentaris = comentaris;
    }
}
