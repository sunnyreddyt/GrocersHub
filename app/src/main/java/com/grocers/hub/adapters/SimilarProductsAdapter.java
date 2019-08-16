package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.models.SimilarProductsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SimilarProductsAdapter extends RecyclerView.Adapter<SimilarProductsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<SimilarProductsResponse> similarProductsResponseArrayList;
    ItemClickListener itemClickListener;

    public SimilarProductsAdapter(Context mContext,
                                  ArrayList<SimilarProductsResponse> similarProductsResponseArrayList) {
        this.mContext = mContext;
        this.similarProductsResponseArrayList = similarProductsResponseArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView productNameTextView, offerCostTextView, costTextView;
        ImageView productImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
        }
    }

    @Override
    public SimilarProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list_item, parent, false);

        return new SimilarProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimilarProductsAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(similarProductsResponseArrayList.get(position).getName());
        holder.offerCostTextView.setText("₹ " + String.valueOf(similarProductsResponseArrayList.get(position).getPrice()));
        Picasso.get().load(similarProductsResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = similarProductsResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return similarProductsResponseArrayList.size();
    }

}

