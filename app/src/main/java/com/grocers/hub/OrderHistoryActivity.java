package com.grocers.hub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView ordersListRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ordersListRecyclerView = (RecyclerView) findViewById(R.id.ordersListRecyclerView);


    }
}
