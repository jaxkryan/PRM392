package com.example.myshopping.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myshopping.Domain.Foods;
import com.example.myshopping.R;

import java.util.ArrayList;
import java.lang.reflect.Array;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.viewHolder> {
    ArrayList<Foods> items;
    Context context;

    public BestFoodAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestFoodAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_best_deal, parent, false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.viewHolder holder, int position) {
        // Set text for the other fields
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.starTxt.setText("" + items.get(position).getStar());
        holder.timeTxt.setText(items.get(position).getTimeValue() + " min");

        // Get the image path
        String imagePath = items.get(position).getImagePath();

        // Check if the imagePath is a URL or a drawable resource name
        if (imagePath.startsWith("https://")) {
            // Load image from URL using Glide
            Glide.with(context)
                    .load(imagePath)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        } else {
            // Load image from drawable
            loadImageFromDrawable(holder.pic, imagePath);
        }
    }

    // Method to load image from drawable resources
    public void loadImageFromDrawable(ImageView imageView, String imagePath) {
        // Get the resource ID of the image in drawable using the name from ImagePath
        int resourceId = imageView.getContext().getResources().getIdentifier(imagePath, "drawable", imageView.getContext().getPackageName());

        // Set the image to the ImageView if the resource ID is valid
        if (resourceId != 0) {
            Glide.with(imageView.getContext())
                    .load(resourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(imageView);
        } else {
            // Handle the case where the image is not found
            Log.e("ImageLoader", "Drawable resource not found for path: " + imagePath);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, starTxt, timeTxt;
        ImageView pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            starTxt = itemView.findViewById(R.id.starTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
