package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    ItemClickListener itemClickListener;
    int selectedPaymentPosition;

    public void setCLickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PaymentAdapter(Context mContext,
                          ArrayList<ShippingResponse> shippingResponseArrayList, int selectedPaymentPosition) {
        this.mContext = mContext;
        this.shippingResponseArrayList = shippingResponseArrayList;
        this.selectedPaymentPosition = selectedPaymentPosition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton paymentTypeRadioButton;

        public MyViewHolder(View view) {
            super(view);

            paymentTypeRadioButton = (RadioButton) view.findViewById(R.id.paymentTypeRadioButton);
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

        holder.paymentTypeRadioButton.setText(shippingResponseArrayList.get(position).getTitle());

        if (position == selectedPaymentPosition) {
            holder.paymentTypeRadioButton.setChecked(true);
        } else {
            holder.paymentTypeRadioButton.setChecked(false);
        }

        holder.paymentTypeRadioButton.setOnClickListener(new View.OnClickListener() {
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
        return shippingResponseArrayList.size();
    }

}



