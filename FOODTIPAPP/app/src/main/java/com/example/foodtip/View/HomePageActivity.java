package com.example.foodtip.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private FoodTip foodTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodTip = FoodTip.getInstance();
        foodTip.getUser();
        setting();
    }

    private void setting(){
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_home, R.id.navigation_user).build();
        BottomNavigationItemView but = findViewById(R.id.navigation_search);
        but.setVisibility(View.GONE);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}