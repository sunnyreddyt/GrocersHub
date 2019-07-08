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
import com.grocers.hub.models.CategoryModel;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CategoryModel> categoryModelArrayList;

    public ProductsAdapter(Context mContext,
                                    ArrayList<CategoryModel> categoryModelArrayList) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView categoryNameTextView;
        ImageView categoryBackgroundImageView, categoryImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            /*categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            categoryBackgroundImageView = (ImageView) view.findViewById(R.id.categoryBackgroundImageView);
            categoryImageView = (ImageView) view.findViewById(R.id.categoryImageView);*/
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

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
