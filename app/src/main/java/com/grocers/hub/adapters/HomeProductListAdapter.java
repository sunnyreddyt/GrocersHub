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

import com.grocers.hub.ProductDetailActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.fragments.HomeFragment;
import com.grocers.hub.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeProductListAdapter extends RecyclerView.Adapter<HomeProductListAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<HomeResponse> homeResponseArrayList;
    Shared shared;
    int parentPosition;
    ArrayList<String> productOptions = new ArrayList<String>();
    HomeFragment fragment;

    public HomeProductListAdapter(Context mContext,
                                  ArrayList<HomeResponse> homeResponseArrayList, int parentPosition, HomeFragment fragment) {
        this.mContext = mContext;
        this.homeResponseArrayList = homeResponseArrayList;
        this.shared = new Shared(mContext);
        this.parentPosition = parentPosition;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout, cartCountLayout;
        Spinner optionsSpinner;
        ImageView productImageView;
        RelativeLayout optionsLayout, cartAddLayout, discountLayout;
        TextView productNameTextView, discountTextView, offerCostTextView, addTextView, costTextView, minusTextView, countTextView, plusTextView;

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
            discountLayout = (RelativeLayout) view.findViewById(R.id.discountLayout);
            addTextView = (TextView) view.findViewById(R.id.addTextView);
            cartAddLayout = (RelativeLayout) view.findViewById(R.id.cartAddLayout);
            minusTextView = (TextView) view.findViewById(R.id.minusTextView);
            discountTextView = (TextView) view.findViewById(R.id.discountTextView);
            countTextView = (TextView) view.findViewById(R.id.countTextView);
            plusTextView = (TextView) view.findViewById(R.id.plusTextView);
            cartCountLayout = (LinearLayout) view.findViewById(R.id.cartCountLayout);
        }
    }

    @Override
    public HomeProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_products_list_item, parent, false);

        return new HomeProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeProductListAdapter.MyViewHolder holder, final int position) {

        Picasso.get().load(homeResponseArrayList.get(position).getImage()).into(holder.productImageView);
        int finalPriceInteger = (int) homeResponseArrayList.get(position).getFinalPrice();
        holder.offerCostTextView.setText("₹ " + String.valueOf(finalPriceInteger));
        holder.productNameTextView.setText(String.valueOf(homeResponseArrayList.get(position).getName()));

        int originalPriceInteger = (int) homeResponseArrayList.get(position).getPrice();
        holder.costTextView.setText("₹ " + String.valueOf(originalPriceInteger));
        if (homeResponseArrayList.get(position).getPrice() == homeResponseArrayList.get(position).getFinalPrice()) {
            holder.costTextView.setVisibility(View.GONE);
            holder.discountLayout.setVisibility(View.GONE);
        } else {
            holder.costTextView.setVisibility(View.VISIBLE);

            double originalPrice = homeResponseArrayList.get(position).getPrice();
            int originalPriceInt = (int) originalPrice;

            if (originalPriceInt > 0) {
                double finalPrice = homeResponseArrayList.get(position).getFinalPrice();
                int finalPriceInt = (int) finalPrice;
                int decreaseAmount = originalPriceInt - finalPriceInt;
                double divisionValue = (double) decreaseAmount / originalPrice;
                double discount = divisionValue * 100.0;
                if ((int)discount > 0) {
                    holder.discountLayout.setVisibility(View.VISIBLE);
                    holder.discountTextView.setText(String.valueOf((int)discount) + "% off");
                }
            }

        }

        /*for (int g = 0; g < HomeAdapter.cartResponseArrayList.size(); g++) {
            if (HomeAdapter.cartResponseArrayList.get(g).getSku().equalsIgnoreCase(homeResponseArrayList.get(position).getSku())) {
                holder.addTextView.setText("ADDED");
            }
        }*/

        if (homeResponseArrayList.get(position).getOptions() == null || homeResponseArrayList.get(position).getOptions().size() == 0) {
            if (homeResponseArrayList.get(position).getCartQuantity() > 0) {
                holder.cartCountLayout.setVisibility(View.VISIBLE);
                holder.cartAddLayout.setVisibility(View.GONE);
                holder.countTextView.setText(String.valueOf(homeResponseArrayList.get(position).getCartQuantity()));
            } else {
                holder.cartCountLayout.setVisibility(View.GONE);
                holder.cartAddLayout.setVisibility(View.VISIBLE);
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

            int optionalFinalPriceInteger = (int) homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice();
            holder.offerCostTextView.setText("₹ " + String.valueOf(optionalFinalPriceInteger));

            double priceDouble = Double.parseDouble(homeResponseArrayList.get(position).getOptions().get(0).getPrice());
            int priceInt = (int) priceDouble;
            if (priceInt == homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice()) {
                holder.costTextView.setVisibility(View.GONE);
                holder.discountLayout.setVisibility(View.GONE);
            } else {
                holder.costTextView.setVisibility(View.VISIBLE);

                int originalPrice = priceInt;
                if (originalPrice > 0) {
                    double finalPrice = homeResponseArrayList.get(position).getOptions().get(0).getFinalPrice();
                    int finalPriceInt = (int) finalPrice;
                    int decreaseAmount = originalPrice - finalPriceInt;
                    double divisionValue = (double) decreaseAmount / originalPrice;
                    double discount = divisionValue * 100.0;
                    if ((int)discount > 0) {
                        holder.discountLayout.setVisibility(View.VISIBLE);
                        holder.discountTextView.setText(String.valueOf((int) discount) + "% off");
                    } else {
                        holder.discountLayout.setVisibility(View.GONE);
                    }
                }
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
                    int finalPriceInteger = (int) homeResponseArrayList.get(position).getOptions().get(i).getFinalPrice();
                    holder.offerCostTextView.setText("₹ " + String.valueOf(finalPriceInteger));
                    double priceDouble = Double.parseDouble(homeResponseArrayList.get(position).getOptions().get(i).getPrice());
                    int priceInt = (int) priceDouble;
                    holder.costTextView.setText("₹ " + String.valueOf(priceInt));
                    if (priceInt == homeResponseArrayList.get(position).getOptions().get(i).getFinalPrice()) {
                        holder.costTextView.setVisibility(View.GONE);
                        holder.discountLayout.setVisibility(View.GONE);
                    } else {
                        holder.costTextView.setVisibility(View.VISIBLE);
                        int originalPrice = priceInt;
                        if (originalPrice > 0) {
                            double finalPrice = homeResponseArrayList.get(position).getOptions().get(i).getFinalPrice();
                            int finalPriceInt = (int) finalPrice;
                            int decreaseAmount = originalPrice - finalPriceInt;
                            double divisionValue = (double) decreaseAmount / originalPrice;
                            double discount = divisionValue * 100.0;
                            if ((int)discount > 0) {
                                holder.discountLayout.setVisibility(View.VISIBLE);
                                holder.discountTextView.setText(String.valueOf((int)discount) + "% off");
                            }
                        }
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
                /*int availableQuantity = 0;
                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count++;

                String sku_temp = "";
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    availableQuantity = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getQty();
                    if (availableQuantity >= count) {
                        homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                    }
                } else {
                    sku_temp = homeResponseArrayList.get(position).getSku();
                    availableQuantity = homeResponseArrayList.get(position).getQty();
                    if (availableQuantity >= count) {
                        homeResponseArrayList.get(position).setCartQuantity(count);
                    }
                }

                if (availableQuantity >= count) {
                    updateCartProductOfflineUsingSkuID(sku_temp, count, "update");
                    holder.countTextView.setText(String.valueOf(count));
                } else {
                    Toast.makeText(mContext, "Only "+String.valueOf(count-1)+" are available in stock", Toast.LENGTH_SHORT).show();
                }*/

                String sku_temp = "";
                int availableQuantity = 0;
                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count++;
                OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    offlineCartProduct.setProduct_id(Integer.parseInt(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getProduct_id()));
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku());
                    offlineCartProduct.setValue_index(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getValue_index());
                    offlineCartProduct.setPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getPrice());
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getFinalPrice());
                    offlineCartProduct.setDefault_title(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getDefault_title());
                    availableQuantity = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getQty();
                    if (availableQuantity >= count) {
                        homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                    }
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                } else {
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getSku());
                    offlineCartProduct.setPrice(String.valueOf(homeResponseArrayList.get(position).getPrice()));
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getFinalPrice());
                    availableQuantity = homeResponseArrayList.get(position).getQty();
                    if (availableQuantity >= count) {
                        homeResponseArrayList.get(position).setCartQuantity(count);
                    }
                    sku_temp = homeResponseArrayList.get(position).getSku();
                }
                offlineCartProduct.setQty(count);
                offlineCartProduct.setParentSkuID(homeResponseArrayList.get(position).getSku());
                offlineCartProduct.setName(homeResponseArrayList.get(position).getName());
                offlineCartProduct.setProduct_type(homeResponseArrayList.get(position).getProduct_type());
                offlineCartProduct.setImage(homeResponseArrayList.get(position).getImage());

                if (availableQuantity >= count) {
                    updateCartProductOfflineUsingSkuID(offlineCartProduct, sku_temp, count, "update");
                    holder.countTextView.setText(String.valueOf(count));
                } else {
                    Toast.makeText(mContext, "Only " + String.valueOf(count - 1) + " are available in stock", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*int count = Integer.parseInt(holder.countTextView.getText().toString());
                count--;
                String sku_temp = "";
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                    homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                } else {
                    sku_temp = homeResponseArrayList.get(position).getSku();
                    homeResponseArrayList.get(position).setCartQuantity(count);
                }

                if (count > 0 *//*&& (availableQuantity > count)*//*) {
                    holder.countTextView.setText(String.valueOf(count));
                    updateCartProductOfflineUsingSkuID(sku_temp, count, "update");
                } else if (count == 0) {
                    holder.cartCountLayout.setVisibility(View.GONE);
                    holder.cartAddLayout.setVisibility(View.VISIBLE);
                    updateCartProductOfflineUsingSkuID(sku_temp, count, "delete");
                }*/

                String sku_temp = "";
                int count = Integer.parseInt(holder.countTextView.getText().toString());
                count--;
                OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    offlineCartProduct.setProduct_id(Integer.parseInt(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getProduct_id()));
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku());
                    offlineCartProduct.setValue_index(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getValue_index());
                    offlineCartProduct.setPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getPrice());
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getFinalPrice());
                    offlineCartProduct.setDefault_title(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getDefault_title());
                    homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(count);
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                } else {
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getSku());
                    offlineCartProduct.setPrice(String.valueOf(homeResponseArrayList.get(position).getPrice()));
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getFinalPrice());
                    homeResponseArrayList.get(position).setCartQuantity(count);
                    sku_temp = homeResponseArrayList.get(position).getSku();
                }
                offlineCartProduct.setQty(count);
                offlineCartProduct.setParentSkuID(homeResponseArrayList.get(position).getSku());
                offlineCartProduct.setName(homeResponseArrayList.get(position).getName());
                offlineCartProduct.setProduct_type(homeResponseArrayList.get(position).getProduct_type());
                offlineCartProduct.setImage(homeResponseArrayList.get(position).getImage());

                if (count > 0) {
                    holder.countTextView.setText(String.valueOf(count));
                    updateCartProductOfflineUsingSkuID(offlineCartProduct, sku_temp, count, "update");
                } else if (count == 0) {
                    holder.cartCountLayout.setVisibility(View.GONE);
                    holder.cartAddLayout.setVisibility(View.VISIBLE);
                    updateCartProductOfflineUsingSkuID(offlineCartProduct, sku_temp, count, "delete");
                }
            }
        });


        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int availableQuantity = 0;
                String sku_temp = "";
                OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                if (homeResponseArrayList.get(position).getOptions() != null && homeResponseArrayList.get(position).getOptions().size() > 0) {
                    offlineCartProduct.setProduct_id(Integer.parseInt(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getProduct_id()));
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku());
                    offlineCartProduct.setValue_index(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getValue_index());
                    offlineCartProduct.setPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getPrice());
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getFinalPrice());
                    offlineCartProduct.setDefault_title(homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getDefault_title());
                    availableQuantity = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getQty();
                    if (availableQuantity > 0) {
                        homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).setCartQuantity(1);
                    }
                    sku_temp = homeResponseArrayList.get(position).getOptions().get(holder.optionsSpinner.getSelectedItemPosition()).getSku();
                } else {
                    offlineCartProduct.setSkuID(homeResponseArrayList.get(position).getSku());
                    offlineCartProduct.setPrice(String.valueOf(homeResponseArrayList.get(position).getPrice()));
                    offlineCartProduct.setFinalPrice(homeResponseArrayList.get(position).getFinalPrice());
                    availableQuantity = homeResponseArrayList.get(position).getQty();
                    if (availableQuantity > 0) {
                        homeResponseArrayList.get(position).setCartQuantity(1);
                    }
                    sku_temp = homeResponseArrayList.get(position).getSku();
                }
                offlineCartProduct.setQty(1);
                offlineCartProduct.setParentSkuID(homeResponseArrayList.get(position).getSku());
                offlineCartProduct.setName(homeResponseArrayList.get(position).getName());
                offlineCartProduct.setProduct_type(homeResponseArrayList.get(position).getProduct_type());
                offlineCartProduct.setImage(homeResponseArrayList.get(position).getImage());

                if (availableQuantity > 0) {
                    //addCartProductOffline(offlineCartProduct);
                    updateCartProductOfflineUsingSkuID(offlineCartProduct, sku_temp, 1, "insert");
                    holder.cartCountLayout.setVisibility(View.VISIBLE);
                    holder.countTextView.setText(String.valueOf(1));
                    holder.cartAddLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(mContext, "Product is not available in stock to buy", Toast.LENGTH_SHORT).show();
                }
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
                fragment.updateCartCount(offlineCartProductList.size());
            }
        }
        new AddCartProductOffline().execute();
    }

    public void updateCartProductOfflineUsingSkuID(final OfflineCartProduct insertOfflineCartProduct, final String skuIDTemp, final int count, final String type) {
        class AddCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {

                OfflineCartProduct offlineCartProduct = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getProductUsingSkuID(skuIDTemp);
                if (offlineCartProduct != null) {
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
                } else {
                    if (type.equalsIgnoreCase("update") || type.equalsIgnoreCase("insert")) {
                        DatabaseClient
                                .getInstance(mContext)
                                .getAppDatabase()
                                .offlineCartDao()
                                .insert(insertOfflineCartProduct);
                    }
                   /* else{
                        DatabaseClient
                                .getInstance(mContext)
                                .getAppDatabase()
                                .offlineCartDao()
                                .delete(offlineCartProduct);
                    }*/
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
                fragment.updateCartCount(offlineCartProductList.size());
            }
        }
        new AddCartProductOffline().execute();
    }

}

