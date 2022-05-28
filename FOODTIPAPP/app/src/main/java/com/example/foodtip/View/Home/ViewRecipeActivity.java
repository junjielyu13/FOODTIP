package com.example.foodtip.View.Home;

import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.Model.Step;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.IngredientAdapter;
import com.example.foodtip.View.ViewHolder.SliderAdapter;
import com.example.foodtip.View.ViewHolder.StepsAdapter;
import com.example.foodtip.ViewModel.SeeRecipeActivityViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.ArrayList;

public class ViewRecipeActivity extends AppCompatActivity {
    private SeeRecipeActivityViewModel seeViewModel;
    private FoodTip foodTip;
    private Intent intent;
    private Bundle bundle;
    private final String TAG = "ViewRecipe";
    private final int MAX_IMG_UP = 10;
    private TextView title, description;
    private SliderView sliderView;
    private RecyclerView ingredients_View, steps_View, comentarisView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodTip = FoodTip.getInstance();
        intent = getIntent();
        setContentView(R.layout.recepta_view);

        bundle = intent.getBundleExtra("bundle");
        //seeViewModel = new SeeRecipeActivityViewModel(this.getApplication(),(ArrayList<SliderData>) bundle.getSerializable("picture"),(ArrayList<Ingredient>) bundle.getSerializable("ingredients"),(ArrayList<Step>) bundle.getSerializable("steps"));
        initView();
    }

    private void initView(){
        title = findViewById(R.id.recepta_title);
        description = findViewById(R.id.recepta_view_descrip);
        sliderView = findViewById(R.id.imageSlider);
        ingredients_View = findViewById(R.id.recepta_view_recycle_ingredient);
        steps_View = findViewById(R.id.recepta_view_recycle_steps);
        comentarisView = findViewById(R.id.recepta_view_recycle_comentaris);

        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));

        setLiveDataObservers();
    }

    public void setLiveDataObservers(){
        seeViewModel = new SeeRecipeActivityViewModel(this.getApplication(),(ArrayList<SliderData>) bundle.getSerializable("picture"),(ArrayList<Ingredient>) bundle.getSerializable("ingredients"),(ArrayList<Step>) bundle.getSerializable("steps"));

        final Observer<ArrayList<SliderData>> observer_SliderData = new Observer<ArrayList<SliderData>>() {
            @Override
            public void onChanged(ArrayList<SliderData> sliderData) {
                SliderAdapter sliderAdapter = new SliderAdapter(seeViewModel.getmImages().getValue());
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setSliderAdapter(sliderAdapter,false);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                sliderAdapter.notifyDataSetChanged();
            }
        };
        final Observer<ArrayList<Ingredient>> observer_ingredeint = new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(ArrayList<Ingredient> ingredients) {
                IngredientAdapter ingredientAdapter = new IngredientAdapter(seeViewModel.getmIngredients().getValue(), null);
                ingredients_View.setLayoutManager(new LinearLayoutManager(ViewRecipeActivity.this));
                ingredients_View.swapAdapter(ingredientAdapter,false);
                ingredientAdapter.notifyDataSetChanged();
            }
        };
        final Observer<ArrayList<Step>> observer_steps = new Observer<ArrayList<Step>>() {
            @Override
            public void onChanged(ArrayList<Step> steps) {
                StepsAdapter stepsAdapter = new StepsAdapter(seeViewModel.getmSteps().getValue(),null);
                steps_View.setLayoutManager(new LinearLayoutManager(ViewRecipeActivity.this));
                steps_View.swapAdapter(stepsAdapter,false);
                stepsAdapter.notifyDataSetChanged();
            }
        };
        seeViewModel.getmImages().observe(this,observer_SliderData);
        seeViewModel.getmIngredients().observe(this,observer_ingredeint);
        seeViewModel.getmSteps().observe(this,observer_steps);
    }
}
