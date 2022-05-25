package com.example.foodtip.View.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;
import com.smarteist.autoimageslider.SliderView;

public class CardReceptaHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private SliderView image;
    private ImageButton like;
    private CardView cardView;

    public CardReceptaHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.recepta_title);
        this.image = itemView.findViewById(R.id.recepta_imageSlider);
        this.like = itemView.findViewById(R.id.like_button);
        this.cardView = itemView.findViewById(R.id.recepta_card_view);
    }

    public TextView getTitle() {
        return title;
    }

    public SliderView getImage() {
        return image;
    }

    public ImageButton getLike() {
        return like;
    }

    public CardView getCardView(){
        return cardView;
    }


}
