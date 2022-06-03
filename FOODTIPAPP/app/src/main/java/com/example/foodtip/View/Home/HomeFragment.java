package com.example.foodtip.View.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;
import com.example.foodtip.View.Search.SearchActivity;
import com.example.foodtip.View.ViewHolder.CardReceptaAdapter;
import com.example.foodtip.View.ViewHolder.OptionInterface.CMD;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.example.foodtip.databinding.HomePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomePageBinding binding;
    private HomePageViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Activity parent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CardReceptaAdapter receptaAdapter;
    private ImageButton imageButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomePageViewModel homeViewModel =
                new ViewModelProvider(this).get(HomePageViewModel.class);

        binding = HomePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        parent = this.getActivity();
        setting();
        return root;
    }
    private void setting(){
        this.recyclerView = binding.getRoot().findViewById(R.id.recy_home_page);
        this.floatingActionButton = binding.getRoot().findViewById(R.id.floating_but);
        this.swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipe_layout);
        this.imageButton = binding.getRoot().findViewById(R.id.home_but);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        floatingActionButton.setOnClickListener((v)->{
            startActivity(new Intent(binding.getRoot().getContext(), UpdateCusineActivity.class));
        });
        setLiveDataObservers();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                receptaAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        imageButton.setOnClickListener(l->{
            FoodTip.getInstance().getReceptaInformation(viewModel, CMD.ADD);
        });

    }

    public void setLiveDataObservers(){
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        final Observer<ArrayList<Recepta>> observer_recepta = new Observer<ArrayList<Recepta>>() {
            @Override
            public void onChanged(ArrayList<Recepta> receptas) {
                receptaAdapter = new CardReceptaAdapter(receptas,parent);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_search:
                this.parent.startActivity(new Intent(this.parent, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
