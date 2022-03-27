package com.example.foodtip.View.UI_ZP;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;


public class ReceptaViewHolder extends RecyclerView.ViewHolder {
    private final LinearLayout main;
    private final TextView title;
    private final ImageButton recepta_button;
    private final LinearLayout like_comment_layout;
    private final ImageButton like_button;
    private final ImageButton comment_button;

    public ReceptaViewHolder(View view) {
        super(view);
        main = view.findViewById(R.id.recepta_view);
        title = view.findViewById(R.id.recepta_title);
        recepta_button = view.findViewById(R.id.recepta_button);
        like_comment_layout = view.findViewById(R.id.like_comment_Layout);
        like_button = view.findViewById(R.id.like_button);
        comment_button = view.findViewById(R.id.comment_buttton);
    }

    public LinearLayout getMain() {
        return main;
    }

    public TextView getTitle() {
        return title;
    }

    public ImageButton getRecepta_button() {
        return recepta_button;
    }

    public LinearLayout getLike_comment_layout() {
        return like_comment_layout;
    }

    public ImageButton getLike_button() {
        return like_button;
    }

    public ImageButton getComment_button() {
        return comment_button;
    }
}
