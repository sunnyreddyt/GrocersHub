package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.LocationsModel;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<LocationsModel> cityArrayList;
    ItemClickListener itemClickListener;
    int[] citiesImages = new int[]{R.drawable.cone, R.drawable.ctwo, R.drawable.cfour, R.drawable.cthree,
            R.drawable.cfive, R.drawable.csix, R.drawable.cseven, R.drawable.ceight, R.drawable.cnine, R.drawable.cthree};

    public CityListAdapter(Context mContext,
                           ArrayList<LocationsModel> cityArrayList) {
        this.mContext = mContext;
        this.cityArrayList = cityArrayList;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView cityNameTextView;
        ImageView cityImageView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);
            cityImageView = (ImageView) view.findViewById(R.id.cityImageView);
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


        if (cityArrayList.get(position).getCity().length() > 0) {
            holder.cityNameTextView.setText(cityArrayList.get(position).getCity().substring(0, 1).toUpperCase() + cityArrayList.get(position).getCity().substring(1, cityArrayList.get(position).getCity().toString().length()));
        }
        holder.cityImageView.setImageResource(citiesImages[position]);
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
