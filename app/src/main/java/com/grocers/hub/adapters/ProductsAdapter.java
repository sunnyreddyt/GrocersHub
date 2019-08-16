package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ProductsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;
    Shared shared;
    ArrayList<ProductsResponse> productsResponseArrayList;

    public ProductsAdapter(Context mContext,
                           ArrayList<ProductsResponse> productsResponseArrayList) {
        this.mContext = mContext;
        this.shared = new Shared(mContext);
        this.productsResponseArrayList = productsResponseArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView productNameTextView, offerCostTextView, costTextView, addImageView;
        ImageView productImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            addImageView = (TextView) view.findViewById(R.id.addImageView);
        }
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list_item, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(productsResponseArrayList.get(position).getName());
        holder.offerCostTextView.setText(String.valueOf(productsResponseArrayList.get(position).getPrice()));
        Picasso.get().load(productsResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = productsResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });

        holder.addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserName().length() > 0) {

                } else {
                    Toast.makeText(mContext, "Please login to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsResponseArrayList.size();
    }

}
