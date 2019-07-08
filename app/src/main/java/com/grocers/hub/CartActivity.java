package com.grocers.hub;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CartProductsAdapter;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        CartProductsAdapter cartProductsAdapter = new CartProductsAdapter(CartActivity.this);
        cartRecyclerView.setAdapter(cartProductsAdapter);


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
