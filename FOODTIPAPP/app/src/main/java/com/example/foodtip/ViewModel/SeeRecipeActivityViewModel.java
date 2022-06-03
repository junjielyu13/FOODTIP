package com.example.foodtip.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.Comentari;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.Model.Step;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

public class SeeRecipeActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<SliderData>> mImages;
    private final MutableLiveData<ArrayList<Ingredient>> mIngredients;
    private final MutableLiveData<ArrayList<Step>> mSteps;
    private final MutableLiveData<ArrayList<Comentari>> mComentaris;
    private FoodTip foodTip;
    public SeeRecipeActivityViewModel(@NonNull Application application, ArrayList<SliderData> images, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, ArrayList<Comentari> comentaris) {
        super(application);
        foodTip = FoodTip.getInstance();
        mImages = new MutableLiveData<>();
        mImages.setValue(images);
        mIngredients = new MutableLiveData<>();
        mIngredients.setValue(ingredients);
        mSteps = new MutableLiveData<>();
        mSteps.setValue(steps);
        mComentaris = new MutableLiveData<>();
        mComentaris.setValue(comentaris);
    }

    public MutableLiveData<ArrayList<SliderData>> getmImages() {
        return mImages;
    }

    public MutableLiveData<ArrayList<Ingredient>> getmIngredients() {
        return mIngredients;
    }

    public MutableLiveData<ArrayList<Step>> getmSteps() {
        return mSteps;
    }

    public MutableLiveData<ArrayList<Comentari>> getmComentaris(){
        return mComentaris;
    }

    public void setmImages(ArrayList<SliderData> picture){
        mImages.setValue(picture);
    }
    public void setmIngredients(ArrayList<Ingredient> ingredients){
        mIngredients.setValue(ingredients);
    }
    public void setmSteps(ArrayList<Step> steps){
        mSteps.setValue(steps);
    }

}
