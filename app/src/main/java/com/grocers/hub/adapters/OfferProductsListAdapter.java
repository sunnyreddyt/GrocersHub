package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferProductsListAdapter extends RecyclerView.Adapter<OfferProductsListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<HomeResponse> homeResponseArrayList;
    OnOfferProductClickListener itemClickListener;
    Shared shared;
    int parentPosition;
    ArrayList<String> productOptions = new ArrayList<String>();

    public OfferProductsListAdapter(Context mContext,
                                    ArrayList<HomeResponse> homeResponseArrayList, int parentPosition) {
        this.mContext = mContext;
        this.homeResponseArrayList = homeResponseArrayList;
        this.shared = new Shared(mContext);
        this.parentPosition = parentPosition;
    }

    public void setClickListener(OnOfferProductClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        Spinner optionsSpinner;
        ImageView productImageView;
        RelativeLayout optionsLayout;
        TextView productNameTextView, addTextView, offerCostTextView, costTextView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            /*
            categoryBackgroundImageView = (ImageView) view.findViewById(R.id.categoryBackgroundImageView);
            */
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            optionsSpinner = (Spinner) view.findViewById(R.id.optionsSpinner);
            optionsLayout = (RelativeLayout) view.findViewById(R.id.optionsLayout);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
        }
    }

    @Override
    public OfferProductsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_products_list_item, parent, false);

        return new OfferProductsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OfferProductsListAdapter.MyViewHolder holder, final int position) {

        Picasso.get().load(homeResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.offerCostTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getPrice()));
        holder.productNameTextView.setText(String.valueOf(homeResponseArrayList.get(position).getName()));
        holder.costTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getPrice()));
        if (homeResponseArrayList.get(position).getPrice() == homeResponseArrayList.get(position).getPrice()) {
            holder.costTextView.setVisibility(View.GONE);
        } else {
            holder.costTextView.setVisibility(View.VISIBLE);
        }

        for (int g = 0; g < HomeAdapter.cartResponseArrayList.size(); g++) {
            if (HomeAdapter.cartResponseArrayList.get(g).getSku().equalsIgnoreCase(homeResponseArrayList.get(position).getSku())) {
                holder.addTextView.setText("ADDED");
            }
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = homeResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });

        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserName().length() > 0) {
                    if (itemClickListener != null && holder.addTextView.getText().toString().equalsIgnoreCase("ADD")) {
                        itemClickListener.onOfferProductClick(parentPosition, position);
                    }
                } else {
                    Toast.makeText(mContext, "Please login to add", Toast.LENGTH_SHORT).show();
                }
            }
        });


        productOptions = new ArrayList<String>();
        if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
            holder.offerCostTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice()));
            holder.optionsSpinner.setVisibility(View.VISIBLE);
            holder.optionsLayout.setVisibility(View.VISIBLE);
            for (int k = 0; k < homeResponseArrayList.get(position).getOptions().size(); k++) {
                productOptions.add(homeResponseArrayList.get(position).getOptions().get(k).getDefault_title());
            }

            ArrayAdapter aa = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, productOptions);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.optionsSpinner.setAdapter(aa);

            holder.optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.v("selected", String.valueOf(i));
                    holder.offerCostTextView.setText(String.valueOf(homeResponseArrayList.get(position).getOptions().get(i).getFinalPrice()));
                    double priceDouble = Double.parseDouble(homeResponseArrayList.get(position).getOptions().get(i).getPrice());
                    int proceInt = (int) priceDouble;
                    holder.offerCostTextView.setText(String.valueOf(proceInt));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            holder.optionsSpinner.setSelection(0);

        } else {
            holder.optionsSpinner.setVisibility(View.INVISIBLE);
            holder.optionsLayout.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return homeResponseArrayList.size();
    }

}
