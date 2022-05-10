package com.example.foodtip.View.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.CardReceptaAdapter;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.example.foodtip.databinding.HomePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomePageBinding binding;
    private HomePageViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = HomePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setting();
        return root;
    }
    private void setting(){
        this.recyclerView = binding.getRoot().findViewById(R.id.recy_home_page);
        this.floatingActionButton = binding.getRoot().findViewById(R.id.floating_but);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        floatingActionButton.setOnClickListener((v)->{
            startActivity(new Intent(binding.getRoot().getContext(), UpdateCusineActivity.class));
        });
        setLiveDataObservers();
    }

    public void setLiveDataObservers(){
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        final Observer<ArrayList<Recepta>> observer_recepta = new Observer<ArrayList<Recepta>>() {
            @Override
            public void onChanged(ArrayList<Recepta> receptas) {
                System.out.println("111111111111111111111111111111");
                CardReceptaAdapter receptaAdapter = new CardReceptaAdapter(receptas,viewModel);
                recyclerView.swapAdapter(receptaAdapter,false);
                receptaAdapter.notifyDataSetChanged();

            }
        };
        viewModel.getReceptas().observe(getViewLifecycleOwner(),observer_recepta);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
