package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    TextView paymentTextView;
    EditText nameEditText, emailEditText, phoneEditText, addressEditText, pincodeEditText;
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
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        pincodeEditText = (EditText) findViewById(R.id.pincodeEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);


        nameEditText.setText(shared.getUserName());
        emailEditText.setText(shared.getUserEmail());


        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0 && pincodeEditText.getText().toString().length() > 0 && addressEditText.getText().toString().length() > 0 && phoneEditText.getText().toString().length() > 0) {
                 /*   Intent intent = new Intent(AddressActivity.this, CheckoutActivity.class);
                    startActivity(intent);*/
                    setChippingAddressServiceCall();
                } else {
                    Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setChippingAddressServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        ShippingAddressRequest shippingAddressRequest = new ShippingAddressRequest();

        ShippingAddressRequest shipping_address = new ShippingAddressRequest();
        shipping_address.setRegion("Telangana");
        shipping_address.setRegion_id(43);
        shipping_address.setRegion_code("TN");
        shipping_address.setCountry_id("IN");
        shipping_address.setPostcode(pincodeEditText.getText().toString());
        shipping_address.setFirstname(nameEditText.getText().toString());
        shipping_address.setEmail(emailEditText.getText().toString());
        shipping_address.setTelephone(phoneEditText.getText().toString());
        shipping_address.setSame_as_billing(1);
        // street info
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(addressEditText.getText().toString());
        shipping_address.setStreet(jsonArray);
//address info
        ShippingAddressRequest addressInformation = new ShippingAddressRequest();
        addressInformation.setShipping_address(shipping_address);
        addressInformation.setShipping_carrier_code("flatrate");
        addressInformation.setShipping_method_code("flatrate");
        shippingAddressRequest.setAddressInformation(addressInformation);

        Gson gson = new Gson();
        Log.v("shippingAddressRequest", gson.toJson(shippingAddressRequest));

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
