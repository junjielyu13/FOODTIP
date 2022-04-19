package com.example.foodtip.Model;

public class Step {
    private String text;
    private CollectionImages collectionImages;

    public Step(String text, CollectionImages collectionImages) {
        this.text = text;
        this.collectionImages = collectionImages;
    }

    //Setter and Getter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CollectionImages getCollectionImages() {
        return collectionImages;
    }

    public void setCollectionImages(CollectionImages collectionImages) {
        this.collectionImages = collectionImages;
    }
}
