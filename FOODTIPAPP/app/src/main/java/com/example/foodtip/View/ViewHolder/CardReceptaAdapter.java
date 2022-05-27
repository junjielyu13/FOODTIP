package com.example.foodtip.View.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.Model.Recepta;
import com.example.foodtip.R;
import com.example.foodtip.View.Home.ViewRecipeActivity;
import com.example.foodtip.View.ViewHolder.OptionInterface.CMD;
import com.example.foodtip.ViewModel.HomePageViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class CardReceptaAdapter extends RecyclerView.Adapter<CardReceptaHolder> {
    private final ArrayList<Recepta> receptas;
    private final Activity activity;
    private final FoodTip foodTip;
    public CardReceptaAdapter(ArrayList<Recepta> receptas, Activity activity) {
        foodTip = FoodTip.getInstance();
        this.receptas = receptas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardReceptaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recepta_view_item,parent,false);
        return new CardReceptaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardReceptaHolder holder, int position) {
        Recepta recepta = receptas.get(position);


        TextView title = holder.getTitle();
        SliderView sliderView = holder.getImage();
        CardView cardView = holder.getCardView();
        ImageButton imageButton = holder.getLike();
        title.setText(recepta.getTitle());

        SliderAdapter sliderAdapter = new SliderAdapter(recepta.getImages());
        sliderView.setSliderAdapter(sliderAdapter,false);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        if(recepta.getLikes().contains(FoodTip.getInstance().getUser().getId())){
            imageButton.setImageResource(R.mipmap.heart2);
        }else{
            imageButton.setImageResource(R.mipmap.heart1);
        }
        title.setOnClickListener(l ->{
            receptaView(recepta);
        });

        sliderView.setOnClickListener(l->{
            receptaView(recepta);
        });

        cardView.setOnClickListener(l->{
            receptaView(recepta);
        });

        imageButton.setOnClickListener(l->{
            if(recepta.getLikes().contains(FoodTip.getInstance().getUser().getId())){
                FoodTip.getInstance().click_like(recepta,CMD.DELETE);
                imageButton.setImageResource(R.mipmap.heart1);
            }else{
                FoodTip.getInstance().click_like(recepta, CMD.ADD);
                imageButton.setImageResource(R.mipmap.heart2);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(receptas != null){
            return receptas.size();
        }
        return 0;
    }
    private void receptaView(Recepta recepta){
        Intent intent = new Intent(activity, ViewRecipeActivity.class);
        intent.putExtra("id",recepta.getId());
        intent.putExtra("title",recepta.getTitle());
        intent.putExtra("description",recepta.getDescription());
        Bundle bundle = new Bundle();
        bundle.putSerializable("picture",recepta.getImages());
        bundle.putSerializable("ingredients",recepta.getIngredients());
        bundle.putSerializable("steps",recepta.getSteps());
        bundle.putSerializable("comentaris",recepta.getComentaris());
        intent.putExtra("bundle",bundle);
        activity.startActivity(intent);
    }
}
