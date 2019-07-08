package com.grocers.hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.adapters.ProductsAdapter;

public class CategoryProductsActivity extends AppCompatActivity {

    RecyclerView productsRecyclerView;
    ProductsAdapter productsAdapter;
    RelativeLayout cartLayout;
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);

        //products List
        productsRecyclerView.setLayoutManager(new GridLayoutManager(CategoryProductsActivity.this, 2));
        productsAdapter = new ProductsAdapter(CategoryProductsActivity.this, null);
        productsRecyclerView.setAdapter(productsAdapter);


        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryProductsActivity.this, CartActivity.class);
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
}
