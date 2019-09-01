package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.gson.Gson;
import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OrderProductsAdapter;
import com.grocers.hub.adapters.PaymentAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.DeleteCartResponse;
import com.grocers.hub.models.FinalOrderResponse;
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

public class CheckoutActivity extends AppCompatActivity implements ItemClickListener {

    ImageView backImageView;
    RecyclerView paymentMethodsRecyclerView, productsRecyclerView;
    GHUtil ghUtil;
    TextView orderTextView, discountTextView, shippingTextView, taxTextView, grandTotalTextView;
    Context context;
    Shared shared;
    String quoteID = "";
    String email, phone, postcode, address, name;
    Dialog couponDialog, orderSuccessDialog;
    String selectedPaymentMethod = "";
    int selectedPaymentPosition = -1;
    PaymentAdapter paymentAdapter;
    ShippingResponse shippingResponse;

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
        phone = intent.getStringExtra("phone");
        postcode = intent.getStringExtra("postcode");
        address = intent.getStringExtra("address");
        name = intent.getStringExtra("name");

        shippingResponse = ghUtil.getShippingResponse();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        paymentMethodsRecyclerView.setLayoutManager(mLayoutManager);
        paymentAdapter = new PaymentAdapter(context, shippingResponse.getPayment_methods(), selectedPaymentPosition);
        paymentMethodsRecyclerView.setAdapter(paymentAdapter);
        paymentAdapter.setCLickListener(this);

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
                if (selectedPaymentPosition != -1) {
                    if (quoteID.length() > 0) {
                        if (ghUtil.isConnectingToInternet()) {
                            setPaymentServiceCall();
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getQuoteIDServiceCall();
                        Toast.makeText(context, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please select payment method", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (ghUtil.isConnectingToInternet()) {
            getQuoteIDServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    public void setPaymentServiceCall() {
        ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCartId(Integer.parseInt(quoteID));

        PaymentRequest.PaymentMethod paymentMethod = new PaymentRequest.PaymentMethod();
        paymentMethod.setMethod(selectedPaymentMethod);

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
        billingAddress.setFirstname(name);
        billingAddress.setLastname(name);

        paymentRequest.setBilling_address(billingAddress);
        paymentRequest.setPaymentMethod(paymentMethod);

        Gson gson = new Gson();
        Log.v("paymentRequest", gson.toJson(paymentRequest));

        Call<FinalOrderResponse> loginResponseCall = service.setPayment(shared.getToken(), paymentRequest);
        loginResponseCall.enqueue(new Callback<FinalOrderResponse>() {
            @Override
            public void onResponse(Call<FinalOrderResponse> call, Response<FinalOrderResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    orderSuccessDialog();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FinalOrderResponse> call, Throwable t) {
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

    public void orderSuccessDialog() {
        orderSuccessDialog = new Dialog(CheckoutActivity.this);
        orderSuccessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        orderSuccessDialog.setContentView(R.layout.dialog_order_success);

        TextView titleTextView = (TextView) orderSuccessDialog.findViewById(R.id.titleTextView);
        TextView okTextView = (TextView) orderSuccessDialog.findViewById(R.id.okTextView);

        titleTextView.setText("Hi " + shared.getUserFirstName() + ", ");

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderSuccessDialog.dismiss();
                Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        orderSuccessDialog.show();
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

    @Override
    public void onClick(int position) {
        selectedPaymentPosition = position;
        selectedPaymentMethod = shippingResponse.getPayment_methods().get(position).getCode();
        paymentAdapter.notifyDataSetChanged();
    }
}
