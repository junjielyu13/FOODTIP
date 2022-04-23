package com.example.foodtip.View.Home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.SliderAdapter;
import com.example.foodtip.View.ViewHolder.SliderData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class UpdateCusineActivity extends AppCompatActivity {

    private final int MAX_IMG_UP = 10;
    private EditText title;
    private ImageButton add_picture;
    private Button afegir_ingredient, afegir_steps, publicar;
    private SliderView sliderView;
    private ArrayList<SliderData> images;
    private ActivityResultLauncher<SliderData> getImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_new_cousine);
        images = new ArrayList<>();
        String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
        String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
        String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";
        images.add(new SliderData(url1));
        images.add(new SliderData(url2));
        images.add(new SliderData(url3));

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

        /*getImg = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result != null){
                        }
                    }
                }
        );*/

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
        Button mAlbum = (Button) dialog.findViewById(R.id.album);
        Button mCamera = (Button) dialog.findViewById(R.id.camera);
        mAlbum.setOnClickListener(view -> {
        });
        mCamera.setOnClickListener(view -> {
            openCamera();
        });
        mCancel.setOnClickListener((v)->{
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openCamera() {
    }

    private void openAlbum() {
    }


}
