package com.example.foodtip.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.R;
import com.example.foodtip.View.Home.UpdateCusineActivity;
import com.example.foodtip.View.ViewHolder.ReceptaAdapter;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private HomePageViewModel viewModel;
    private FoodTip foodTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodTip = FoodTip.getInstance();
        //foodTip.getUser();
        setting();
    }

    private void setting(){
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_but);
        recyclerView = findViewById(R.id.recy_home_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));

        //setLiveDataObservers();
        floatingActionButton.setOnClickListener((v)->{
            startActivity(new Intent(this, UpdateCusineActivity.class));
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_home, R.id.navigation_user).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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

    public void setLiveDataObservers(){
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        final Observer<ArrayList<Recepta>> observer_recepta = new Observer<ArrayList<Recepta>>() {
            @Override
            public void onChanged(ArrayList<Recepta> receptas) {
                System.out.println(receptas.size());
                if(receptas.size() == 1){
                    //System.out.println(receptas.get(0).getImages().get(0).getImgUri());
                }
                ReceptaAdapter receptaAdapter = new ReceptaAdapter(receptas,viewModel);
                recyclerView.swapAdapter(receptaAdapter,false);
                receptaAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getReceptas().observe(this,observer_recepta);
    }
}