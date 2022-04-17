package com.example.foodtip.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtip.R;
import com.example.foodtip.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_View extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_home, R.id.navigation_user).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        MenuItem search = menu.findItem(R.id.nav_search);

        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search Ingredient");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.filter.filter(newText)
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getActionView());
        return super.onOptionsItemSelected(item);
    }
}
