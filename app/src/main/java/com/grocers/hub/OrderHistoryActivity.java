package com.grocers.hub;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.OrderAdapter;
import com.grocers.hub.adapters.SearchProductsAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.OrdersResponse;
import com.grocers.hub.models.ProductsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView ordersListRecyclerView;
    Shared shared;
    GHUtil ghUtil;
    Context context;
    ArrayList<OrdersResponse> ordersResponseArrayList;
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        context = OrderHistoryActivity.this;
        ghUtil = GHUtil.getInstance(OrderHistoryActivity.this);
        shared = new Shared(OrderHistoryActivity.this);
        ordersListRecyclerView = (RecyclerView) findViewById(R.id.ordersListRecyclerView);
        backImageView = (ImageView) findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            getOrdersServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void getOrdersServiceCall() {
        ghUtil.showDialog(OrderHistoryActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<OrdersResponse> loginResponseCall = service.getOrderHistory(shared.getUserID());
        loginResponseCall.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    ordersResponseArrayList = new ArrayList<OrdersResponse>();
                    ordersResponseArrayList = response.body().getOrders();
                    if (ordersResponseArrayList != null && ordersResponseArrayList.size() > 0) {
                        ordersListRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        OrderAdapter orderAdapter = new OrderAdapter(context, ordersResponseArrayList);
                        ordersListRecyclerView.setAdapter(orderAdapter);
                    } else {
                        Toast.makeText(context, "No Orders found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
