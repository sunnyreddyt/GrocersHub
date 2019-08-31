package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.OrderProductsAdapter;
import com.grocers.hub.adapters.PaymentAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.DeleteCartResponse;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.models.PaymentRequest;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

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
    String quoteID = "";
    String email, phone, postcode, address;
    Dialog couponDialog;

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

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("email");
        postcode = intent.getStringExtra("email");
        address = intent.getStringExtra("email");

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
                if (quoteID.length() > 0) {
                    setPaymentServiceCall();
                } else {
                    getQuoteIDServiceCall();
                    Toast.makeText(context, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getQuoteIDServiceCall();

    }

    public void setPaymentServiceCall() {
        ghUtil.dismissDialog();
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCartId(Integer.parseInt(quoteID));

        PaymentRequest.PaymentMethod paymentMethod = new PaymentRequest.PaymentMethod();
        paymentMethod.setMethod("cashondelivery");

        PaymentRequest.BillingAddress billingAddress = new PaymentRequest.BillingAddress();
        billingAddress.setEmail(email);
        billingAddress.setRegion("Telangana");
        billingAddress.setRegion_id(564);
        billingAddress.setRegion_code("TG");
        billingAddress.setCountry_id("IN");
        billingAddress.setStreet(address);
        billingAddress.setPostcode(postcode);
        billingAddress.setCity(shared.getCity());
        billingAddress.setTelephone(phone);
        billingAddress.setFirstname(shared.getUserFirstName());
        billingAddress.setLastname(shared.getUserLastName());

        Call<GeneralResponse> loginResponseCall = service.setPayment("Bearer " + shared.getToken(), paymentRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(context, "Order Places Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getQuoteIDServiceCall() {
        ghUtil.showDialog(CheckoutActivity.this);
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

    public void couponDialog() {
        couponDialog = new Dialog(CheckoutActivity.this);
        couponDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        couponDialog.setContentView(R.layout.dialog_coupon);

        final EditText couponCodeEditText = (EditText) couponDialog.findViewById(R.id.couponCodeEditText);
        TextView skipTextView = (TextView) couponDialog.findViewById(R.id.skipTextView);
        TextView applyTextView = (TextView) couponDialog.findViewById(R.id.applyTextView);


        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponDialog.dismiss();
            }
        });

        applyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String couponCode = couponCodeEditText.getText().toString();
                if (couponCode.length() > 0) {
                    applyCouponServiceCall(couponCode);
                }
            }
        });

        couponDialog.show();
    }

    public void applyCouponServiceCall(String couponCode) {
        ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        Call<DeleteCartResponse> loginResponseCall = service.applyCoupon(quoteID, couponCode);
        loginResponseCall.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200 && response.body().getStatus() == 200) {

                    couponDialog.dismiss();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
