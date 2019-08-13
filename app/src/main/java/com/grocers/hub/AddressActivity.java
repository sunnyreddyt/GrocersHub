package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    TextView paymentTextView;
    Shared shared;
    GHUtil ghUtil;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        context = AddressActivity.this;
        shared = new Shared(AddressActivity.this);
        ghUtil = GHUtil.getInstance(AddressActivity.this);

        paymentTextView = (TextView) findViewById(R.id.paymentTextView);


        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, CheckoutActivity.class);
                startActivity(intent);
                //  setChippingAddressServiceCall();
            }
        });
    }

    public void setChippingAddressServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        ShippingAddressRequest shippingAddressRequest = new ShippingAddressRequest();

        Call<ShippingResponse> loginResponseCall = service.setShipping("Bearer " + shared.getToken(), shippingAddressRequest);
        loginResponseCall.enqueue(new Callback<ShippingResponse>() {
            @Override
            public void onResponse(Call<ShippingResponse> call, Response<ShippingResponse> response) {

                if (response.code() == 200) {
                    ghUtil.setShippingResponse(response.body());
                    Intent intent = new Intent(AddressActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ShippingResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
