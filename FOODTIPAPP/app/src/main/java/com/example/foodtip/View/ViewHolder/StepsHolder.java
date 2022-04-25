package com.example.foodtip.View.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;

public class StepsHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private ImageButton but;
    private TextView text;
    private ImageView foto;
    public StepsHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.steps_title);
        this.but = itemView.findViewById(R.id.steps_img_but);
        this.text = itemView.findViewById(R.id.steps_text);
        this.foto = itemView.findViewById(R.id.steps_picture);
    }

    public TextView getTitle() {
        return title;
    }

    public ImageButton getBut() {
        return but;
    }

    public TextView getText() {
        return text;
    }

    public ImageView getFoto() {
        return foto;
    }
}
