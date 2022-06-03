package com.example.foodtip.View.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtip.R;
import com.example.foodtip.ViewModel.SearchViewModel;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagHolder> {
    private final ArrayList<String> tags;
    private final SearchViewModel viewModel;
    private static final int SEARCH = 0;
    private static final int HISTORY = 1;
    private final int mode;
    public TagAdapter(ArrayList<String> tags, int mode,SearchViewModel viewModel) {
        this.tags = tags;
        this.mode = mode;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag,parent,false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        switch (this.mode){
            case SEARCH:
                holder.getTextView().setText(tags.get(position));
                holder.getDelete_but().setOnClickListener(l->{
                    viewModel.DeleteTag(tags.get(position));
                });
                break;
            case HISTORY:
                holder.getTextView().setText(tags.get(position));
                holder.getTextView().setOnClickListener(l->{
                    viewModel.addTag(tags.get(position));
                });
                holder.getDelete_but().setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if(tags != null){
            return tags.size();
        }
        return 0;
    }

}
