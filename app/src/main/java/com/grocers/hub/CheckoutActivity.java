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
import com.grocers.hub.adapters.OrderProductsAdapter;
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
    RecyclerView paymentMethodsRecyclerView, productsRecyclerView;
    GHUtil ghUtil;
    TextView orderTextView, discountTextView, shippingTextView, taxTextView, grandTotalTextView;
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
        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        paymentMethodsRecyclerView = (RecyclerView) findViewById(R.id.paymentMethodsRecyclerView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);
        shippingTextView = (TextView) findViewById(R.id.shippingTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);

        ShippingResponse shippingResponse = ghUtil.getShippingResponse();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        paymentMethodsRecyclerView.setLayoutManager(mLayoutManager);
        PaymentAdapter paymentAdapter = new PaymentAdapter(context, shippingResponse.getPayment_methods());
        paymentMethodsRecyclerView.setAdapter(paymentAdapter);

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        OrderProductsAdapter orderProductsAdapter = new OrderProductsAdapter(CheckoutActivity.this, shippingResponse.getTotals().getItems());
        productsRecyclerView.setAdapter(orderProductsAdapter);

        discountTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getDiscount_amount()));
        shippingTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getShipping_amount()));
        taxTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getTax_amount()));
        grandTotalTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getGrand_total()));

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

        PaymentRequest.BillingAddress billingAddress = new PaymentRequest.BillingAddress();
        billingAddress.setEmail(shared.getUserEmail());

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
