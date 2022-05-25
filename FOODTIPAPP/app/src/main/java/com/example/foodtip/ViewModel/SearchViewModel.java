package com.example.foodtip.ViewModel;


import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<String>> ingredient;
    private final MutableLiveData<ArrayList<String>> history;
    private final MutableLiveData<ArrayList<String>> searching;
    private final MutableLiveData<ArrayList<Recepta>> receptas;
    private final MutableLiveData<ArrayList<Recepta>> result;
    public SearchViewModel(@NonNull Application application){
        super(application);
        ingredient = new MutableLiveData<>();
        history = new MutableLiveData<>();
        searching = new MutableLiveData<>();
        receptas = new MutableLiveData<>();
        result = new MutableLiveData<>();
        FoodTip.getInstance().getValidIngredient(this);
        FoodTip.getInstance().getAllRecepta(this);
    }

    public MutableLiveData<ArrayList<String>> getIngredient() {
        return ingredient;
    }

    public MutableLiveData<ArrayList<String>> getHistory() {
        return history;
    }

    public MutableLiveData<ArrayList<String>> getSearching() {
        return searching;
    }

    public MutableLiveData<ArrayList<Recepta>> getReceptas() {
        return receptas;
    }

    public MutableLiveData<ArrayList<Recepta>> getResult() {
        return result;
    }
    public boolean addTag(String input){
        if(searching.getValue() == null){
            searching.setValue(new ArrayList<>());
        }
        if(searching.getValue().contains(input)){
            return false;
        }
        searching.getValue().add(input);
        searching.setValue(searching.getValue());
        ChangeResult();
        return true;
    }

    public void DeleteTag(String input){
        this.searching.getValue().remove(input);
        this.searching.setValue(this.searching.getValue());
        ChangeResult();
    }
    public void ChangeResult(){
        ArrayList<Recepta> tmp = (ArrayList<Recepta>) receptas.getValue().clone();
        for(String str: searching.getValue()){
            for(Recepta recepta: receptas.getValue()){
                if(!recepta.containIngredient(str) && tmp.contains(recepta)){
                    tmp.remove(recepta);

                }
            }
        }
        result.setValue(tmp);
    }
    public void addRecepta(Recepta recepta){
        if(receptas.getValue() == null){
            receptas.setValue(new ArrayList<>());
        }
        receptas.getValue().add(recepta);
        receptas.setValue(receptas.getValue());
    }
    public void setIngredient(String s){
        if(this.ingredient.getValue() == null){
            this.ingredient.setValue(new ArrayList<>());
        }
        this.ingredient.getValue().add(s);
        this.ingredient.setValue(this.ingredient.getValue());
    }

}
