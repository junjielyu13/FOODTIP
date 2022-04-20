package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Comentaris;
import com.example.foodtip.Model.Ingredients;
import com.example.foodtip.Model.Likes;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.Model.Steps;

import java.util.ArrayList;
import java.util.List;

public class MisFavoritosViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Recepta>> mMisFavoritos;

    public MisFavoritosViewModel(Application application) {
        super(application);

        mMisFavoritos =new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recepta>> getMisFavoritos(){return mMisFavoritos;}

    public Recepta getRecepta(int idx){return mMisFavoritos.getValue().get(idx);}

    public void addRecepta(String description, String title, CollectionImages images, Ingredients ingredients, Steps steps, Likes num_like, Comentaris comentaris){
        Recepta rec = new Recepta(description, title, images, ingredients, steps, num_like, comentaris);
        if (rec != null){
            mMisFavoritos.getValue().add(rec);
            mMisFavoritos.setValue(mMisFavoritos.getValue());
        }
    }

    public void setCollection(ArrayList<Recepta> rec){mMisFavoritos.setValue(rec);}

}
