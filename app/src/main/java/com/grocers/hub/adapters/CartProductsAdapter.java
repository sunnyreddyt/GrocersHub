package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CartResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ctel-cpu-84 on 2/9/2018.
 */

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CartResponse> cartResponseArrayList;
    Shared shared;

    public CartProductsAdapter(Context mContext, ArrayList<CartResponse> cartResponseArrayList) {

        this.mContext = mContext;
        this.cartResponseArrayList = cartResponseArrayList;
        shared = new Shared(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        CardView itemLayout;
        TextView costTextView, addTextView, countTextView, deleteTextView, productNameTextView, productQuantityTextView, offerCostTextView;

        public MyViewHolder(View view) {
            super(view);

            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            itemLayout = (CardView) view.findViewById(R.id.itemLayout);
            countTextView = (TextView) view.findViewById(R.id.countTextView);
            deleteTextView = (TextView) view.findViewById(R.id.deleteTextView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            productQuantityTextView = (TextView) view.findViewById(R.id.productQuantityTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
        }
    }


    @Override
    public CartProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_products_list_item, parent, false);

        return new CartProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartProductsAdapter.MyViewHolder holder, final int position) {


        holder.productNameTextView.setText(cartResponseArrayList.get(position).getName());
        Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + cartResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.offerCostTextView.setText(String.valueOf(cartResponseArrayList.get(position).getPrice()));
        holder.costTextView.setVisibility(View.INVISIBLE);

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = cartResponseArrayList.get(position).getSku();
                intent.putExtra("skuID", skuID);
                mContext.startActivity(intent);
            }
        });

        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count++;
                holder.countTextView.setText(String.valueOf(count));
            }
        });

        holder.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                if (count > 0) {
                    count--;
                    holder.countTextView.setText(String.valueOf(count));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return cartResponseArrayList.size();
    }

    public int dpToPx(int dp) {
        float density = mContext.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}