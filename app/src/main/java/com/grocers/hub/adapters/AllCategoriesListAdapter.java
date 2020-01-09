package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CategoryProductsActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.models.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllCategoriesListAdapter extends RecyclerView.Adapter<AllCategoriesListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CategoryModel> categoryModelArrayList;
    ItemClickListener itemClickListener;

    public AllCategoriesListAdapter(Context mContext,
                                    ArrayList<CategoryModel> categoryModelArrayList) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView categoryNameTextView;
        ImageView categoryImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            categoryImageView = (ImageView) view.findViewById(R.id.categoryImageView);
        }
    }

    @Override
    public AllCategoriesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_category_list_item, parent, false);

        return new AllCategoriesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllCategoriesListAdapter.MyViewHolder holder, final int position) {

        CategoryModel categoryModel = categoryModelArrayList.get(position);
//        holder.categoryImageView.setImageResource(R.drawable.ic_categories_black);
        Log.v("image_path", Constants.CATEGORY_IMAGE__BASE_URL + categoryModel.getImage());
        Picasso.get().load(Constants.CATEGORY_IMAGE__BASE_URL + categoryModel.getImage()).into(holder.categoryImageView);

        holder.categoryNameTextView.setText(categoryModel.getName());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CategoryProductsActivity.class);
                intent.putExtra("id", String.valueOf(categoryModelArrayList.get(position).getId()));
                intent.putExtra("name", categoryModelArrayList.get(position).getName());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

}