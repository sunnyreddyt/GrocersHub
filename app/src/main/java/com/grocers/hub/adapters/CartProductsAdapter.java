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

import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ctel-cpu-84 on 2/9/2018.
 */

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.MyViewHolder> {

    private Context mContext;
    JSONArray cArray;
    Shared shared;

    public CartProductsAdapter(Context mContext) {

        this.mContext = mContext;
        shared = new Shared(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView itemLayout;
        TextView quantity_textView;
        ImageView add_imageView, delete_imageView;
        TextView addTextView, countTextView, deleteTextView;

        public MyViewHolder(View view) {
            super(view);
   /*         itemLayout = (CardView) itemView.findViewById(R.id.itemLayout);
            quantity_textView = (TextView) itemView.findViewById(R.id.quantity_textView);
            add_imageView = (ImageView) itemView.findViewById(R.id.add_imageView);
            delete_imageView = (ImageView) itemView.findViewById(R.id.delete_imageView);*/

            addTextView = (TextView) itemView.findViewById(R.id.addTextView);
            countTextView = (TextView) itemView.findViewById(R.id.countTextView);
            deleteTextView = (TextView) itemView.findViewById(R.id.deleteTextView);
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
        // return cArray.length();
        return 3;
    }

    public int dpToPx(int dp) {
        float density = mContext.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}