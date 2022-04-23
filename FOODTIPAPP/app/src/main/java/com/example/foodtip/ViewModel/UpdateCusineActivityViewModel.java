package com.example.foodtip.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.View.ViewHolder.SliderData;

import java.util.ArrayList;

public class UpdateCusineActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<SliderData>> mImages;
    private final MutableLiveData<ArrayList<Ingredient>> mIngredients;

    public UpdateCusineActivityViewModel(@NonNull Application application) {
        super(application);
        mImages = new MutableLiveData<>();
        mIngredients = new MutableLiveData<>();
        ArrayList<Ingredient> random = new ArrayList<>();
        for(int i = 0; i <= 10; i++){
            random.add(new Ingredient(Integer.toString(i)));
        }
        mIngredients.setValue(random);
    }

    public MutableLiveData<ArrayList<Ingredient>> getmIngredients() {
        return mIngredients;
    }

    public MutableLiveData<ArrayList<SliderData>> getmImages() {
        return mImages;
    }

    public void add_picture(Uri uri){
        if(mImages.getValue() == null) {
            mImages.setValue(new ArrayList<>());
        }
        mImages.getValue().add(new SliderData(uri.toString()));
        mImages.setValue(mImages.getValue());
    }

    public void add_ingredient(String nom){
        if(mIngredients.getValue() == null){
            mIngredients.setValue(new ArrayList<>());
        }
        mIngredients.getValue().add(new Ingredient(nom));
        mIngredients.setValue(mIngredients.getValue());
    }

    public void remove_ingredient(Ingredient ingredient){
        mIngredients.getValue().remove(ingredient);
        mIngredients.setValue(mIngredients.getValue());
    }
}
