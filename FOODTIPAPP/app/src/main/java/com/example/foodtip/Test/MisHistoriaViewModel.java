package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Likes;
import com.example.foodtip.Model.Recepta;

import java.util.ArrayList;

public class MisHistoriaViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Recepta>> mMisHistoria;

    public MisHistoriaViewModel(Application application) {
        super(application);

        mMisHistoria =new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recepta>> getMisFavoritos(){return mMisHistoria;}

    public Recepta getRecepta(int idx){return mMisHistoria.getValue().get(idx);}

    public void addRecepta(){
    }

    public void setCollection(ArrayList<Recepta> rec){mMisHistoria.setValue(rec);}
}
