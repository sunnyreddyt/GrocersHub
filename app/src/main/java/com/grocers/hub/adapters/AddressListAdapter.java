package com.grocers.hub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.models.UserAddressListModel;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<UserAddressListModel> userAddressListModelArrayList;
    ItemClickListener itemClickListener;
    int selectedPaymentPosition;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AddressListAdapter(Context mContext,
                              ArrayList<UserAddressListModel> userAddressListModelArrayList, int selectedPaymentPosition) {
        this.mContext = mContext;
        this.userAddressListModelArrayList = userAddressListModelArrayList;
        this.selectedPaymentPosition = selectedPaymentPosition;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton paymentTypeRadioButton;
        TextView addressTextView, contactTextView;
        LinearLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);

            paymentTypeRadioButton = (RadioButton) view.findViewById(R.id.paymentTypeRadioButton);
            addressTextView = (TextView) view.findViewById(R.id.addressTextView);
            contactTextView = (TextView) view.findViewById(R.id.contactTextView);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
        }
    }

    @Override
    public AddressListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payments_item, parent, false);

        return new AddressListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AddressListAdapter.MyViewHolder holder, final int position) {

        holder.contactTextView.setText(userAddressListModelArrayList.get(position).getFirstname() + " (" + userAddressListModelArrayList.get(position).getTelephone() + ")");
        holder.addressTextView.setText(userAddressListModelArrayList.get(position).getStreet());
        if (position == selectedPaymentPosition) {
            holder.paymentTypeRadioButton.setChecked(true);
        } else {
            holder.paymentTypeRadioButton.setChecked(false);
        }

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
        return userAddressListModelArrayList.size();
    }

}




