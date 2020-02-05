package com.grocers.hub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.OrderDetailsProductAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.OrderDetailsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    GHUtil ghUtil;
    TextView orderTextView, orderIDTextView, orderDateTextView, orderStatus, subTotalTextView, addressTextView, contactTextView, deliveredToTextView, discountTextView, shippingTextView, taxTextView, grandTotalTextView, applyCouponTextView;
    Context context;
    Shared shared;
    ImageView backImageView;
    RecyclerView productsRecyclerView;
    LinearLayout detailsLayout;
    String orderID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        shared = new Shared(OrderDetailActivity.this);
        context = OrderDetailActivity.this;
        ghUtil = GHUtil.getInstance(OrderDetailActivity.this);

        orderTextView = (TextView) findViewById(R.id.orderTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);
        shippingTextView = (TextView) findViewById(R.id.shippingTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
        deliveredToTextView = (TextView) findViewById(R.id.deliveredToTextView);
        contactTextView = (TextView) findViewById(R.id.contactTextView);
        subTotalTextView = (TextView) findViewById(R.id.subTotalTextView);
        orderIDTextView = (TextView) findViewById(R.id.orderIDTextView);
        detailsLayout = (LinearLayout) findViewById(R.id.detailsLayout);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        orderDateTextView = (TextView) findViewById(R.id.orderDateTextView);

        orderID = getIntent().getStringExtra("orderID");
        orderIDTextView.setText(orderID);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            getOrderDetailsServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    public void getOrderDetailsServiceCall() {
        ghUtil.showDialog(OrderDetailActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<OrderDetailsResponse> loginResponseCall = service.getOrderDetails(orderID);
        loginResponseCall.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    detailsLayout.setVisibility(View.VISIBLE);

                    productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                    OrderDetailsProductAdapter orderDetailsProductAdapter = new OrderDetailsProductAdapter(OrderDetailActivity.this, response.body().getOrders().get(0).getItems());
                    productsRecyclerView.setAdapter(orderDetailsProductAdapter);

                    orderStatus.setText(response.body().getOrders().get(0).getData().getStatus());


                    Log.v("order_date",response.body().getOrders().get(0).getData().getCreated_at());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat newFormatter = new SimpleDateFormat("dd-MMM-yyyy");
                    try {
                        Date date = formatter.parse(response.body().getOrders().get(0).getData().getCreated_at());
                        String timeString = newFormatter.format(date);
                        orderDateTextView.setText(timeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    double discountDouble = Double.parseDouble(response.body().getOrders().get(0).getData().getDiscount_amount());
                    String discount = new DecimalFormat("##.##").format(discountDouble);
                    discountTextView.setText("₹ " + discount);

                    double shippingDouble = Double.parseDouble(response.body().getOrders().get(0).getData().getBase_shipping_amount());
                    String shipping = new DecimalFormat("##.##").format(shippingDouble);
                    shippingTextView.setText("₹ " + shipping);

                    double taxDouble = Double.parseDouble(response.body().getOrders().get(0).getData().getTax_amount());
                    String taxAmount = new DecimalFormat("##.##").format(taxDouble);
                    taxTextView.setText("₹ " + taxAmount);

                    double subTotalDouble = Double.parseDouble(response.body().getOrders().get(0).getData().getSubtotal());
                    String subTotal = new DecimalFormat("##.##").format(subTotalDouble);
                    subTotalTextView.setText("₹ " + subTotal);

                    double grandTotalDouble = Double.parseDouble(response.body().getOrders().get(0).getGrandTotal());
                    String grandTotal = new DecimalFormat("##.##").format(grandTotalDouble);
                    grandTotalTextView.setText("₹ " + grandTotal);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
