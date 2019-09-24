package com.grocers.hub.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.grocers.hub.models.ProductsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;
    Shared shared;
    ArrayList<ProductsResponse> productsResponseArrayList;
    OnProductClickListener onProductClickListener;
    ArrayList<String> productOptions = new ArrayList<String>();

    public ProductsAdapter(Context mContext, ArrayList<ProductsResponse> productsResponseArrayList/*, ArrayList<String> productIsAddedCartArrayList*/) {
        this.mContext = mContext;
        this.shared = new Shared(mContext);
        this.productsResponseArrayList = productsResponseArrayList;
    }

    public void setClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout, cartCountLayout;
        RelativeLayout optionsLayout, cartAddLayout;
        TextView productNameTextView, offerCostTextView, costTextView, addTextView, minusTextView, countTextView, plusTextView;
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
            cartAddLayout = (RelativeLayout) view.findViewById(R.id.cartAddLayout);
            optionsLayout = (RelativeLayout) view.findViewById(R.id.optionsLayout);
            minusTextView = (TextView) view.findViewById(R.id.minusTextView);
            countTextView = (TextView) view.findViewById(R.id.countTextView);
            plusTextView = (TextView) view.findViewById(R.id.plusTextView);
            cartCountLayout = (LinearLayout) view.findViewById(R.id.cartCountLayout);
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
        holder.costTextView.setText("₹ " + String.valueOf(productsResponseArrayList.get(position).getPrice()));
        if (productsResponseArrayList.get(position).getFinalPrice() == productsResponseArrayList.get(position).getPrice()) {
            holder.costTextView.setVisibility(View.GONE);
        } else {
            holder.costTextView.setVisibility(View.VISIBLE);
        }

        if (productsResponseArrayList.get(position).getCartQuantity() > 0) {
            holder.cartCountLayout.setVisibility(View.VISIBLE);
            holder.cartAddLayout.setVisibility(View.GONE);
            holder.countTextView.setText(String.valueOf(productsResponseArrayList.get(position).getCartQuantity()));
        } else {
            holder.cartCountLayout.setVisibility(View.GONE);
            holder.cartAddLayout.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(productsResponseArrayList.get(position).getImage()).into(holder.productImageView);

        /*holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = 1;
                holder.countTextView.setText(String.valueOf(count));

                String sku_temp = "";
                if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = productsResponseArrayList.get(position).getSku();
                    productsResponseArrayList.get(position).setCartQuantity(count);
                }

            }
        });*/


        holder.plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count++;
                holder.countTextView.setText(String.valueOf(count));

                String sku_temp = "";
                if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = productsResponseArrayList.get(position).getSku();
                    productsResponseArrayList.get(position).setCartQuantity(count);
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
                if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = productsResponseArrayList.get(position).getSku();
                    productsResponseArrayList.get(position).setCartQuantity(count);
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

        productOptions = new ArrayList<String>();
        if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
            holder.offerCostTextView.setText("₹ " + String.valueOf(productsResponseArrayList.get(position).getOptions().get(0).getFinalPrice()));
            double priceDouble = Double.parseDouble(productsResponseArrayList.get(position).getOptions().get(0).getPrice());
            int priceInt = (int) priceDouble;

            if (priceInt == productsResponseArrayList.get(position).getOptions().get(0).getFinalPrice()) {
                holder.costTextView.setVisibility(View.GONE);
            }

            if (productsResponseArrayList.get(position).getOptions().get(0).getCartQuantity() > 0) {
                holder.cartCountLayout.setVisibility(View.VISIBLE);
                holder.cartAddLayout.setVisibility(View.GONE);
                holder.countTextView.setText(String.valueOf(productsResponseArrayList.get(position).getOptions().get(0).getCartQuantity()));
            } else {
                holder.cartCountLayout.setVisibility(View.GONE);
                holder.cartAddLayout.setVisibility(View.VISIBLE);
            }

            holder.costTextView.setText("₹ " + String.valueOf(priceInt));
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
                    holder.offerCostTextView.setText("₹ " + String.valueOf(productsResponseArrayList.get(position).getOptions().get(i).getFinalPrice()));
                    double priceDouble = Double.parseDouble(productsResponseArrayList.get(position).getOptions().get(i).getPrice());
                    int priceInt = (int) priceDouble;
                    holder.costTextView.setText("₹ " + String.valueOf(priceInt));
                    if (priceInt == productsResponseArrayList.get(position).getOptions().get(i).getFinalPrice()) {
                        holder.costTextView.setVisibility(View.GONE);
                    } else {
                        holder.costTextView.setVisibility(View.VISIBLE);
                    }


                    if (productsResponseArrayList.get(position).getOptions().get(i).getCartQuantity() > 0) {
                        holder.cartCountLayout.setVisibility(View.VISIBLE);
                        holder.cartAddLayout.setVisibility(View.GONE);
                        holder.countTextView.setText(String.valueOf(productsResponseArrayList.get(position).getOptions().get(i).getCartQuantity()));
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
                /*if (shared.getUserName().length() > 0) {
                    if (holder.addTextView.getText().toString().equalsIgnoreCase("ADD")) {
                        if (onProductClickListener != null) {
                            onProductClickListener.onProductClick(position, holder.optionsSpinner.getSelectedItemPosition());
                        }
                    } else {
                        Toast.makeText(mContext, "Product is already in Cart", Toast.LENGTH_SHORT).show();
                    }
                } else {*/
                OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                if (productsResponseArrayList.get(position).getOptions() != null && productsResponseArrayList.get(position).getOptions().size() > 0) {
                    offlineCartProduct.setProduct_id(Integer.parseInt(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getProduct_id()));
                    offlineCartProduct.setSkuID(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku());
                    offlineCartProduct.setValue_index(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getValue_index());
                    offlineCartProduct.setPrice(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getPrice());
                    offlineCartProduct.setFinalPrice(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getFinalPrice());
                    offlineCartProduct.setDefault_title(productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getDefault_title());
                    productsResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(1);

                } else {
                    offlineCartProduct.setSkuID(productsResponseArrayList.get(position).getSku());
                    offlineCartProduct.setPrice(String.valueOf(productsResponseArrayList.get(position).getPrice()));
                    offlineCartProduct.setFinalPrice(productsResponseArrayList.get(position).getFinalPrice());

                    productsResponseArrayList.get(position).setCartQuantity(1);

                }
                offlineCartProduct.setQty(1);
                offlineCartProduct.setParentSkuID(productsResponseArrayList.get(position).getSku());
                offlineCartProduct.setName(productsResponseArrayList.get(position).getName());
                offlineCartProduct.setProduct_type(productsResponseArrayList.get(position).getProduct_type());
                offlineCartProduct.setImage(productsResponseArrayList.get(position).getImage());

                addCartProductOffline(offlineCartProduct);

                holder.cartCountLayout.setVisibility(View.VISIBLE);
                holder.countTextView.setText(String.valueOf(1));
                holder.cartAddLayout.setVisibility(View.GONE);

                // Toast.makeText(mContext, "Please login to add", Toast.LENGTH_SHORT).show();
                //  }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsResponseArrayList.size();
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
