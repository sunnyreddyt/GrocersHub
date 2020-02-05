package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.OrderDetailsResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetailsProductAdapter extends RecyclerView.Adapter<OrderDetailsProductAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<OrderDetailsResponse> items;
    Shared shared;

    public OrderDetailsProductAdapter(Context mContext, ArrayList<OrderDetailsResponse> items) {
        this.mContext = mContext;
        this.items = items;
        shared = new Shared(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView itemLayout;
        TextView costTextView, countTextView, productNameTextView;

        public MyViewHolder(View view) {
            super(view);

            countTextView = (TextView) view.findViewById(R.id.countTextView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
        }
    }


    @Override
    public OrderDetailsProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_products_list_item, parent, false);

        return new OrderDetailsProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderDetailsProductAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(items.get(position).getName());

        double priceDouble = Double.parseDouble(items.get(position).getPrice());
        String price = new DecimalFormat("##.##").format(priceDouble);
        holder.costTextView.setText("₹ " + price);
        double qtyDouble = Double.parseDouble(items.get(position).getQty());
        int qtyInt = (int) qtyDouble;
        holder.countTextView.setText("Qty: " + String.valueOf(qtyInt));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
