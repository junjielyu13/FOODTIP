package com.example.foodtip.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.DadaBaseAdpter;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.Model.ReceptaBuilder;
import com.example.foodtip.Model.Step;
import com.example.foodtip.Model.SliderData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateCusineActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<SliderData>> mImages;
    private final MutableLiveData<ArrayList<Ingredient>> mIngredients;
    private final MutableLiveData<ArrayList<Step>> mSteps;
    private final MutableLiveData<Bitmap> bitmapMutableLiveData;
    /*----------------------------------------------------------------*/
    private FoodTip foodTip;

    public UpdateCusineActivityViewModel(@NonNull Application application) {
        super(application);
        mImages = new MutableLiveData<>();
        mIngredients = new MutableLiveData<>();
        mSteps = new MutableLiveData<>();
        bitmapMutableLiveData = new MutableLiveData<>();
        foodTip = FoodTip.getInstance();
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

    public void change_picture_of_step(Bitmap bitmap){
        bitmapMutableLiveData.setValue(bitmap);
        bitmapMutableLiveData.setValue(bitmapMutableLiveData.getValue());
    }
    public Bitmap get_current_step_picture(){
        return bitmapMutableLiveData.getValue();
    }

    public void add_ingredient(String nom){
        if(mIngredients.getValue() == null){
            mIngredients.setValue(new ArrayList<>());
        }
        mIngredients.getValue().add(new Ingredient(nom));
        mIngredients.setValue(mIngredients.getValue());
    }

    public void add_steps(Step step){
        if(mSteps.getValue() == null){
            mSteps.setValue(new ArrayList<>());
        }
        step.setImages(bitmapMutableLiveData.getValue());
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

    public void update_new_cusine(Activity activity,String title, String description){
        /**                         Recepta
         * -------------------------------------------------
         *"recepta"
         *      ->  ReceptaID
         *             -> BitMap reference: Array
         *                          -> Reference:Uri
         *             -> Ingredient : ArrayList
         *                      -> Ingredients ID
         *             -> Steps:
         *                  -> BitMap reference; Uri
         *                  ->Text: String
         *                  -> Title: String
         */

        /**
         *                  Foto de recepta
         * --------------------------------------------------
         * "recepta"
         *      -> ReceptaID: Carpeta
         *              -> Images: Carpeta
         *                      -> UniqID in Carpeta
         */
        /**
         * ingreeint->
         *        IngredientID
         *                  -> ReceptaID:UserID
         */
        Recepta recepta = foodTip.createRecepta(title,description,mImages.getValue(),mIngredients.getValue(),mSteps.getValue());

        ArrayList<String> ingredient_id = foodTip.UpdateIngredients(recepta,FirebaseAuth.getInstance().getUid());

        ArrayList<String> pictures = foodTip.UpdatePictures(activity,recepta,FirebaseAuth.getInstance().getUid());

        ArrayList<Map<String,String>> steps = foodTip.UpdateSteps(recepta);

        Map<String,Object> map = new HashMap<>();
        map.put("title",recepta.getTitle());
        map.put("bitmaps",pictures);
        map.put("ingredient",ingredient_id);
        map.put("steps",steps);

        foodTip.GuardarRecepta(recepta,map);
    }



}
