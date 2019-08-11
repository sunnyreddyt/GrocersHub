package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.models.ProductDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductImagesListAdapter extends RecyclerView.Adapter<ProductImagesListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ProductDetailsResponse> media_gallery_entries;
    ItemClickListener itemClickListener;

    public ProductImagesListAdapter(Context mContext, ArrayList<ProductDetailsResponse> media_gallery_entries) {
        this.mContext = mContext;
        this.media_gallery_entries = media_gallery_entries;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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

        Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + media_gallery_entries.get(position).getFile()).into(holder.productImageView);

        holder.productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return media_gallery_entries.size();
    }

}

