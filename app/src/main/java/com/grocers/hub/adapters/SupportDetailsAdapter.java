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
import com.grocers.hub.ShippingAddressActivity;
import com.grocers.hub.models.SupportResponse;
import com.grocers.hub.models.UserAddressListModel;

import java.util.ArrayList;

public class SupportDetailsAdapter extends RecyclerView.Adapter<SupportDetailsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<SupportResponse> supportResponseArrayList;

    public SupportDetailsAdapter(Context mContext, ArrayList<SupportResponse> supportResponseArrayList) {
        this.mContext = mContext;
        this.supportResponseArrayList = supportResponseArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cityTextView, addressTextView, emailTextView, contactTextView;
        LinearLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);

            cityTextView = (TextView) view.findViewById(R.id.cityTextView);
            addressTextView = (TextView) view.findViewById(R.id.addressTextView);
            contactTextView = (TextView) view.findViewById(R.id.contactTextView);
            emailTextView = (TextView) view.findViewById(R.id.emailTextView);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
        }
    }

    @Override
    public SupportDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_details_list_item, parent, false);

        return new SupportDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SupportDetailsAdapter.MyViewHolder holder, final int position) {

        holder.cityTextView.setText(supportResponseArrayList.get(position).getCity());
        holder.emailTextView.setText(supportResponseArrayList.get(position).getEmail());
        holder.contactTextView.setText(supportResponseArrayList.get(position).getPhone());
        holder.addressTextView.setText(supportResponseArrayList.get(position).getAddrress());


    }

    @Override
    public int getItemCount() {
        return supportResponseArrayList.size();
    }

}
