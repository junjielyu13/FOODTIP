package com.example.foodtip.View.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtip.Model.Step;
import com.example.foodtip.R;
import com.example.foodtip.ViewModel.UpdateCusineActivityViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsHolder> {
    private final ArrayList<Step> steps;
    private final UpdateCusineActivityViewModel updateCusineActivityViewModel;

    public StepsAdapter(ArrayList<Step> steps, UpdateCusineActivityViewModel updateCusineActivityViewModel) {
        this.steps = steps;
        this.updateCusineActivityViewModel = updateCusineActivityViewModel;
    }

    @NonNull
    @Override
    public StepsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps,parent,false);

        return new StepsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsHolder holder, int position) {
        Step step = steps.get(position);
        TextView title = holder.getTitle();
        TextView text = holder.getText();
        ImageButton imageButton = holder.getBut();
        ImageView imageView = holder.getFoto();

        title.setText(step.getTitle());
        text.setText(step.getText());
        imageView.setImageBitmap(step.getImages());
        imageButton.setOnClickListener((v)->{
            this.updateCusineActivityViewModel.remove_steps(step);
        });
    }

    @Override
    public int getItemCount() {
        if(steps != null){
            return steps.size();
        }
        return 0;
    }
}
