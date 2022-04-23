package com.example.foodtip.View.ViewHolder;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.Ingredient;
import com.example.foodtip.R;
import com.example.foodtip.View.Home.UpdateCusineActivity;
import com.example.foodtip.ViewModel.UpdateCusineActivityViewModel;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
    private final ArrayList<Ingredient> ingredients;
    private final UpdateCusineActivityViewModel updateCusineActivityViewModel;

    public IngredientAdapter(ArrayList<Ingredient> ingredients,UpdateCusineActivityViewModel viewModel) {
        this.ingredients = ingredients;
        this.updateCusineActivityViewModel = viewModel;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient,parent,false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        ImageButton imageButton = holder.getDelete_but();
        TextView textView = (TextView) holder.getTextView();
        textView.setText(ingredient.getNom());
        imageButton.setOnClickListener((v)->{
            this.updateCusineActivityViewModel.remove_ingredient(ingredient);
        });
    }

    @Override
    public int getItemCount() {
        if(ingredients != null){
            return ingredients.size();
        }
        return 0;
    }
}
