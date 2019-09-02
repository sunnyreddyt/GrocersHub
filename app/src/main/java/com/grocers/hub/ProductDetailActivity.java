package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.ProductImagesListAdapter;
import com.grocers.hub.adapters.ProductsAdapter;
import com.grocers.hub.adapters.ProductsUnitsAdapter;
import com.grocers.hub.adapters.SimilarProductsAdapter;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView productImagesRecyclerView, productUnitsRecyclerView, similarProductsRecyclerView;
    ProductImagesListAdapter productImagesListAdapter;
    ProductsUnitsAdapter productsUnitsAdapter;
    RelativeLayout cartLayout;
    ImageView backImageView, productImageView;
    String skuID;
    TextView productNameTextView, productPriceTextView, cartTextView, cartCountTextView, noSimilarProductsTextView;
    ProductDetailsResponse productDetailsResponse;
    Context context;
    Shared shared;
    String quoteID = "";
    GHUtil ghUtil;
    int activityCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ghUtil = GHUtil.getInstance(ProductDetailActivity.this);
        shared = new Shared(ProductDetailActivity.this);
        cartCountTextView = (TextView) findViewById(R.id.cartCountTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        noSimilarProductsTextView = (TextView) findViewById(R.id.noSimilarProductsTextView);
        productImagesRecyclerView = (RecyclerView) findViewById(R.id.productImagesRecyclerView);
        // productUnitsRecyclerView = (RecyclerView) findViewById(R.id.productUnitsRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);
        similarProductsRecyclerView = (RecyclerView) findViewById(R.id.similarProductsRecyclerView);
        context = ProductDetailActivity.this;
        productPriceTextView = (TextView) findViewById(R.id.productPriceTextView);
        cartTextView = (TextView) findViewById(R.id.cartTextView);
        productNameTextView = (TextView) findViewById(R.id.productNameTextView);
        productImageView = (ImageView) findViewById(R.id.productImageView);

        Intent intent = getIntent();
        skuID = intent.getStringExtra("skuID");
        Log.v("skuID", skuID);
        getProductDetailServiceCall();

  /*      LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        productUnitsRecyclerView.setLayoutManager(mLayoutManager1);
        productsUnitsAdapter = new ProductsUnitsAdapter(ProductDetailActivity.this, null);
        productUnitsRecyclerView.setAdapter(productsUnitsAdapter);*/

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (shared.getUserID().length() > 0) {
                            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Please login to view cart", Toast.LENGTH_SHORT).show();
                        }
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
                if (shared.getUserEmail().length() > 0) {
                    if (cartTextView.getText().equals("Proceed to Cart")) {
                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        getQuoteIDServiceCall();
                    }
                } else {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityCount > 0 && shared.getUserID().length() > 0) {
            getCartProductsServiceCall();
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
                        String name = productDetailsResponse.getName();
                        if (productDetailsResponse.getWeight() > 0) {
                            name = name + " - " + String.valueOf(productDetailsResponse.getWeight());
                        }
                        productNameTextView.setText(name);
                        productPriceTextView.setText(String.valueOf(productDetailsResponse.getPrice()));

                        if (productDetailsResponse.getMedia_gallery_entries().size() > 0) {
                            Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + productDetailsResponse.getMedia_gallery_entries().get(0).getFile()).into(productImageView);
                        }
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        productImagesRecyclerView.setLayoutManager(mLayoutManager);
                        productImagesListAdapter = new ProductImagesListAdapter(ProductDetailActivity.this, productDetailsResponse.getMedia_gallery_entries());
                        productImagesRecyclerView.setAdapter(productImagesListAdapter);
                        productImagesListAdapter.setClickListener(ProductDetailActivity.this);
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
                            similarProductsRecyclerView.setVisibility(View.VISIBLE);
                            noSimilarProductsTextView.setVisibility(View.GONE);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            similarProductsRecyclerView.setLayoutManager(mLayoutManager);
                            SimilarProductsAdapter similarProductsAdapter = new SimilarProductsAdapter(context, response.body().getData());
                            similarProductsRecyclerView.setAdapter(similarProductsAdapter);
                        } else {
                            similarProductsRecyclerView.setVisibility(View.GONE);
                            noSimilarProductsTextView.setVisibility(View.VISIBLE);
                        }
                    } else if (response.body().getStatus() == 400) {
                        similarProductsRecyclerView.setVisibility(View.GONE);
                        noSimilarProductsTextView.setVisibility(View.VISIBLE);
                        //Toast.makeText(context, "No similar products available", Toast.LENGTH_SHORT);
                    }
                } else {
                    similarProductsRecyclerView.setVisibility(View.GONE);
                    noSimilarProductsTextView.setVisibility(View.VISIBLE);
                    // Toast.makeText(context, "No similar products available", Toast.LENGTH_LONG).show();
                }

                if (shared.getUserID().length() > 0) {
                    getCartProductsServiceCall();
                }
            }

            @Override
            public void onFailure(Call<SimilarProductsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                similarProductsRecyclerView.setVisibility(View.GONE);
                noSimilarProductsTextView.setVisibility(View.VISIBLE);
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
                if (shared.getUserID().length() > 0) {
                    getCartProductsServiceCall();
                }
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
                            addToCartServiceCall();
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
                    getCartProductsServiceCall();
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

    public void getCartProductsServiceCall() {
        ghUtil.showDialog(ProductDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                ghUtil.dismissDialog();
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
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                ghUtil.dismissDialog();
                //Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
