package com.example.foodtip.Test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.MisReceptas;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.Model.User;

public class UserViewModel extends AndroidViewModel {

    private final MutableLiveData<User> userMutableLiveData;

    public UserViewModel(Application application) {
        super(application);
        this.userMutableLiveData = new MutableLiveData<>();
    }

}
