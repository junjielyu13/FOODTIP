package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Likes;
import com.example.foodtip.Model.Recepta;

import java.util.ArrayList;

public class MisFavoritosViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Recepta>> mMisFavoritos;

    public MisFavoritosViewModel(Application application) {
        super(application);

        mMisFavoritos =new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recepta>> getMisFavoritos(){return mMisFavoritos;}

    public Recepta getRecepta(int idx){return mMisFavoritos.getValue().get(idx);}

    public void addRecepta(){
    }

    public void setCollection(ArrayList<Recepta> rec){mMisFavoritos.setValue(rec);}

}
