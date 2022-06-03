package com.example.foodtip.View.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.foodtip.Model.SliderData;
import com.example.foodtip.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {
    private List<SliderData> images;

    public SliderAdapter(ArrayList<SliderData> sliderDataArrayList) {
        this.images = sliderDataArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        SliderData sliderData = images.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderData.getImgUri())
                .fitCenter()
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        if(images == null){
            return 0;
        }
        return images.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image_view);
        }
    }
}
