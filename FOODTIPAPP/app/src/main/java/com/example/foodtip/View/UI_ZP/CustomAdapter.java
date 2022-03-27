package com.example.foodtip.View.UI_ZP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<ReceptaViewHolder> {
    private final ArrayList<String> data_list;
    private final Context parentContext;

    public CustomAdapter(ArrayList<String> data_list, Context context){
        this.parentContext = context;
        this.data_list = data_list;
    }
    @NonNull
    @Override
    public ReceptaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recepta_view_item,parent,false);
        return new ReceptaViewHolder(view);
    }
    //Replace the content of a view
    @Override
    public void onBindViewHolder(@NonNull ReceptaViewHolder holder, int position) {
        holder.getTitle().setText(data_list.get(position));
    }
    @Override
    public int getItemCount() {
        if(data_list != null){
            return data_list.size();
        }
        return 0;
    }
}
