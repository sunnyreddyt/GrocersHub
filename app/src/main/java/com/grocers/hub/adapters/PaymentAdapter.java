package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ShippingResponse;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<ShippingResponse> shippingResponseArrayList;

    public PaymentAdapter(Context mContext,
                          ArrayList<ShippingResponse> shippingResponseArrayList) {
        this.mContext = mContext;
        this.shippingResponseArrayList = shippingResponseArrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView paymentTypeTextView;

        public MyViewHolder(View view) {
            super(view);

            paymentTypeTextView = (TextView) view.findViewById(R.id.paymentTypeTextView);
        }
    }

    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payments_item, parent, false);

        return new PaymentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentAdapter.MyViewHolder holder, final int position) {

        holder.paymentTypeTextView.setText(shippingResponseArrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return shippingResponseArrayList.size();
    }

}



