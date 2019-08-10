package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ProductDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductImagesListAdapter extends RecyclerView.Adapter<ProductImagesListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ProductDetailsResponse> media_gallery_entries;

    public ProductImagesListAdapter(Context mContext, ArrayList<ProductDetailsResponse> media_gallery_entries) {
        this.mContext = mContext;
        this.media_gallery_entries = media_gallery_entries;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;

        public MyViewHolder(View view) {
            super(view);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
        }
    }

    @Override
    public ProductImagesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_images_list_item, parent, false);

        return new ProductImagesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductImagesListAdapter.MyViewHolder holder, final int position) {

        Picasso.get().load(media_gallery_entries.get(position).getFile()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return media_gallery_entries.size();
    }

}

