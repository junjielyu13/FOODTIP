package com.example.foodtip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.foodtip.Test.MainActivityViewModel;
import com.example.foodtip.View.UI_ZP.CustomAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context parentContext;
    private MainActivityViewModel viewModel;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorite_view);
        parentContext = this.getBaseContext();

        recyclerView = findViewById(R.id.my_favorite_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        setLiveDataObservers();

        Button button = findViewById(R.id.button2);
        button.setOnClickListener((v) -> {
            showPopup(recyclerView);
        });
    }
    public void setLiveDataObservers() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        if (viewModel != null){
            System.out.println("1232");
        }
        final Observer<ArrayList<String>> observer = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                CustomAdapter customAdapter = new CustomAdapter(strings,parentContext);
                recyclerView.swapAdapter(customAdapter,false);
                customAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getDada().observe(this,observer);
    }
    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popuptest,null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout saveDescr = popupView.findViewById(R.id.note_description);
        Button saveButton = popupView.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            String text = saveDescr.getEditText().getText().toString();
            System.out.println(text);
            viewModel.addText(text);
            popupWindow.dismiss();
        });
    }

}