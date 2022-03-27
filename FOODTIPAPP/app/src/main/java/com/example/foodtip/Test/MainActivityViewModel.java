package com.example.foodtip.Test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel{
    private final MutableLiveData<ArrayList<String>> dada;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        dada = new MutableLiveData<>();
        ArrayList<String> ac = new ArrayList<String>();
        for (int i = 0; i <= 10; i++){
            ac.add(Integer.toString(i));
        }
        dada.setValue(ac);
    }

    public MutableLiveData<ArrayList<String>> getDada() {
        return dada;
    }
    public void addText(String s){
        dada.getValue().add(s);
        dada.setValue(dada.getValue());
    }
}
