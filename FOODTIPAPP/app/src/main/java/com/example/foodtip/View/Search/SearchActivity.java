package com.example.foodtip.View.Search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.CardReceptaAdapter;
import com.example.foodtip.View.ViewHolder.OptionInterface.TAG;
import com.example.foodtip.View.ViewHolder.TagAdapter;
import com.example.foodtip.ViewModel.SearchViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private SearchViewModel viewModel;
    private RecyclerView history, search, result;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Context context;
    private Activity activity;
    public SearchActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        context = this;
        activity = this;
        setting();
    }

    private void setting(){
        history = findViewById(R.id.history_recycle);
        search = findViewById(R.id.search_recycle);
        result = findViewById(R.id.result_recycler);
        searchView = findViewById(R.id.search_bar);
        listView = findViewById(R.id.list_view);
        setLiveDataObservers();
        setSearchViewAction();
        history.setLayoutManager(getLayoutManager());
        search.setLayoutManager(getLayoutManager());
        result.setLayoutManager(new LinearLayoutManager(this));
    }

    private FlexboxLayoutManager getLayoutManager(){
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return  flexboxLayoutManager;
    }

    public void setLiveDataObservers(){
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        final Observer<ArrayList<String>> searching_observer = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                TagAdapter tagAdapter = new TagAdapter(strings,TAG.SEARCH,viewModel);
                search.swapAdapter(tagAdapter,false);
                tagAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<String>> ingredient_observer = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,strings);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Recepta>> recepta_observer = new Observer<ArrayList<Recepta>>() {
            @Override
            public void onChanged(ArrayList<Recepta> receptas) {
                CardReceptaAdapter cardReceptaAdapter = new CardReceptaAdapter(receptas,activity);
                result.swapAdapter(cardReceptaAdapter,false);
                cardReceptaAdapter.notifyDataSetChanged();

            }
        };
        final Observer<ArrayList<String>> history_observer = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                TagAdapter tagAdapter = new TagAdapter(strings,TAG.HISTORY,viewModel);
                history.swapAdapter(tagAdapter,false);
                tagAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getSearching().observe(this,searching_observer);
        viewModel.getIngredient().observe(this,ingredient_observer);
        viewModel.getResult().observe(this,recepta_observer);
        viewModel.getHistory().observe(this,history_observer);
    }

    private void setSearchViewAction(){
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(viewModel.getIngredient().getValue().contains(s)){
                    adapter.getFilter().filter(s);
                }else{
                    Toast.makeText(SearchActivity.this,"NOT FOUND",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object str = adapter.getItem(i);
                viewModel.addTag(str.toString());
                viewModel.updateHistory(str.toString());
                searchView.clearFocus();
                listView.setVisibility(View.GONE);
            }
        });
    }
    private void setHistoryOnClick(){

    }
}
