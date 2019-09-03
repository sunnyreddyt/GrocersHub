package com.grocers.hub.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CategoryModel> categoryModelArrayList;
    ItemClickListener itemClickListener;

    public SubCategoriesAdapter(Context mContext,
                                ArrayList<CategoryModel> categoryModelArrayList) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView categoryNameTextView;
        ImageView categoryBackgroundImageView, categoryImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
        }
    }

    @Override
    public SubCategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_list_item, parent, false);

        return new SubCategoriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubCategoriesAdapter.MyViewHolder holder, final int position) {

        CategoryModel categoryModel = categoryModelArrayList.get(position);
        holder.categoryNameTextView.setText(categoryModel.getName());
        if (categoryModel.isCategoryBackground()) {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#01d365"));
            holder.categoryNameTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        } else {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#8b8b8b"));
            holder.categoryNameTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
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
        return categoryModelArrayList.size();
    }

}
