package com.example.foodtip.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.View.ViewHolder.SliderData;

import java.util.ArrayList;

public class UpdateCusineActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<SliderData>> mImages;
    public UpdateCusineActivityViewModel(@NonNull Application application) {
        super(application);
        mImages = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<SliderData>> getmImages() {
        return mImages;
    }
    public void add_picture(Uri uri){
        if(mImages.getValue() == null) {
            mImages.setValue(new ArrayList<>());
        }
        mImages.getValue().add(new SliderData(uri.toString()));
        mImages.setValue(mImages.getValue());
    }
}
