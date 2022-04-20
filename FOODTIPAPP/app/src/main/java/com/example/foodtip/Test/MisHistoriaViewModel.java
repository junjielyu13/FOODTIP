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

public class MisHistoriaViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Recepta>> mMisHistoria;

    public MisHistoriaViewModel(Application application) {
        super(application);

        mMisHistoria =new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recepta>> getMisFavoritos(){return mMisHistoria;}

    public Recepta getRecepta(int idx){return mMisHistoria.getValue().get(idx);}

    public void addRecepta(String description, String title, CollectionImages images, Ingredients ingredients, Steps steps, Likes num_like, Comentaris comentaris){
        Recepta rec = new Recepta(description, title, images, ingredients, steps, num_like, comentaris);
        if (rec != null){
            mMisHistoria.getValue().add(rec);
            mMisHistoria.setValue(mMisHistoria.getValue());
        }
    }

    public void setCollection(ArrayList<Recepta> rec){mMisHistoria.setValue(rec);}
}
