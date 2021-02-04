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

import com.grocers.hub.adapters.SupportDetailsAdapter;
import com.grocers.hub.models.MinimumOrderResponse;
import com.grocers.hub.models.SupportResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {

    private ImageView backImageView;
    private GHUtil ghUtil;
    Context context;
    RecyclerView supportRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        context = SupportActivity.this;
        ghUtil = GHUtil.getInstance(context);
        supportRecyclerView = (RecyclerView) findViewById(R.id.supportRecyclerView);
        backImageView = (ImageView) findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            getSupportDetails();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSupportDetails() {
        ghUtil.showDialog(SupportActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<SupportResponse> loginResponseCall = service.getSupportDetails();
        loginResponseCall.enqueue(new Callback<SupportResponse>() {
            @Override
            public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {

                    ArrayList<SupportResponse> supportResponseArrayList = new ArrayList<SupportResponse>();
                    if (response.body() != null) {
                        if (response.body().getLocation().getHyderabad() != null) {
                            supportResponseArrayList.addAll(response.body().getLocation().getHyderabad());
                        }
                        if (response.body().getLocation().getKamreddy() != null) {
                            supportResponseArrayList.addAll(response.body().getLocation().getKamreddy());
                        }
                        if (response.body().getLocation().getNizambad() != null) {
                            supportResponseArrayList.addAll(response.body().getLocation().getNizambad());
                        }
                    }

                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    supportRecyclerView.setLayoutManager(mLayoutManager);
                    SupportDetailsAdapter supportDetailsAdapter = new SupportDetailsAdapter(context, supportResponseArrayList);
                    supportRecyclerView.setAdapter(supportDetailsAdapter);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SupportResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
            }
        });
    }
}
