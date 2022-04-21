package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Likes;
import com.example.foodtip.Model.Recepta;

import java.util.ArrayList;

public class MisReceptasViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Recepta>> mMisReceptas;

    public MisReceptasViewModel(Application application) {
        super(application);

        mMisReceptas =new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recepta>> getMisFavoritos(){return mMisReceptas;}

    public Recepta getRecepta(int idx){return mMisReceptas.getValue().get(idx);}

    public void addRecepta(){
    }

    public void setCollection(ArrayList<Recepta> rec){mMisReceptas.setValue(rec);}
}
