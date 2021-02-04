package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.ShippingResponse;

import java.util.ArrayList;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ShippingResponse> items;
    Shared shared;

    public OrderProductsAdapter(Context mContext, ArrayList<ShippingResponse> items) {
        this.mContext = mContext;
        this.items = items;
        shared = new Shared(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView itemLayout;
        TextView costTextView, originalPriceTextView, countTextView, productNameTextView;

        public MyViewHolder(View view) {
            super(view);

            countTextView = (TextView) view.findViewById(R.id.countTextView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
            originalPriceTextView = (TextView) view.findViewById(R.id.originalPriceTextView);
        }
    }


    @Override
    public OrderProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_products_list_item, parent, false);

        return new OrderProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderProductsAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(items.get(position).getName());
        holder.costTextView.setText("₹ " + String.valueOf(items.get(position).getRow_total()));
        int quantity = (int) items.get(position).getQty();
        holder.countTextView.setText("Qty: " + String.valueOf(quantity));

        int productFinalPrice = (int) items.get(position).getRow_total();
        double productPriceDouble = items.get(position).getBase_price();
        int productPriceInt = (int) productPriceDouble;
        double specialPriceDouble = items.get(position).getSpecial_price();
        int specialPriceInt = (int) specialPriceDouble;
        int productOriginalPrice = productPriceInt + specialPriceInt;
        int productOriginalPriceTotal = productOriginalPrice * quantity;
        if (productOriginalPriceTotal > 0 && (productFinalPrice != productOriginalPriceTotal)) {
            holder.originalPriceTextView.setVisibility(View.VISIBLE);
            holder.originalPriceTextView.setText("₹ " + productOriginalPriceTotal);
        } else {
            holder.originalPriceTextView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}