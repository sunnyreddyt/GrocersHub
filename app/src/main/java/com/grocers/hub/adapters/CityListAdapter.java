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
import com.grocers.hub.models.City;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<City> cityArrayList;
    ItemClickListener itemClickListener;

    public CityListAdapter(Context mContext,
                           ArrayList<City> cityArrayList) {
        this.mContext = mContext;
        this.cityArrayList = cityArrayList;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView cityNameTextView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);
        }
    }

    @Override
    public CityListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);

        return new CityListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CityListAdapter.MyViewHolder holder, final int position) {

        holder.cityNameTextView.setText(cityArrayList.get(position).getCity_name());

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
        return cityArrayList.size();
    }

}
