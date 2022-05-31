package com.example.foodtip.View.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;

public class ComentariHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    public ComentariHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.comentaris_item_txt);
    }

    public TextView getTextView() {
        return textView;
    }
}
