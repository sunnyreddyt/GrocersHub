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

import com.grocers.hub.adapters.PaymentAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.PaymentRequest;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    ImageView backImageView;
    RecyclerView paymentMethodsRecyclerView;
    GHUtil ghUtil;
    TextView orderTextView;
    Context context;
    Shared shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        shared = new Shared(CheckoutActivity.this);
        context = CheckoutActivity.this;
        ghUtil = GHUtil.getInstance(CheckoutActivity.this);

        orderTextView = (TextView) findViewById(R.id.orderTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        paymentMethodsRecyclerView = (RecyclerView) findViewById(R.id.paymentMethodsRecyclerView);

        ShippingResponse shippingResponse = ghUtil.getShippingResponse();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        paymentMethodsRecyclerView.setLayoutManager(mLayoutManager);
        PaymentAdapter paymentAdapter = new PaymentAdapter(context, shippingResponse.getPayment_methods());
        paymentMethodsRecyclerView.setAdapter(paymentAdapter);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPaymentServiceCall();
            }
        });

    }

    public void setPaymentServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        PaymentRequest paymentRequest = new PaymentRequest();

        Call<GeneralResponse> loginResponseCall = service.setPayment("Bearer " + shared.getToken(), paymentRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                if (response.code() == 200) {
                    Toast.makeText(context, "Order Places Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
