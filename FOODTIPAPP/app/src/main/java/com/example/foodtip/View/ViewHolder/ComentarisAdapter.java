package com.example.foodtip.View.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.Model.Comentari;
import com.example.foodtip.Model.FoodTip;
import com.example.foodtip.R;
import com.example.foodtip.View.ViewHolder.OptionInterface.CMD;

import java.util.ArrayList;

public class ComentarisAdapter extends RecyclerView.Adapter<ComentariHolder> {
    private final ArrayList<Comentari> comentaris;

    public ComentarisAdapter(ArrayList<Comentari> comentaris){
        this.comentaris = comentaris;
    }
    @NonNull
    @Override
    public ComentariHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comentaris_layout,parent,false);
        return new ComentariHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentariHolder holder, int position) {
        Comentari comentari = comentaris.get(position);
        System.out.println(comentari.getAutor());
        TextView textView = holder.getTextView();
        textView.setText(comentari.getComment());
    }

    @Override
    public int getItemCount() {
        if(comentaris != null){
            return comentaris.size();
        }
        return 0;
    }
}
