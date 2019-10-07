package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.OrderDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.OrdersResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<OrdersResponse> ordersResponseArrayList;
    Shared shared;
    String orderID;

    public OrderItemsAdapter(Context mContext,
                             ArrayList<OrdersResponse> ordersResponseArrayList, String orderID) {
        this.mContext = mContext;
        this.ordersResponseArrayList = ordersResponseArrayList;
        this.shared = new Shared(mContext);
        this.orderID = orderID;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        ImageView productImageView;
        TextView productNameTextView, offerCostTextView;


        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
        }
    }

    @Override
    public OrderItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_products_item, parent, false);

        return new OrderItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderItemsAdapter.MyViewHolder holder, final int position) {

        Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + ordersResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.offerCostTextView.setText("₹ " + String.valueOf(ordersResponseArrayList.get(position).getPrice()));
        holder.productNameTextView.setText(String.valueOf(ordersResponseArrayList.get(position).getName()));

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderID", orderID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersResponseArrayList.size();
    }

}

