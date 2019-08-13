package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.ProductImagesListAdapter;
import com.grocers.hub.adapters.ProductsAdapter;
import com.grocers.hub.adapters.ProductsUnitsAdapter;
import com.grocers.hub.adapters.SimilarProductsAdapter;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.ProductDetailsResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.models.SimilarProductsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
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
    TextView productNameTextView, productPriceTextView, cartTextView;
    ProductDetailsResponse productDetailsResponse;
    Context context;
    Shared shared;
    String quoteID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        shared = new Shared(ProductDetailActivity.this);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        productImagesRecyclerView = (RecyclerView) findViewById(R.id.productImagesRecyclerView);
        productUnitsRecyclerView = (RecyclerView) findViewById(R.id.productUnitsRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);
        similarProductsRecyclerView = (RecyclerView) findViewById(R.id.similarProductsRecyclerView);
        context = ProductDetailActivity.this;
        productPriceTextView = (TextView) findViewById(R.id.productPriceTextView);
        cartTextView = (TextView) findViewById(R.id.cartTextView);
        productNameTextView = (TextView) findViewById(R.id.productNameTextView);
        productImageView = (ImageView) findViewById(R.id.productImageView);

        Intent intent = getIntent();
        skuID = intent.getStringExtra("skuID");
        getProductDetailServiceCall();


        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        productUnitsRecyclerView.setLayoutManager(mLayoutManager1);
        productsUnitsAdapter = new ProductsUnitsAdapter(ProductDetailActivity.this, null);
        productUnitsRecyclerView.setAdapter(productsUnitsAdapter);

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserEmail().length() > 0) {
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
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
                getQuoteIDServiceCall();
            }
        });

    }

    public void getProductDetailServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<ProductDetailsResponse> loginResponseCall = service.getProductDetails(skuID);
        loginResponseCall.enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
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
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Picasso.get().load(Constants.PRODUCT_IMAGE__BASE_URL + productDetailsResponse.getMedia_gallery_entries().get(position).getFile()).into(productImageView);
    }

    public void getSimilarProductsServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<SimilarProductsResponse> loginResponseCall = service.getSimilarProducts(skuID);
        loginResponseCall.enqueue(new Callback<SimilarProductsResponse>() {
            @Override
            public void onResponse(Call<SimilarProductsResponse> call, Response<SimilarProductsResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        similarProductsRecyclerView.setLayoutManager(mLayoutManager);
                        SimilarProductsAdapter similarProductsAdapter = new SimilarProductsAdapter(context, response.body().getData());
                        similarProductsRecyclerView.setAdapter(similarProductsAdapter);
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SimilarProductsResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getQuoteIDServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<QuoteIDResponse> loginResponseCall = service.getQuoteID(shared.getToken(), "123");
        loginResponseCall.enqueue(new Callback<QuoteIDResponse>() {
            @Override
            public void onResponse(Call<QuoteIDResponse> call, Response<QuoteIDResponse> response) {

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
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCartServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setQty(1);
        addToCartRequest.setQuote_id(quoteID);
        addToCartRequest.setSku(skuID);
        Call<AddToCartResponse> loginResponseCall = service.addToCart("Bearer " + shared.getToken(), addToCartRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                if (response.code() == 200) {
                    Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    cartTextView.setText("Added");
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
