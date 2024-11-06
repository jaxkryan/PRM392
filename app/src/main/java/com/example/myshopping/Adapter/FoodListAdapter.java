package com.example.myshopping.Adapter;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import com.example.myshopping.Activity.DetailActivity;
import com.example.myshopping.Domain.Foods;
import com.example.myshopping.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.viewHolder> {
    ArrayList<Foods> items;
    Context context;

    public FoodListAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FoodListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_food,parent, false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.viewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.timeTxt.setText(items.get(position).getTimeValue() + "min");
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.rateTxt.setText("" + items.get(position).getStar());

//        Glide.with(context)
//                .load(items.get(position).getImagePath())
//                .transform(new CenterCrop(), new RoundedCorners(30))
//                .into(holder.pic);
        // Get the image path
        String imagePath = items.get(position).getImagePath();
        // Check if the imagePath is a URL or a drawable resource name
            loadImageFromDrawable(holder.pic, imagePath);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", (Serializable) items.get(position));
            context.startActivity(intent);
        });
    }
    // Method to load image from drawable resources
    public void loadImageFromDrawable(ImageView imageView, String imagePath) {
        int resourceId = imageView.getContext().getResources().getIdentifier(imagePath, "drawable", imageView.getContext().getPackageName());

        if (resourceId != 0) {
            Glide.with(imageView.getContext())
                    .load(resourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(imageView);
        } else {
            Log.e("ImageLoader", "Drawable resource not found for path: " + imagePath);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, rateTxt, timeTxt;
        ImageView pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            rateTxt = itemView.findViewById(R.id.rateTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            pic = itemView.findViewById(R.id.img);
        }
    }
}
