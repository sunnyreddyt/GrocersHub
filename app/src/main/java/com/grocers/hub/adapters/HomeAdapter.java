package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    ItemClickListener itemClickListener;
    private ArrayList<HomeResponse> categoryProducts;

    public HomeAdapter(Context mContext,
                       ArrayList<HomeResponse> categoryProducts) {
        this.mContext = mContext;
        this.categoryProducts = categoryProducts;
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView categoryNameTextView, viewAllTextView;
        RecyclerView productsRecyclerView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            viewAllTextView = (TextView) view.findViewById(R.id.viewAllTextView);
            productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        }
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        return new HomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {


        holder.categoryNameTextView.setText(categoryProducts.get(position).getName());

        holder.viewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.productsRecyclerView.setLayoutManager(mLayoutManager1);
        OfferProductsListAdapter offerProductsListAdapter = new OfferProductsListAdapter(mContext, categoryProducts.get(position).getProducts());
        holder.productsRecyclerView.setAdapter(offerProductsListAdapter);


    }

    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }

}
