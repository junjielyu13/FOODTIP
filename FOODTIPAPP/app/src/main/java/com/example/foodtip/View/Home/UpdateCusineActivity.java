package com.example.foodtip.View.Home;


import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Step;
import com.example.foodtip.Model.StepBuilder;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.IngredientAdapter;
import com.example.foodtip.View.ViewHolder.SliderAdapter;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.View.ViewHolder.StepsAdapter;
import com.example.foodtip.ViewModel.StepViewModel;
import com.example.foodtip.ViewModel.UpdateCusineActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateCusineActivity extends AppCompatActivity {

    private UpdateCusineActivityViewModel viewModel;
    private final String TAG = "UpdateCusine";
    private final int MAX_IMG_UP = 10;
    private EditText title, description;
    private ImageButton add_picture;
    private ImageView popup_imageview;
    private Button afegir_ingredient, afegir_steps, publicar;
    private SliderView sliderView;
    private RecyclerView ingredients_View, steps_View;
    private ActivityResultLauncher<String> getImg_food_img, getImg_step;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_new_cousine);
        setting();
    }

    private void setting() {

        title = findViewById(R.id.new_cusine_title_edi);
        add_picture = findViewById(R.id.new_cusine_img_but);
        afegir_ingredient = findViewById(R.id.new_cusine_ingre_but);
        afegir_steps = findViewById(R.id.new_cusine_steps_but);
        publicar = findViewById(R.id.new_cusine_publicar);
        sliderView = findViewById(R.id.imageSlider);
        ingredients_View = findViewById(R.id.new_cusine_recycle_ingredient);
        steps_View = findViewById(R.id.new_cusine_recycle_steps);
        description = findViewById(R.id.new_cusine_descrip_txt);

        setLiveDataObservers();

        getImg_food_img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result != null){
                            viewModel.add_picture(result);
                        }
                    }
                }
        );

        getImg_step = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result != null){
                            try {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),result);
                                viewModel.change_picture_of_step(ImageDecoder.decodeBitmap(source));
                                popup_imageview.setImageBitmap(ImageDecoder.decodeBitmap(source));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        add_picture.setOnClickListener((v)->{
            afegir_picture();
        });
        afegir_ingredient.setOnClickListener((v)->{
            afegir_ingredients(ingredients_View);
        });
        afegir_steps.setOnClickListener((v)->{
            afegir_step(steps_View);
        });
        publicar.setOnClickListener((v)->{
            publicar_recepta();
        });
    }

    private void publicar_recepta() {
        if(check_element()){
            viewModel.update_new_cusine(this,title.getText().toString(),description.getText().toString());
            Toast.makeText(this,"Cuisine Updated",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean check_element(){
        if(viewModel.getmSteps().getValue() == null){
            Toast.makeText(this,"REQUIRE AT LEAST 1 IMAGES FOR RECIPE",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(viewModel.getmIngredients().getValue() == null){
            Toast.makeText(this,"REQUIRE AT LEAST 1 INGREDIENT FOR RECIPE",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(viewModel.getmSteps().getValue() == null) {
            Toast.makeText(this, "REQUIRE AT LEAST 1 STEP FOR RECIPE", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void afegir_step(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_view_steps,null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 900);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout inputLayout_title = popupView.findViewById(R.id.popup_steps_title);
        TextInputLayout inputLayout_text = popupView.findViewById(R.id.popup_steps_txt);
        ImageView imageView = popupView.findViewById(R.id.popup_steps_img_view);
        Button saveButton = popupView.findViewById(R.id.steps_save_but);
        this.popup_imageview = imageView;


        imageView.setOnClickListener((v)->{
            getImg_step.launch("image/*");
            imageView.setImageBitmap(viewModel.get_current_step_picture());
        });

        saveButton.setOnClickListener((v)->{
            imageView.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

            String title = inputLayout_title.getEditText().getText().toString();
            String text = inputLayout_text.getEditText().getText().toString();
            Step step = new StepBuilder(UpdateCusineActivity.this)
                    .title(title)
                    .text(text)
                    .buildUser();

            viewModel.add_steps(step);
            popupWindow.dismiss();
        });
    }

    private void afegir_ingredients(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_view_ingredient,null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout inputLayout = popupView.findViewById(R.id.ingredient_name);
        Button saveButton = popupView.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            viewModel.add_ingredient(inputLayout.getEditText().getText().toString());
            popupWindow.dismiss();
        });
    }

    private void afegir_picture() {
        //View bottomView = getLayoutInflater().inflate(R.layout.bottom_dialog,null);
        BottomSheetDialog dialog = new BottomSheetDialog(UpdateCusineActivity.this);
        dialog.setContentView(R.layout.bottom_dialog);
        Button mCancel = (Button) dialog.findViewById(R.id.cancel);
        Button mAlbum = (Button) dialog.findViewById(R.id.album);
        Button mCamera = (Button) dialog.findViewById(R.id.camera);
        mAlbum.setOnClickListener(view -> {
            openAlbum();
        });
        mCamera.setOnClickListener(view -> {
            openCamera();
        });
        mCancel.setOnClickListener((v)->{
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openAlbum() {
        if(viewModel.getmImages().getValue() != null && viewModel.getmImages().getValue().size() >= MAX_IMG_UP){
            Toast.makeText(this,"Max num of picture is 10",Toast.LENGTH_SHORT).show();
        }
        else getImg_food_img.launch("image/*");
    }

    private void openCamera() {
    }

    public void setLiveDataObservers(){
        viewModel = new ViewModelProvider(this).get(UpdateCusineActivityViewModel.class);
        final Observer<ArrayList<SliderData>> observer_picture = new Observer<ArrayList<SliderData>>(){

            @Override
            public void onChanged(ArrayList<SliderData> sliderDataArrayList) {
                SliderAdapter sliderAdapter = new SliderAdapter(sliderDataArrayList);
                sliderView.setSliderAdapter(sliderAdapter,false);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                sliderView.startAutoCycle();
                sliderAdapter.notifyDataSetChanged();
            }
        };
        final Observer<ArrayList<Ingredient>> observer_ingredient = new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(ArrayList<Ingredient> ingredients) {
                IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredients,viewModel);
                ingredients_View.setLayoutManager(new LinearLayoutManager(UpdateCusineActivity.this));
                ingredients_View.swapAdapter(ingredientAdapter,false);
                ingredientAdapter.notifyDataSetChanged();
            }
        };
        final Observer<ArrayList<Step>> observer_step = new Observer<ArrayList<Step>>() {
            @Override
            public void onChanged(ArrayList<Step> steps) {
                StepsAdapter stepsAdapter = new StepsAdapter(steps,viewModel);
                steps_View.setLayoutManager(new LinearLayoutManager(UpdateCusineActivity.this));
                steps_View.swapAdapter(stepsAdapter,false);
                stepsAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getmImages().observe(this,observer_picture);
        viewModel.getmIngredients().observe(this,observer_ingredient);
        viewModel.getmSteps().observe(this,observer_step);
    }
}
