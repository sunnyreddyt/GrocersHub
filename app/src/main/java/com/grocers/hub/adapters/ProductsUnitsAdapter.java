package com.grocers.hub.adapters;

import android.content.Context;
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
import com.grocers.hub.models.ProductDetailsResponse;

import java.util.ArrayList;

public class ProductsUnitsAdapter extends RecyclerView.Adapter<ProductsUnitsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ProductDetailsResponse> options;
    int selectedUnitPosition = 0;

    public ProductsUnitsAdapter(Context mContext,
                                ArrayList<ProductDetailsResponse> options, int selectedUnitPosition) {
        this.mContext = mContext;
        this.options = options;
        this.selectedUnitPosition = selectedUnitPosition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productUnitTextView;

        public MyViewHolder(View view) {
            super(view);
            productUnitTextView = (TextView) view.findViewById(R.id.productUnitTextView);
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

        if (selectedUnitPosition == position) {
            holder.productUnitTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.productUnitTextView.setBackgroundColor(Color.parseColor("#01d365"));
        } else {
            holder.productUnitTextView.setTextColor(Color.parseColor("#000000"));
            holder.productUnitTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.productUnitTextView.setText(options.get(position).getDefault_title());
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

}


