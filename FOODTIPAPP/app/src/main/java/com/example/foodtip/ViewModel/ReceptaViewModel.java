package com.example.foodtip.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Recepta;

import java.util.ArrayList;

public class ReceptaViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Recepta>> receptas;

    private FoodTip foodTip;
    public ReceptaViewModel(@NonNull Application application) {
        super(application);
        receptas = new MutableLiveData<>();
        foodTip = FoodTip.getInstance();
        foodTip.getMisRecepta(this);
    }
    public MutableLiveData<ArrayList<Recepta>> getReceptas() {
        return receptas;
    }

    public void add_recepta(Recepta recepta){
        if(receptas.getValue() == null) {
            receptas.setValue(new ArrayList<>());
        }
        receptas.getValue().add(recepta);
        receptas.setValue(receptas.getValue());
    }
}
