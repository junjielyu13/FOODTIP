package com.example.foodtip.Test;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.CollectionImages;
import com.example.foodtip.Model.Step;

import java.util.ArrayList;

public class StepsViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<Step>> mSteps;

    public StepsViewModel(Application application) {
        super(application);

        mSteps = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Step>> getSteps(){return mSteps;}

    public Step getStep(int idx){ return mSteps.getValue().get(idx);}

    public void addStep(String text, Bitmap image){
        Step sp = new Step(text, image);
        if(sp != null){
            mSteps.getValue().add(sp);
            mSteps.setValue(mSteps.getValue());
        }
    }

    public void setCollection(ArrayList<Step> sp){mSteps.setValue(sp);}
}
