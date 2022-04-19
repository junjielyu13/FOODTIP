package com.example.foodtip.DataTest;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.User;
import com.example.foodtip.*;
import com.example.foodtip.View.MainActivity;

import java.util.Map;

public class Login_dades {

    Map<String, User> UsersMap;

    private void CreatDates(){

        addUsers("a","1");
        addUsers("b","2");
    }


    public Map<String, User> getUsersMap() {
        return UsersMap;
    }

    public void setUsersMap(Map<String, User> usersMap) {
        UsersMap = usersMap;
    }

    public void addUsers(String username, String password){
        UsersMap.put(username,new User(username,password);
    }
}

