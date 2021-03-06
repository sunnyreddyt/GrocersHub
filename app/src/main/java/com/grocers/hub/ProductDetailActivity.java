package com.grocers.hub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OnCouponClick;
import com.grocers.hub.adapters.ProductImagesListAdapter;
import com.grocers.hub.adapters.ProductsUnitsAdapter;
import com.grocers.hub.adapters.SimilarProductsAdapter;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.AddToCartOptionRequest;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.ProductDetailsResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.models.SimilarProductsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements ItemClickListener, OnCouponClick {

    RecyclerView productImagesRecyclerView, productUnitsRecyclerView, similarProductsRecyclerView;
    ProductImagesListAdapter productImagesListAdapter;
    ProductsUnitsAdapter productsUnitsAdapter;
    RelativeLayout cartLayout, discountLayout;
    LinearLayout productUnitsLayout, similarProductsLayout;
    ImageView backImageView, productImageView;
    String skuID = "", childSkuID = "";
    TextView skuIDTextView, productNameTextView, discountTextView, productOriginalPriceTextView, stockQuantityTextView, productPriceTextView, cartTextView, cartCountTextView, noSimilarProductsTextView;
    ProductDetailsResponse productDetailsResponse;
    Context context;
    Shared shared;
    String quoteID = "";
    GHUtil ghUtil;
    int activityCount = 0, productOptionValue = 0, productQuantityAvailability = 0;
    public static int selectedUnitPosition = 0;
    List<OfflineCartProduct> offlineCartProductArrayList;
    String productImageUrl = "";
    int maxQuantityAllowed = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ghUtil = GHUtil.getInstance(ProductDetailActivity.this);
        shared = new Shared(ProductDetailActivity.this);
        cartCountTextView = (TextView) findViewById(R.id.cartCountTextView);
        discountLayout = (RelativeLayout) findViewById(R.id.discountLayout);
        similarProductsLayout = (LinearLayout) findViewById(R.id.similarProductsLayout);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        skuIDTextView = (TextView) findViewById(R.id.skuIDTextView);
        productOriginalPriceTextView = (TextView) findViewById(R.id.productOriginalPriceTextView);
        noSimilarProductsTextView = (TextView) findViewById(R.id.noSimilarProductsTextView);
        productImagesRecyclerView = (RecyclerView) findViewById(R.id.productImagesRecyclerView);
        productUnitsRecyclerView = (RecyclerView) findViewById(R.id.productUnitsRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);
        similarProductsRecyclerView = (RecyclerView) findViewById(R.id.similarProductsRecyclerView);
        context = ProductDetailActivity.this;
        productPriceTextView = (TextView) findViewById(R.id.productPriceTextView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);
        cartTextView = (TextView) findViewById(R.id.cartTextView);
        productNameTextView = (TextView) findViewById(R.id.productNameTextView);
        stockQuantityTextView = (TextView) findViewById(R.id.stockQuantityTextView);
        productImageView = (ImageView) findViewById(R.id.productImageView);
        productUnitsLayout = (LinearLayout) findViewById(R.id.productUnitsLayout);
        Intent intent = getIntent();
        skuID = intent.getStringExtra("skuID");
        childSkuID = skuID;
        Log.v("skuID", skuID);
        getProductDetailServiceCall();

        if (skuID != null) {
            skuIDTextView.setText("Sku: " + skuID);
        }

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //if (shared.getUserID().length() > 0) {
                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                        /*} else {
                            Toast.makeText(ProductDetailActivity.this, "Please login to view cart", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (shared.getUserEmail().length() > 0) {
                if (productQuantityAvailability > 0) {
                    if (cartTextView.getText().equals("Proceed to Cart")) {
                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        /*if (shared.getUserID().length() > 0) {
                            getQuoteIDServiceCall();
                        } else {*/
                        if (maxQuantityAllowed > 0) {
                            OfflineCartProduct offlineCartProduct = new OfflineCartProduct();
                            if (productDetailsResponse.getOptions() != null && productDetailsResponse.getOptions().size() > 0) {
                                offlineCartProduct.setProduct_id(Integer.parseInt(productDetailsResponse.getOptions().get(selectedUnitPosition).getProduct_id()));
                                offlineCartProduct.setSkuID(productDetailsResponse.getOptions().get(selectedUnitPosition).getSku());
                                offlineCartProduct.setValue_index(productDetailsResponse.getOptions().get(selectedUnitPosition).getValue_index());
                                offlineCartProduct.setPrice(productDetailsResponse.getOptions().get(selectedUnitPosition).getPrice());
                                offlineCartProduct.setFinalPrice(productDetailsResponse.getOptions().get(selectedUnitPosition).getFinalPrice());
                                offlineCartProduct.setDefault_title(productDetailsResponse.getOptions().get(selectedUnitPosition).getDefault_title());

                            } else {
                                offlineCartProduct.setSkuID(productDetailsResponse.getData().getSku());
                                offlineCartProduct.setPrice(productDetailsResponse.getData().getPrice());
                                offlineCartProduct.setFinalPrice(productDetailsResponse.getData().getFinal_price());
                            }
                            offlineCartProduct.setQty(1);
                            offlineCartProduct.setMaxQtyAllowed(productDetailsResponse.getMaxQtyAllowed());
                            offlineCartProduct.setName(productDetailsResponse.getData().getName());
                            offlineCartProduct.setProduct_type(productDetailsResponse.getData().getType_id());
                            offlineCartProduct.setImage(productImageUrl);

                            addCartProductOffline(offlineCartProduct);
                        } else {
                            new AlertDialog.Builder(context).setTitle("Alert").setMessage("Max Allowed quantity per order is:" + String.valueOf(maxQuantityAllowed))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        // }
                    }
                } else {
                    Toast.makeText(context, "Product is not available in stock", Toast.LENGTH_SHORT).show();
                }
                /*} else {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }*/
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityCount > 0) {
            ghUtil.dismissDialog();
            getCartProductsOffline();
        } else {
            activityCount++;
        }
    }

    public void getProductDetailServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<ProductDetailsResponse> loginResponseCall = service.getProductDetails(skuID);
        loginResponseCall.enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                ghUtil.dismissDialog();
                productDetailsResponse = new ProductDetailsResponse();
                if (response.code() == 200) {
                    productDetailsResponse = response.body();
                    if (productDetailsResponse != null) {
                        productNameTextView.setText(productDetailsResponse.getData().getName());
                        productQuantityAvailability = productDetailsResponse.getData().getQuantity_and_stock_status().getQty();
                        productUpdate();

                        if (productDetailsResponse.getMedia_gallery_entries().size() > 0) {
                            Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + productDetailsResponse.getMedia_gallery_entries().get(0).getFile()).into(productImageView);
                            productImageUrl = Constants.PRODUCT_IMAGE__BASE_URL + productDetailsResponse.getMedia_gallery_entries().get(0).getFile();
                        }
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        productImagesRecyclerView.setLayoutManager(mLayoutManager);
                        productImagesListAdapter = new ProductImagesListAdapter(ProductDetailActivity.this, productDetailsResponse.getMedia_gallery_entries());
                        productImagesRecyclerView.setAdapter(productImagesListAdapter);
                        productImagesListAdapter.setClickListener(ProductDetailActivity.this);

                        if (productDetailsResponse.getOptions() != null && productDetailsResponse.getOptions().size() > 0) {
                            maxQuantityAllowed = productDetailsResponse.getOptions().get(0).getMaxQtyAllowed();
                            productUnitsLayout.setVisibility(View.VISIBLE);
                            selectedUnitPosition = 0;
                            productPriceTextView.setText("₹ " + String.valueOf(productDetailsResponse.getOptions().get(0).getFinalPrice()));
                            double priceDouble = Double.parseDouble(productDetailsResponse.getOptions().get(0).getPrice());
                            int priceInt = (int) priceDouble;
                            int finalPriceInt = (int) productDetailsResponse.getOptions().get(0).getFinalPrice();
                            if (priceInt == finalPriceInt) {
                                productOriginalPriceTextView.setVisibility(View.GONE);
                                discountLayout.setVisibility(View.GONE);
                            } else {
                                productOriginalPriceTextView.setVisibility(View.VISIBLE);
                                productOriginalPriceTextView.setText("₹ " + String.valueOf(priceInt) + " ");

                                int originalPrice = priceInt;
                                if (originalPrice > 0) {
                                    double finalPrice = productDetailsResponse.getOptions().get(0).getFinalPrice();
                                    int finalPriceIntTwo = (int) finalPrice;
                                    int decreaseAmount = originalPrice - finalPriceIntTwo;
                                    double divisionValue = (double) decreaseAmount / originalPrice;
                                    double discount = divisionValue * 100.0;
                                    //int discount = (decreaseAmount / originalPrice) * 100;
                                    if ((int) discount > 0) {
                                        discountLayout.setVisibility(View.VISIBLE);
                                        discountTextView.setText(String.valueOf((int) discount) + "% off");
                                    } else {
                                        discountLayout.setVisibility(View.GONE);
                                    }
                                }
                            }

                            productQuantityAvailability = productDetailsResponse.getOptions().get(0).getQty();
                            productUpdate();
                            productOptionValue = Integer.parseInt(productDetailsResponse.getOptions().get(0).getValue_index());
                            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            productUnitsRecyclerView.setLayoutManager(mLayoutManager1);
                            productsUnitsAdapter = new ProductsUnitsAdapter(ProductDetailActivity.this, productDetailsResponse.getOptions());
                            productUnitsRecyclerView.setAdapter(productsUnitsAdapter);
                            productsUnitsAdapter.setClickListener(ProductDetailActivity.this);

                            childSkuID = productDetailsResponse.getOptions().get(0).getSku();

                        } else {
                            maxQuantityAllowed = productDetailsResponse.getMaxQtyAllowed();
                            productPriceTextView.setText("₹ " + String.valueOf(productDetailsResponse.getData().getFinal_price()));
                            double priceDouble = Double.parseDouble(productDetailsResponse.getData().getPrice());
                            int priceInt = (int) priceDouble;
                            if (priceInt == productDetailsResponse.getData().getFinal_price()) {
                                productOriginalPriceTextView.setVisibility(View.GONE);
                                discountLayout.setVisibility(View.GONE);
                            } else {
                                productOriginalPriceTextView.setVisibility(View.VISIBLE);
                                productOriginalPriceTextView.setText("₹ " + String.valueOf(priceInt) + " ");
                                int originalPrice = priceInt;
                                if (originalPrice > 0) {
                                    double finalPrice = productDetailsResponse.getData().getFinal_price();
                                    int finalPriceIntTwo = (int) finalPrice;
                                    int decreaseAmount = originalPrice - finalPriceIntTwo;
                                    double divisionValue = (double) decreaseAmount / originalPrice;
                                    double discount = divisionValue * 100.0;
                                    //int discount = (decreaseAmount / originalPrice) * 100;
                                    if ((int) discount > 0) {
                                        discountLayout.setVisibility(View.VISIBLE);
                                        discountTextView.setText(String.valueOf((int) discount) + "% off");
                                    } else {
                                        discountLayout.setVisibility(View.GONE);
                                    }
                                }
                            }

                            productQuantityAvailability = productDetailsResponse.getQty();
                            productUpdate();

                            productUnitsLayout.setVisibility(View.GONE);
                        }
                    }
                    getSimilarProductsServiceCall();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(int position) {
        Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + productDetailsResponse.getMedia_gallery_entries().get(position).getFile()).into(productImageView);
    }

    public void getSimilarProductsServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<SimilarProductsResponse> loginResponseCall = service.getSimilarProducts(skuID);
        loginResponseCall.enqueue(new Callback<SimilarProductsResponse>() {
            @Override
            public void onResponse(Call<SimilarProductsResponse> call, Response<SimilarProductsResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getData().size() > 0) {
                            similarProductsLayout.setVisibility(View.VISIBLE);
                            similarProductsRecyclerView.setVisibility(View.VISIBLE);
                            noSimilarProductsTextView.setVisibility(View.GONE);

                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            similarProductsRecyclerView.setLayoutManager(mLayoutManager);
                            SimilarProductsAdapter similarProductsAdapter = new SimilarProductsAdapter(context, response.body().getData());
                            similarProductsRecyclerView.setAdapter(similarProductsAdapter);
                        } else {
                            similarProductsLayout.setVisibility(View.GONE);
                            similarProductsRecyclerView.setVisibility(View.GONE);
                            noSimilarProductsTextView.setVisibility(View.VISIBLE);
                        }
                    } else if (response.body().getStatus() == 400) {
                        similarProductsLayout.setVisibility(View.GONE);
                        similarProductsRecyclerView.setVisibility(View.GONE);
                        noSimilarProductsTextView.setVisibility(View.VISIBLE);
                        //Toast.makeText(context, "No similar products available", Toast.LENGTH_SHORT);
                    }
                } else {
                    similarProductsLayout.setVisibility(View.GONE);
                    similarProductsRecyclerView.setVisibility(View.GONE);
                    noSimilarProductsTextView.setVisibility(View.VISIBLE);
                    // Toast.makeText(context, "No similar products available", Toast.LENGTH_LONG).show();
                }

                getCartProductsOffline();

            }

            @Override
            public void onFailure(Call<SimilarProductsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                similarProductsLayout.setVisibility(View.GONE);
                similarProductsRecyclerView.setVisibility(View.GONE);
                noSimilarProductsTextView.setVisibility(View.VISIBLE);
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
                getCartProductsOffline();
            }
        });
    }

    public void getQuoteIDServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<QuoteIDResponse> loginResponseCall = service.getQuoteID(shared.getToken(), shared.getUserID());
        loginResponseCall.enqueue(new Callback<QuoteIDResponse>() {
            @Override
            public void onResponse(Call<QuoteIDResponse> call, Response<QuoteIDResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getStatus() == 200) {
                            quoteID = String.valueOf(response.body().getQuote_id());
                            if (productDetailsResponse.getOptions() != null && productDetailsResponse.getOptions().size() > 0) {
                                addToCartOptionServiceCall();
                            } else {
                                addToCartServiceCall();
                            }
                        }
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteIDResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCartServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        AddToCartRequest cartItem = new AddToCartRequest();
        cartItem.setQty(1);
        cartItem.setQuote_id(quoteID);
        cartItem.setSku(skuID);

        addToCartRequest.setCartItem(cartItem);
        Call<AddToCartResponse> loginResponseCall = service.addToCart("Bearer " + shared.getToken(), addToCartRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    cartTextView.setText("Proceed to Cart");
                    getCartProductsOffline();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCartOptionServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        AddToCartOptionRequest addToCartOptionRequest = new AddToCartOptionRequest();
        AddToCartOptionRequest.CartItem cartItem = new AddToCartOptionRequest.CartItem();
        cartItem.setQty(1);
        cartItem.setQuote_id(Integer.parseInt(quoteID));
        cartItem.setSku(skuID);

        AddToCartOptionRequest.ProductOption product_option = new AddToCartOptionRequest.ProductOption();

        AddToCartOptionRequest.ExtensionAttributes extension_attributes = new AddToCartOptionRequest.ExtensionAttributes();

        ArrayList<AddToCartOptionRequest.ConfigurableItemOptions> configurable_item_options = new ArrayList<AddToCartOptionRequest.ConfigurableItemOptions>();
        AddToCartOptionRequest.ConfigurableItemOptions obj = new AddToCartOptionRequest.ConfigurableItemOptions();
        obj.setOption_id("140");
        obj.setOption_value(productOptionValue);
        configurable_item_options.add(obj);

        extension_attributes.setConfigurable_item_options(configurable_item_options);

        product_option.setExtension_attributes(extension_attributes);

        cartItem.setProduct_option(product_option);
        cartItem.setProduct_type("configurable");

        addToCartOptionRequest.setCartItem(cartItem);

        Gson gson = new Gson();
        Log.v("addToCartOptionRequest", gson.toJson(addToCartOptionRequest));
        Call<AddToCartResponse> loginResponseCall = service.addToCartOption("Bearer " + shared.getToken(), addToCartOptionRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    cartTextView.setText("Proceed to Cart");
                    getCartProductsOffline();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public void getCartProductsServiceCall() {
        // ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                // ghUtil.dismissDialog();
                if (response.code() == 200) {

                    int cartCount = response.body().getItems().size();
                    if (cartCount > 0) {
                        cartCountTextView.setText(String.valueOf(cartCount));
                    } else {
                        cartCountTextView.setText("0");
                    }

                    for (int o = 0; o < response.body().getItems().size(); o++) {
                        if (response.body().getItems().get(o).getSku().equalsIgnoreCase(skuID)) {
                            cartTextView.setText("Proceed to Cart");
                        }
                    }

                } else {
                    cartCountTextView.setText("0");
                    // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                // ghUtil.dismissDialog();
                //Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public void onCouponClick(int position) {
        selectedUnitPosition = position;
        productsUnitsAdapter.notifyDataSetChanged();
        productPriceTextView.setText("₹ " + String.valueOf(productDetailsResponse.getOptions().get(position).getFinalPrice()));
        maxQuantityAllowed = productDetailsResponse.getOptions().get(position).getMaxQtyAllowed();

        double priceDouble = Double.parseDouble(productDetailsResponse.getOptions().get(position).getPrice());
        int priceInt = (int) priceDouble;
        if (priceInt == productDetailsResponse.getOptions().get(position).getFinalPrice()) {
            productOriginalPriceTextView.setVisibility(View.GONE);
            discountLayout.setVisibility(View.GONE);
        } else {
            productOriginalPriceTextView.setVisibility(View.VISIBLE);
            productOriginalPriceTextView.setText("₹ " + String.valueOf(priceInt) + " ");

            int originalPrice = priceInt;
            if (originalPrice > 0) {
                double finalPrice = productDetailsResponse.getOptions().get(position).getFinalPrice();
                int finalPriceIntTwo = (int) finalPrice;
                int decreaseAmount = originalPrice - finalPriceIntTwo;
                double divisionValue = (double) decreaseAmount / originalPrice;
                double discount = divisionValue * 100.0;
                //int discount = (decreaseAmount / originalPrice) * 100;
                if ((int) discount > 0) {
                    discountLayout.setVisibility(View.VISIBLE);
                    discountTextView.setText(String.valueOf((int) discount) + "% off");
                } else {
                    discountLayout.setVisibility(View.GONE);
                }
            }
        }

        productQuantityAvailability = productDetailsResponse.getOptions().get(position).getQty();
        productUpdate();

        childSkuID = productDetailsResponse.getOptions().get(position).getSku();
        getCartProductsOffline();
    }

    public void productUpdate() {
        if (productQuantityAvailability == 0) {
            cartTextView.setAlpha((float) 0.5);
            stockQuantityTextView.setText("Not in stock");
            stockQuantityTextView.setTextColor(Color.parseColor("#ff0013"));
        } else {
            cartTextView.setAlpha(1);
            stockQuantityTextView.setText("(" + String.valueOf(productQuantityAvailability) + " qty available)");
            stockQuantityTextView.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void addCartProductOffline(final OfflineCartProduct offlineCartProduct) {
        class AddCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {
                DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .insert(offlineCartProduct);
                List<OfflineCartProduct> offlineCartProductList = new ArrayList<OfflineCartProduct>();
                OfflineCartProduct offlineCartProduct_temp = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAll();

                return offlineCartProductList;
            }

            @Override
            protected void onPostExecute(List<OfflineCartProduct> offlineCartProductList) {
                super.onPostExecute(offlineCartProductList);
                Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                cartTextView.setText("Proceed to Cart");
                cartCountTextView.setText(String.valueOf(offlineCartProductList.size()));
                getCartProductsOffline();
            }
        }
        new AddCartProductOffline().execute();
    }

    public void getCartProductsOffline() {
        class GetCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {

                offlineCartProductArrayList = new ArrayList<OfflineCartProduct>();
                offlineCartProductArrayList = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAllProducts();

                return offlineCartProductArrayList;
            }

            @Override
            protected void onPostExecute(List<OfflineCartProduct> offlineCartProductList) {
                super.onPostExecute(offlineCartProductList);

                int cartCount = offlineCartProductList.size();
                if (cartCount > 0) {
                    cartCountTextView.setText(String.valueOf(cartCount));
                } else {
                    cartCountTextView.setText("0");
                }

                boolean isAddedToCart = false;
                for (int o = 0; o < cartCount; o++) {
                    if (offlineCartProductList.get(o).getSkuID().equalsIgnoreCase(childSkuID)) {
                        isAddedToCart = true;
                    }
                }
                if (isAddedToCart) {
                    cartTextView.setText("Proceed to Cart");
                } else {
                    cartTextView.setText("Add to Cart");
                }

            }
        }
        new GetCartProductOffline().execute();
    }

    public void updateCartCount(int cartCount) {
        cartCountTextView.setText(String.valueOf(cartCount));
    }

}
