package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.models.OrdersResponse;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<OrdersResponse> ordersResponseArrayList;

    public OrderAdapter(Context mContext,
                        ArrayList<OrdersResponse> ordersResponseArrayList) {
        this.mContext = mContext;
        this.ordersResponseArrayList = ordersResponseArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView orderIDTextView, orderAmountTextView;
        RecyclerView productsRecyclerView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            orderIDTextView = (TextView) view.findViewById(R.id.orderIDTextView);
            orderAmountTextView = (TextView) view.findViewById(R.id.orderAmountTextView);
            productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        }
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);

        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyViewHolder holder, final int position) {

        holder.orderIDTextView.setText(ordersResponseArrayList.get(position).getOrderId());
        holder.orderAmountTextView.setText("Total: ₹ " + ordersResponseArrayList.get(position).getGrandTotal());

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.productsRecyclerView.setLayoutManager(mLayoutManager1);
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(mContext, ordersResponseArrayList.get(position).getItems());
        holder.productsRecyclerView.setAdapter(orderItemsAdapter);
    }

    @Override
    public int getItemCount() {
        return ordersResponseArrayList.size();
    }

}

