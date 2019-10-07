package com.grocers.hub.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CartActivity;
import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.CartResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctel-cpu-84 on 2/9/2018.
 */

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<CartResponse> cartResponseArrayList;
    Shared shared;
    private OnCartUpdateListener onCartUpdateListener;
    private List<OfflineCartProduct> offlineCartProductList;

    public void setItemClickListener(OnCartUpdateListener onCartUpdateListener) {
        this.onCartUpdateListener = onCartUpdateListener;
    }

    public CartProductsAdapter(Context mContext, ArrayList<CartResponse> cartResponseArrayList, List<OfflineCartProduct> offlineCartProductList) {
        this.mContext = mContext;
        shared = new Shared(mContext);
        this.cartResponseArrayList = cartResponseArrayList;
        this.offlineCartProductList = offlineCartProductList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView, deleteImageView;
        CardView itemLayout;
        TextView costTextView, addTextView, countTextView, deleteTextView, productNameTextView, productQuantityTextView, offerCostTextView;

        public MyViewHolder(View view) {
            super(view);

            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            itemLayout = (CardView) view.findViewById(R.id.itemLayout);
            deleteImageView = (ImageView) view.findViewById(R.id.deleteImageView);
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
        Picasso.get().load(cartResponseArrayList.get(position).getImage()).into(holder.productImageView);
        holder.offerCostTextView.setText(String.valueOf(cartResponseArrayList.get(position).getPrice()));
        holder.costTextView.setVisibility(View.INVISIBLE);
        holder.countTextView.setText(String.valueOf(cartResponseArrayList.get(position).getQty()));
        if (cartResponseArrayList.get(position).getDefault_title() != null && cartResponseArrayList.get(position).getDefault_title().length() > 0) {
            holder.productQuantityTextView.setText(cartResponseArrayList.get(position).getDefault_title());
        }

        if (position == 0) {
            CartActivity.totalAmount = 0;
        }
        if (position==cartResponseArrayList.size()-1){
            ((CartActivity)mContext).updateCartProductsTotalPrice();
        }

        double priceDouble = cartResponseArrayList.get(position).getPrice();
        int price = (int) priceDouble;
        int quantity = cartResponseArrayList.get(position).getQty();
        int productPrice = price * quantity;
        CartActivity.totalAmount = CartActivity.totalAmount + productPrice;

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                String skuID = cartResponseArrayList.get(position).getParentSkuID();
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
                /*if (onCartUpdateListener != null) {
                    onCartUpdateListener.onCartUpdate(cartResponseArrayList.get(position), "add", count);
                }*/
                offlineCartProductList.get(position).setQty(count);
                updateCartProductOffline(offlineCartProductList.get(position), "update");


                double priceDouble = cartResponseArrayList.get(position).getPrice();
                int price = (int) priceDouble;
                int quantity = cartResponseArrayList.get(position).getQty();
                int productPrice = price * quantity;
                CartActivity.totalAmount = CartActivity.totalAmount + productPrice;
                ((CartActivity)mContext).updateCartProductsTotalPrice();

            }
        });

        holder.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                if (count > 1) {
                    count--;
                    holder.countTextView.setText(String.valueOf(count));
                   /* if (onCartUpdateListener != null) {
                        onCartUpdateListener.onCartUpdate(cartResponseArrayList.get(position), "remove", count);
                    }*/
                    offlineCartProductList.get(position).setQty(count);
                    updateCartProductOffline(offlineCartProductList.get(position), "update");

                    double priceDouble = cartResponseArrayList.get(position).getPrice();
                    int price = (int) priceDouble;
                    int quantity = cartResponseArrayList.get(position).getQty();
                    int productPrice = price * quantity;
                    CartActivity.totalAmount = CartActivity.totalAmount - productPrice;
                    ((CartActivity)mContext).updateCartProductsTotalPrice();

                }
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (onCartUpdateListener != null) {
                    onCartUpdateListener.onCartUpdate(cartResponseArrayList.get(position), "delete", 1);
                }*/
                new AlertDialog.Builder(mContext)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("Are you sure, you want to remove from cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateCartProductOffline(offlineCartProductList.get(position), "delete");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        })
                        .show();
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

    public void updateCartProductOffline(final OfflineCartProduct offlineCartProduct, final String type) {
        class AddCartProductOffline extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {

                if (type.equalsIgnoreCase("update")) {
                    DatabaseClient
                            .getInstance(mContext)
                            .getAppDatabase()
                            .offlineCartDao()
                            .update(offlineCartProduct);
                } else if (type.equalsIgnoreCase("delete")) {
                    DatabaseClient
                            .getInstance(mContext)
                            .getAppDatabase()
                            .offlineCartDao()
                            .delete(offlineCartProduct);

                    onCartUpdateListener.onCartUpdate();
                }

                return "";
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);

            }
        }
        new AddCartProductOffline().execute();
    }

}