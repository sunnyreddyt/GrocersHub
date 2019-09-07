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
import com.grocers.hub.models.CouponListResponseModel;

import java.util.ArrayList;

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CouponListResponseModel> couponListResponseModelArrayList;
    OnCouponClick onClick;

    public CouponListAdapter(Context mContext,
                             ArrayList<CouponListResponseModel> couponListResponseModelArrayList) {
        this.mContext = mContext;
        this.couponListResponseModelArrayList = couponListResponseModelArrayList;
    }


    public void setClickListener(OnCouponClick onClick) {
        this.onClick = onClick;
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
    public CouponListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_list_item, parent, false);

        return new CouponListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CouponListAdapter.MyViewHolder holder, final int position) {

        CouponListResponseModel couponListResponseModel = couponListResponseModelArrayList.get(position);
        holder.categoryNameTextView.setText(couponListResponseModel.getCode());
        if (couponListResponseModel.isCategoryBackground()) {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#01d365"));
            holder.categoryNameTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        } else {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#8b8b8b"));
            holder.categoryNameTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.onCouponClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return couponListResponseModelArrayList.size();
    }

}

