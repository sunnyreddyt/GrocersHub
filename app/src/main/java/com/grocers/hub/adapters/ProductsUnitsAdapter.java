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

import java.util.ArrayList;

public class ProductsUnitsAdapter extends RecyclerView.Adapter<ProductsUnitsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CategoryModel> categoryModelArrayList;

    public ProductsUnitsAdapter(Context mContext,
                                ArrayList<CategoryModel> categoryModelArrayList) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView categoryNameTextView;
        ImageView categoryBackgroundImageView, categoryImageView;

        public MyViewHolder(View view) {
            super(view);
            /*itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            categoryBackgroundImageView = (ImageView) view.findViewById(R.id.categoryBackgroundImageView);
            categoryImageView = (ImageView) view.findViewById(R.id.categoryImageView);*/
        }
    }

    @Override
    public ProductsUnitsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_units_list_item, parent, false);

        return new ProductsUnitsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductsUnitsAdapter.MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

}


