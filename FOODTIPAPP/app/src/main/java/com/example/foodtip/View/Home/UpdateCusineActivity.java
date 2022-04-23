package com.example.foodtip.View.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.SliderAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class UpdateCusineActivity extends AppCompatActivity {

    private final int MAX_IMG_UP = 10;
    private EditText title;
    private ImageButton add_picture;
    private Button afegir_ingredient, afegir_steps, publicar;
    private SliderView sliderView;
    private int[] images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_new_cousine);
        images = new int[MAX_IMG_UP];
        images[0] = R.drawable.empty;
        setting();
    }

    private void setting() {

        title = findViewById(R.id.new_cusine_title_edi);
        add_picture = findViewById(R.id.new_cusine_img_but);
        afegir_ingredient = findViewById(R.id.new_cusine_ingre_but);
        afegir_steps = findViewById(R.id.new_cusine_steps_but);
        publicar = findViewById(R.id.new_cusine_publicar);
        sliderView = findViewById(R.id.imageSlider);

        sliderAdapt();

        add_picture.setOnClickListener((v)->{
            afegir_picture();
        });
        afegir_ingredient.setOnClickListener((v)->{
            afegir_ingredients();
        });
        afegir_steps.setOnClickListener((v)->{
            afegir_step();
        });
        publicar.setOnClickListener((v)->{
            publicar_recepta();
        });
    }
    private void sliderAdapt(){
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
    private void publicar_recepta() {
    }

    private void afegir_step() {
    }

    private void afegir_ingredients() {
    }

    private void afegir_picture() {
        View bottomView = getLayoutInflater().inflate(R.layout.bottom_dialog,null);
        BottomSheetDialog dialog = new BottomSheetDialog(UpdateCusineActivity.this);
        dialog.setContentView(R.layout.bottom_dialog);
        Button mCancel = (Button) dialog.findViewById(R.id.cancel);
        mCancel.setOnClickListener((v)->{
            dialog.dismiss();
        });
        dialog.show();
    }



}
