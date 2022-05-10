package com.example.foodtip.View.Home;

import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;
import com.example.foodtip.ViewModel.SeeRecipeActivityViewModel;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;

public class ViewRecipeActivity extends AppCompatActivity {
    private SeeRecipeActivityViewModel seeViewModel;
    private final String TAG = "ViewRecipe";
    private final int MAX_IMG_UP = 10;
    private TextView title, description;
    private SliderView sliderView;
    private RecyclerView ingredients_View, steps_View, comentarisView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_new_cousine);
        initView();
    }

    private void initView(){
        title = findViewById(R.id.recepta_title_edi);
        description = findViewById(R.id.recepta_txt);
        sliderView = findViewById(R.id.imageSliderRecipe);
        ingredients_View = findViewById(R.id.recepta_recycle_ingredient);
        steps_View = findViewById(R.id.recepta_recycle_steps);
        comentarisView = findViewById(R.id.recepta_recycle_comentaris);


    }
}
