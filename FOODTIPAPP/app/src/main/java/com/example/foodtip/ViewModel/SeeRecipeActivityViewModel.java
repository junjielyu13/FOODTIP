package com.example.foodtip.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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
    private final MutableLiveData<Bitmap> bitmapMutableLiveData;
    /*----------------------------------------------------------------*/
    private FoodTip foodTip;

    public SeeRecipeActivityViewModel(@NonNull Application application) {
        super(application);
        mImages = new MutableLiveData<>();
        mIngredients = new MutableLiveData<>();
        mSteps = new MutableLiveData<>();
        bitmapMutableLiveData = new MutableLiveData<>();
        foodTip = FoodTip.getInstance();
    }

    public void see_recipe(Recepta recepta){

    }


}
