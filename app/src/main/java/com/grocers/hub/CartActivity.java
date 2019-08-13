package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.SimilarProductsAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.SimilarProductsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    ImageView backImageView;
    TextView paymentTextView;
    Shared shared;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        shared = new Shared(CartActivity.this);
        context = CartActivity.this;
        backImageView = (ImageView) findViewById(R.id.backImageView);
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        paymentTextView = (TextView) findViewById(R.id.paymentTextView);

        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        getCartProductsServiceCall();

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void getCartProductsServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                if (response.code() == 200) {
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                    cartRecyclerView.setLayoutManager(mLayoutManager);
                    CartProductsAdapter cartProductsAdapter = new CartProductsAdapter(CartActivity.this, response.body().getItems());
                    cartRecyclerView.setAdapter(cartProductsAdapter);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
