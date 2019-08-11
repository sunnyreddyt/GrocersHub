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
import com.grocers.hub.adapters.ProductsUnitsAdapter;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.models.ProductDetailsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView productImagesRecyclerView, productUnitsRecyclerView;
    ProductImagesListAdapter productImagesListAdapter;
    ProductsUnitsAdapter productsUnitsAdapter;
    RelativeLayout cartLayout;
    ImageView backImageView, productImageView;
    String skuID;
    TextView productNameTextView, productPriceTextView;
    ProductDetailsResponse productDetailsResponse;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        productImagesRecyclerView = (RecyclerView) findViewById(R.id.productImagesRecyclerView);
        productUnitsRecyclerView = (RecyclerView) findViewById(R.id.productUnitsRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);
        context = ProductDetailActivity.this;
        productPriceTextView = (TextView) findViewById(R.id.productPriceTextView);
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
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
}
