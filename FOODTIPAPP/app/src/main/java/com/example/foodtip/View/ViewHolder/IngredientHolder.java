package com.example.foodtip.View.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;

public class IngredientHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    private final ImageButton delete_but;
    public IngredientHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.ingredients_txt);
        delete_but = itemView.findViewById(R.id.ingredients_but);
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageButton getDelete_but(){
        return delete_but;
    }

}
