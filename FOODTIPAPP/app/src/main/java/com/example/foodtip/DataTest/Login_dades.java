package com.example.foodtip.DataTest;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.User;
import com.example.foodtip.*;
import java.util.Map;

public class Login_dades {

    private void CreatDates(){
        FoodTip. = new FoodTip();
        foodTip.addUsers("a","1");
        foodTip.addUsers("b","2");
    }
    Map<String, User> UsersMap;

    public Map<String, User> getUsersMap() {
        return UsersMap;
    }

    public void setUsersMap(Map<String, User> usersMap) {
        UsersMap = usersMap;
    }

    public void addUsers(String username, String password){
        UsersMap.put(username,new User(username,password));
    }
}

