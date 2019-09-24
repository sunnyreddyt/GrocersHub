package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.grocers.hub.CategoryProductsActivity;
import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OfferProductsListAdapter extends RecyclerView.Adapter<OfferProductsListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<HomeResponse> homeResponseArrayList;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout, cartCountLayout;
        Spinner optionsSpinner;
        ImageView productImageView;
        RelativeLayout optionsLayout, cartAddLayout;
        TextView productNameTextView, offerCostTextView, addTextView, costTextView, minusTextView, countTextView, plusTextView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            /*
            categoryBackgroundImageView = (ImageView) view.findViewById(R.id.categoryBackgroundImageView);
            */
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
            offerCostTextView = (TextView) view.findViewById(R.id.offerCostTextView);
            optionsSpinner = (Spinner) view.findViewById(R.id.optionsSpinner);
            optionsLayout = (RelativeLayout) view.findViewById(R.id.optionsLayout);
            costTextView = (TextView) view.findViewById(R.id.costTextView);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            cartAddLayout = (RelativeLayout) view.findViewById(R.id.cartAddLayout);
            minusTextView = (TextView) view.findViewById(R.id.minusTextView);
            countTextView = (TextView) view.findViewById(R.id.countTextView);
            plusTextView = (TextView) view.findViewById(R.id.plusTextView);
            cartCountLayout = (LinearLayout) view.findViewById(R.id.cartCountLayout);
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
        holder.offerCostTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getFinalPrice()));
        holder.productNameTextView.setText(String.valueOf(homeResponseArrayList.get(position).getName()));
        holder.costTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getPrice()));
        if (homeResponseArrayList.get(position).getPrice() == homeResponseArrayList.get(position).getFinalPrice()) {
            holder.costTextView.setVisibility(View.GONE);
        } else {
            holder.costTextView.setVisibility(View.VISIBLE);
        }

        /*for (int g = 0; g < HomeAdapter.cartResponseArrayList.size(); g++) {
            if (HomeAdapter.cartResponseArrayList.get(g).getSku().equalsIgnoreCase(homeResponseArrayList.get(position).getSku())) {
                holder.addTextView.setText("ADDED");
            }
        }*/

        if (homeResponseArrayList.get(position).getCartQuantity() > 0) {
            holder.cartCountLayout.setVisibility(View.VISIBLE);
            holder.cartAddLayout.setVisibility(View.GONE);
            holder.countTextView.setText(String.valueOf(homeResponseArrayList.get(position).getCartQuantity()));
        } else {
            holder.cartCountLayout.setVisibility(View.GONE);
            holder.cartAddLayout.setVisibility(View.VISIBLE);
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

        /*holder.addTextView.setOnClickListener(new View.OnClickListener() {
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
        });*/


        productOptions = new ArrayList<String>();
        if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
            holder.offerCostTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice()));

            double priceDouble = Double.parseDouble(homeResponseArrayList.get(position).getOptions().get(0).getPrice());
            int priceInt = (int) priceDouble;
            if (priceInt == homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice()) {
                holder.costTextView.setVisibility(View.GONE);
            } else {
                holder.costTextView.setVisibility(View.VISIBLE);
            }

            if (homeResponseArrayList.get(position).getOptions().get(0).getCartQuantity() > 0) {
                holder.cartCountLayout.setVisibility(View.VISIBLE);
                holder.cartAddLayout.setVisibility(View.GONE);
                holder.countTextView.setText(String.valueOf(homeResponseArrayList.get(position).getOptions().get(0).getCartQuantity()));
            } else {
                holder.cartCountLayout.setVisibility(View.GONE);
                holder.cartAddLayout.setVisibility(View.VISIBLE);
            }

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
                    holder.offerCostTextView.setText("₹ " + String.valueOf(homeResponseArrayList.get(position).getOptions().get(i).getFinalPrice()));
                    double priceDouble = Double.parseDouble(homeResponseArrayList.get(position).getOptions().get(i).getPrice());
                    int priceInt = (int) priceDouble;
                    holder.costTextView.setText("₹ " + String.valueOf(priceInt));
                    if (priceInt == homeResponseArrayList.get(position).getOptions().get(position).getFinalPrice()) {
                        holder.costTextView.setVisibility(View.GONE);
                    } else {
                        holder.costTextView.setVisibility(View.VISIBLE);
                    }


                    if (homeResponseArrayList.get(position).getOptions().get(i).getCartQuantity() > 0) {
                        holder.cartCountLayout.setVisibility(View.VISIBLE);
                        holder.cartAddLayout.setVisibility(View.GONE);
                        holder.countTextView.setText(String.valueOf(homeResponseArrayList.get(position).getOptions().get(i).getCartQuantity()));
                    } else {
                        holder.cartCountLayout.setVisibility(View.GONE);
                        holder.cartAddLayout.setVisibility(View.VISIBLE);
                    }
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

        holder.plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count++;
                holder.countTextView.setText(String.valueOf(count));

                String sku_temp = "";
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = homeResponseArrayList.get(position).getSku();
                    homeResponseArrayList.get(position).setCartQuantity(count);
                }

                updateCartProductOfflineUsingSkuID(sku_temp, count, "update");

            }
        });

        holder.minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count--;

                String sku_temp = "";
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = homeResponseArrayList.get(position).getSku();
                    homeResponseArrayList.get(position).setCartQuantity(count);
                }

                if (count > 0) {
                    holder.countTextView.setText(String.valueOf(count));
                    updateCartProductOfflineUsingSkuID(sku_temp, count, "update");
                } else if (count == 0) {
                    holder.cartCountLayout.setVisibility(View.GONE);
                    holder.cartAddLayout.setVisibility(View.VISIBLE);
                    updateCartProductOfflineUsingSkuID(sku_temp, count, "delete");
                }
            }
        });


        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    offlineCartProduct.setProduct_id(Integer.parseInt(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getProduct_id()));
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku());
                    offlineCartProduct.setValue_index(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getValue_index());
                    offlineCartProduct.setPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getPrice());
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getFinalPrice());
                    offlineCartProduct.setDefault_title(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getDefault_title());
                    homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(1);
                } else {
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getSku());
                    offlineCartProduct.setPrice(String.valueOf(homeResponseArrayList.get(position).getPrice()));
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getFinalPrice());
                    homeResponseArrayList.get(position).setCartQuantity(1);
                }
                offlineCartProduct.setQty(1);
                offlineCartProduct.setParentSkuID(homeResponseArrayList.get(position).getSku());
                offlineCartProduct.setName(homeResponseArrayList.get(position).getName());
                offlineCartProduct.setProduct_type(homeResponseArrayList.get(position).getProduct_type());
                offlineCartProduct.setImage(homeResponseArrayList.get(position).getImage());

                addCartProductOffline(offlineCartProduct);

                holder.cartCountLayout.setVisibility(View.VISIBLE);
                holder.countTextView.setText(String.valueOf(1));
                holder.cartAddLayout.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return homeResponseArrayList.size();
    }

    public void addCartProductOffline(final OfflineCartProduct offlineCartProduct) {
        class AddCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {
                DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .offlineCartDao()
                        .insert(offlineCartProduct);

                List<OfflineCartProduct> offlineCartProduct_temp = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAllProducts();

                return offlineCartProduct_temp;
            }

            @Override
            protected void onPostExecute(List<OfflineCartProduct> offlineCartProductList) {
                super.onPostExecute(offlineCartProductList);
                Toast.makeText(mContext, "Added to Cart", Toast.LENGTH_SHORT).show();
                ((CategoryProductsActivity) mContext).updateCartCount(offlineCartProductList.size());
            }
        }
        new AddCartProductOffline().execute();
    }

    public void updateCartProductOfflineUsingSkuID(final String skuIDTemp, final int count, final String type) {
        class AddCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {

                OfflineCartProduct offlineCartProduct = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getProductUsingSkuID(skuIDTemp);
                if (type.equalsIgnoreCase("update")) {

                    offlineCartProduct.setQty(count);
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
                }

                List<OfflineCartProduct> offlineCartProduct_temp = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAllProducts();

                return offlineCartProduct_temp;
            }

            @Override
            protected void onPostExecute(List<OfflineCartProduct> offlineCartProductList) {
                super.onPostExecute(offlineCartProductList);
                ((CategoryProductsActivity) mContext).updateCartCount(offlineCartProductList.size());
            }
        }
        new AddCartProductOffline().execute();
    }

}
