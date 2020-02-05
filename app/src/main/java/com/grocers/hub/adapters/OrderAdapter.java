package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.OrderDetailActivity;
import com.grocers.hub.R;
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

        RelativeLayout orderLayout;
        TextView orderIDTextView, orderAmountTextView/*, productsHeadingTextView*/;
        RecyclerView productsRecyclerView;
        ImageView arrowImageView;

        public MyViewHolder(View view) {
            super(view);
            orderLayout = (RelativeLayout) view.findViewById(R.id.orderLayout);
            orderIDTextView = (TextView) view.findViewById(R.id.orderIDTextView);
            orderAmountTextView = (TextView) view.findViewById(R.id.orderAmountTextView);
            productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
            arrowImageView = (ImageView) view.findViewById(R.id.arrowImageView);
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
        double totalAmountDouble = Double.parseDouble(ordersResponseArrayList.get(position).getGrandTotal());
        int totalAmount = (int) totalAmountDouble;
        holder.orderAmountTextView.setText(String.valueOf(totalAmount));

        final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.productsRecyclerView.setLayoutManager(mLayoutManager1);
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(mContext, ordersResponseArrayList.get(position).getItems(), ordersResponseArrayList.get(position).getOrderId());
        holder.productsRecyclerView.setAdapter(orderItemsAdapter);

        holder.orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderID", ordersResponseArrayList.get(position).getOrderId());
                mContext.startActivity(intent);
            }
        });

        holder.productsRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderID", ordersResponseArrayList.get(position).getOrderId());
                mContext.startActivity(intent);
            }
        });

        holder.productsRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderID", ordersResponseArrayList.get(position).getOrderId());
                mContext.startActivity(intent);
            }
        });

        holder.arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderID", ordersResponseArrayList.get(position).getOrderId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ordersResponseArrayList.size();
    }

}

