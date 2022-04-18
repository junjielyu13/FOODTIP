package com.example.foodtip.Controller;

import java.util.UUID;

public class Receptas {
    private final String id;
    private String description;
    private String title;
    private CollectionImages images;
    private Ingredients ingredients;
    private Steps steps;
    private Likes num_like;
    private Comentaris comentaris;

    public Receptas(String description, String title, CollectionImages images, Ingredients ingredients, Steps steps, Likes num_like, Comentaris comentaris) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.description = description;
        this.title = title;
        this.images = images;
        this.ingredients = ingredients;
        this.steps = steps;
        this.num_like = num_like;
        this.comentaris = comentaris;
    }
}
