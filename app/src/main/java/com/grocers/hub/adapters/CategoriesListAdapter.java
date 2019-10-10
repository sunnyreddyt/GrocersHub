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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CategoryProductsActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.models.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Sarveshwar Reddy on 7/10/2017.
 */

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CategoryModel> categoryModelArrayList;
    OnCategoryClickListener onCategoryClickListener;

    public CategoriesListAdapter(Context mContext,
                                 ArrayList<CategoryModel> categoryModelArrayList) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
    }


    public void setClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView categoryNameTextView;
        ImageView categoryImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            categoryImageView = (ImageView) view.findViewById(R.id.categoryImageView);
        }
    }

    @Override
    public CategoriesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);

        return new CategoriesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoriesListAdapter.MyViewHolder holder, final int position) {

        CategoryModel categoryModel = categoryModelArrayList.get(position);
        Log.v("image_path", Constants.CATEGORY_IMAGE__BASE_URL + categoryModel.getImage());
        Picasso.get().load(Constants.CATEGORY_IMAGE__BASE_URL + categoryModel.getImage()).into(holder.categoryImageView);

        if (position == 0) {
            holder.categoryImageView.setImageResource(R.drawable.ic_categories_black);
        }

        holder.categoryNameTextView.setText(categoryModel.getName());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 0) {

                    if (onCategoryClickListener != null) {
                        onCategoryClickListener.onCategoryClick(position);
                    }
                    Intent intent = new Intent(mContext, CategoryProductsActivity.class);
                    intent.putExtra("id", String.valueOf(categoryModelArrayList.get(position).getId()));
                    intent.putExtra("name", categoryModelArrayList.get(position).getName());
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

}