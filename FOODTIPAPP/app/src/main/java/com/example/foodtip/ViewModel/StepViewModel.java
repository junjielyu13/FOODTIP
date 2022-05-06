package com.example.foodtip.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.Step;

public class StepViewModel extends AndroidViewModel {
    private final MutableLiveData<Uri> mutableLiveData;


    public StepViewModel(@NonNull Application application) {
        super(application);
        this.mutableLiveData = new MutableLiveData<>();
        this.mutableLiveData.setValue(null);
    }

    public void change_picture(Uri uri){
        mutableLiveData.setValue(uri);
    }
}
