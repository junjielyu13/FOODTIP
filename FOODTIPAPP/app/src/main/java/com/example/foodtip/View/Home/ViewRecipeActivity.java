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
import android.widget.Toast;

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

import com.example.foodtip.Model.Comentari;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.Model.Step;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.ComentarisAdapter;
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
    private Button publicar_but;
    private EditText input_txt;
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
        publicar_but = findViewById(R.id.comentaris_publicar);
        input_txt = findViewById(R.id.comentaris_input);

        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));

        publicar_but.setOnClickListener(l->{
            Comentari comentari = new Comentari(FoodTip.getInstance().getUser().getId(),intent.getStringExtra("id"),input_txt.getText().toString());
            FoodTip.getInstance().publicar_comentaris(comentari);
            input_txt.setText("");
            Toast.makeText(this,"Comentaris publicada",Toast.LENGTH_SHORT).show();
        });

        setLiveDataObservers();
    }

    public void setLiveDataObservers(){
        seeViewModel = new SeeRecipeActivityViewModel(this.getApplication(),
                (ArrayList<SliderData>) bundle.getSerializable("picture"),
                (ArrayList<Ingredient>) bundle.getSerializable("ingredients"),
                (ArrayList<Step>) bundle.getSerializable("steps"),
                (ArrayList<Comentari>) bundle.getSerializable("comentaris"));

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

        final Observer<ArrayList<Comentari>> observer_comentaris = new Observer<ArrayList<Comentari>>() {
            @Override
            public void onChanged(ArrayList<Comentari> comentaris) {
                ComentarisAdapter comentarisAdapter = new ComentarisAdapter(comentaris);
                comentarisView.setLayoutManager(new LinearLayoutManager(ViewRecipeActivity.this));
                comentarisView.swapAdapter(comentarisAdapter,false);
                comentarisAdapter.notifyDataSetChanged();
            }
        };
        seeViewModel.getmImages().observe(this,observer_SliderData);
        seeViewModel.getmIngredients().observe(this,observer_ingredeint);
        seeViewModel.getmSteps().observe(this,observer_steps);
        seeViewModel.getmComentaris().observe(this,observer_comentaris);
    }
}
