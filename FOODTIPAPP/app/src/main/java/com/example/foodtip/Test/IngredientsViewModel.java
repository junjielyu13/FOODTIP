package com.example.foodtip.Test;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Step;

import java.util.ArrayList;

public class IngredientsViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Ingredient>> mIngredients;

    public IngredientsViewModel(Application application) {
        super(application);

        mIngredients = new MutableLiveData<>();
    }
    public LiveData<ArrayList<Ingredient>> getIngredients(){return mIngredients;}

    public Ingredient getIngredient(int idx){ return mIngredients.getValue().get(idx);}

    public void addIngredient(String nom){
        Ingredient ingre = new Ingredient(nom);
        if(ingre != null){
            mIngredients.getValue().add(ingre);
            mIngredients.setValue(mIngredients.getValue());
        }
    }

    public void setCollection(ArrayList<Ingredient> ingre){mIngredients.setValue(ingre);}
}
