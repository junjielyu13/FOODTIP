package com.example.foodtip.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Step;
import com.example.foodtip.View.ViewHolder.SliderData;

import java.util.ArrayList;

public class UpdateCusineActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<SliderData>> mImages;
    private final MutableLiveData<ArrayList<Ingredient>> mIngredients;
    private final MutableLiveData<ArrayList<Step>> mSteps;
    public UpdateCusineActivityViewModel(@NonNull Application application) {
        super(application);
        mImages = new MutableLiveData<>();
        mIngredients = new MutableLiveData<>();
        mSteps = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Ingredient>> getmIngredients() {
        return mIngredients;
    }

    public MutableLiveData<ArrayList<Step>> getmSteps() {
        return mSteps;
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

    public void add_steps(Step step){
        if(mSteps == null){
            mSteps.setValue(new ArrayList<>());
        }
        mSteps.getValue().add(step);
        mSteps.setValue(mSteps.getValue());
    }
    public void remove_ingredient(Ingredient ingredient){
        mIngredients.getValue().remove(ingredient);
        mIngredients.setValue(mIngredients.getValue());
    }
    public void remove_steps(Step step){
        mSteps.getValue().remove(step);
        mSteps.setValue(mSteps.getValue());
    }


}
