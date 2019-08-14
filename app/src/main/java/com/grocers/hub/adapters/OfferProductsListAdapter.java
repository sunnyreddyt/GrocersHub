package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferProductsListAdapter extends RecyclerView.Adapter<OfferProductsListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<HomeResponse> homeResponseArrayList;

    public OfferProductsListAdapter(Context mContext,
                                    ArrayList<HomeResponse> homeResponseArrayList) {
        this.mContext = mContext;
        this.homeResponseArrayList = homeResponseArrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        ImageView productImageView;
        TextView productNameTextView, addImageView, offerCostTextView;


        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            /*
            categoryBackgroundImageView = (ImageView) view.findViewById(R.id.categoryBackgroundImageView);
            */
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            addImageView = (TextView) view.findViewById(R.id.addImageView);
        }
    }

    @Override
    public OfferProductsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_products_list_item, parent, false);

        return new OfferProductsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OfferProductsListAdapter.MyViewHolder holder, final int position) {

        Picasso.get().load(homeResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.offerCostTextView.setText(String.valueOf(homeResponseArrayList.get(position).getPrice()));
        holder.productNameTextView.setText(String.valueOf(homeResponseArrayList.get(position).getName()));

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = homeResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });

        holder.addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return homeResponseArrayList.size();
    }

}
