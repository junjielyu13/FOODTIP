package com.example.foodtip.View.User;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.CardReceptaAdapter;
import com.example.foodtip.ViewModel.FavoritesViewModel;
import com.example.foodtip.ViewModel.ReceptaViewModel;
import com.example.foodtip.databinding.MyRecipeViewBinding;

import java.util.ArrayList;

public class MyCousine extends AppCompatActivity {
    private MyRecipeViewBinding binding;
    private ReceptaViewModel viewModel;
    private RecyclerView recyclerView;
    private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MyRecipeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.getRoot().findViewById(R.id.my_recipe_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activity = this;
        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        viewModel = new ViewModelProvider(this).get(ReceptaViewModel.class);
        final Observer<ArrayList<Recepta>> observer_recepta = new Observer<ArrayList<Recepta>>() {
            @Override
            public void onChanged(ArrayList<Recepta> receptas) {
                CardReceptaAdapter receptaAdapter = new CardReceptaAdapter(receptas,activity);
                recyclerView.swapAdapter(receptaAdapter,false);
                receptaAdapter.notifyDataSetChanged();

            }
        };
        viewModel.getReceptas().observe(this,observer_recepta);
    }
}
