package com.example.foodtip.View.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.example.foodtip.databinding.HomePageBinding;
import com.example.foodtip.databinding.SearchviewBinding;

public class SearchFragment extends Fragment {
    private RecyclerView history, search, result;
    private SearchviewBinding binding;

    public SearchFragment(){
        super(R.layout.searchview);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomePageViewModel homeViewModel =
                new ViewModelProvider(this).get(HomePageViewModel.class);

        binding = SearchviewBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }
}
