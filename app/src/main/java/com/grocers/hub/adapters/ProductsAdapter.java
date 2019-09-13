package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ProductsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;
    Shared shared;
    ArrayList<ProductsResponse> productsResponseArrayList;
    OnProductClickListener onProductClickListener;
    ArrayList<String> productIsAddedCartArrayList;
    ArrayList<String> productOptions = new ArrayList<String>();

    public ProductsAdapter(Context mContext, ArrayList<ProductsResponse> productsResponseArrayList, ArrayList<String> productIsAddedCartArrayList) {
        this.mContext = mContext;
        this.shared = new Shared(mContext);
        this.productsResponseArrayList = productsResponseArrayList;
        this.productIsAddedCartArrayList = productIsAddedCartArrayList;
    }

    public void setClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        RelativeLayout optionsLayout;
        TextView productNameTextView, offerCostTextView, costTextView, addTextView;
        ImageView productImageView;
        Spinner optionsSpinner;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            optionsSpinner = (Spinner) view.findViewById(R.id.optionsSpinner);
            optionsLayout = (RelativeLayout) view.findViewById(R.id.optionsLayout);
        }
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_products_list_item, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(productsResponseArrayList.get(position).getName());
        holder.offerCostTextView.setText("₹ " + String.valueOf(productsResponseArrayList.get(position).getFinalPrice()));
        Picasso.get().load(productsResponseArrayList.get(position).getImage()).into(holder.productImageView);

        if (productIsAddedCartArrayList.get(position).equalsIgnoreCase("yes")) {
            holder.addTextView.setText("ADDED");
        } else {
            holder.addTextView.setText("ADD");
        }

        productOptions = new ArrayList<String>();
        if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
            holder.offerCostTextView.setText("₹ " + String.valueOf(productsResponseArrayList.get(position).getOptions().get(0).getFinalPrice()));
            holder.optionsSpinner.setVisibility(View.VISIBLE);
            holder.optionsLayout.setVisibility(View.VISIBLE);
            for (int k = 0; k < productsResponseArrayList.get(position).getOptions().size(); k++) {
                productOptions.add(productsResponseArrayList.get(position).getOptions().get(k).getDefault_title());
            }

            ArrayAdapter aa = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, productOptions);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.optionsSpinner.setAdapter(aa);

            holder.optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.v("selected", String.valueOf(i));
                    holder.offerCostTextView.setText(productsResponseArrayList.get(position).getOptions().get(i).getFinalPrice());
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

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = productsResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });

        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserName().length() > 0) {
                    if (holder.addTextView.getText().toString().equalsIgnoreCase("ADD")) {
                        if (onProductClickListener != null) {
                            onProductClickListener.onProductClick(position, holder.optionsSpinner.getSelectedItemPosition());
                        }
                    } else {
                        Toast.makeText(mContext, "Product is already in Cart", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please login to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsResponseArrayList.size();
    }

}
