package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.Comentari;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.User;

import java.util.ArrayList;

public class ComentarisViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Comentari>> mComentaris;

    public ComentarisViewModel(Application application) {
        super(application);

        mComentaris = new MutableLiveData<>();
    }
    public LiveData<ArrayList<Comentari>> getComentaris(){return mComentaris;}

    public Comentari getComentari(int idx){ return mComentaris.getValue().get(idx);}

    public void addComentari(User autor, String comment){
    }

    public void setCollection(ArrayList<Comentari> com){mComentaris.setValue(com);}
}
