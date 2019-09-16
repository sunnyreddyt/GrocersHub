package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.ProductsResponse;
import com.grocers.hub.utils.GHUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchProductsAdapter extends RecyclerView.Adapter<SearchProductsAdapter.MyViewHolder> {

    private Context mContext;
    Shared shared;
    GHUtil ghUtil;
    ArrayList<ProductsResponse> productsResponseArrayList;

    public SearchProductsAdapter(Context mContext,
                                 ArrayList<ProductsResponse> productsResponseArrayList) {
        this.mContext = mContext;
        ghUtil = GHUtil.getInstance(mContext);
        this.shared = new Shared(mContext);
        this.productsResponseArrayList = productsResponseArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView productNameTextView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
        }
    }

    @Override
    public SearchProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_products_list_item, parent, false);

        return new SearchProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchProductsAdapter.MyViewHolder holder, final int position) {

        holder.productNameTextView.setText(productsResponseArrayList.get(position).getName());
        holder.productNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ghUtil.hideKeyboard(mContext);
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = productsResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsResponseArrayList.size();
    }


}
