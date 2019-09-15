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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocers.hub.adapters.AddressListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.AddAddressRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.models.UserAddressListModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressActivity extends AppCompatActivity implements ItemClickListener {

    TextView paymentTextView, addAddressTextView, noAddressTextView;
    Shared shared;
    GHUtil ghUtil;
    Context context;
    ImageView backImageView;
    Dialog addAddressDialog;
    RecyclerView addressRecyclerView;
    String name = "", phone = "", pincode = "", address = "", city = "";
    ArrayList<UserAddressListModel> userAddressListModelArrayList;
    public static int selectedAddressPosition;
    AddressListAdapter addressListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        selectedAddressPosition = -1;

        context = ShippingAddressActivity.this;
        shared = new Shared(ShippingAddressActivity.this);
        ghUtil = GHUtil.getInstance(ShippingAddressActivity.this);

        paymentTextView = (TextView) findViewById(R.id.paymentTextView);
        noAddressTextView = (TextView) findViewById(R.id.noAddressTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        addressRecyclerView = (RecyclerView) findViewById(R.id.addressRecyclerView);
        addAddressTextView = (TextView) findViewById(R.id.addAddressTextView);

        addAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddressDialog();
            }
        });

        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAddressPosition == -1) {
                    Toast.makeText(context, "Please select any address", Toast.LENGTH_SHORT).show();
                } else {
                    if (name.length() > 0 && phone.length() > 0 && pincode.length() > 0 && address.length() > 0 && city.length() > 0) {
                        setShippingAddressServiceCall();
                    } else {
                        Toast.makeText(context, "Please choose one address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            customerDetailsServiceCall();
        } else {
            Toast.makeText(ShippingAddressActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void setShippingAddressServiceCall() {
        ghUtil.showDialog(ShippingAddressActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        ShippingAddressRequest shippingAddressRequest = new ShippingAddressRequest();

        ShippingAddressRequest.Shipping_address shipping_address = new ShippingAddressRequest.Shipping_address();
        shipping_address.setRegion("Telangana");
        shipping_address.setRegion_id(564);
        shipping_address.setRegion_code("TN");
        shipping_address.setCountry_id("IN");
        shipping_address.setCity(city);
        shipping_address.setPostcode(pincode);
        shipping_address.setFirstname(name);
        shipping_address.setLastname(name);
        shipping_address.setEmail(shared.getUserEmail());
        shipping_address.setTelephone(phone);
        shipping_address.setSame_as_billing(1);
        shipping_address.setStreet(address);

//address info
        ShippingAddressRequest.AddressInformation addressInformation = new ShippingAddressRequest.AddressInformation();
        addressInformation.setShipping_address(shipping_address);
        addressInformation.setShipping_carrier_code("flatrate");
        addressInformation.setShipping_method_code("flatrate");
        shippingAddressRequest.setAddressInformation(addressInformation);

        final Gson gson = new Gson();
        Log.v("shippingAddressRequest", gson.toJson(shippingAddressRequest));
        Log.v("token", shared.getToken());

        Call<ShippingResponse> loginResponseCall = service.setShipping(shared.getToken(), shippingAddressRequest);
        loginResponseCall.enqueue(new Callback<ShippingResponse>() {
            @Override
            public void onResponse(Call<ShippingResponse> call, Response<ShippingResponse> response) {
                ghUtil.dismissDialog();
                Log.v("shippingResponse", gson.toJson(response.body()));
                if (response.code() == 200) {
                    ghUtil.setShippingResponse(response.body());
                    Gson gson = new Gson();
                    Log.v("ShippingResponse", gson.toJson(response.body()));
                    Intent intent = new Intent(ShippingAddressActivity.this, CheckoutActivity.class);
                    intent.putExtra("email", shared.getUserEmail());
                    intent.putExtra("phone", phone);
                    intent.putExtra("postcode", pincode);
                    intent.putExtra("address", address);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ShippingResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addAddressDialog() {
        addAddressDialog = new Dialog(ShippingAddressActivity.this);
        addAddressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addAddressDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addAddressDialog.setContentView(R.layout.dialog_add_dialog);

        TextView addAddressTextView = (TextView) addAddressDialog.findViewById(R.id.addAddressTextView);
        final EditText nameEditText = (EditText) addAddressDialog.findViewById(R.id.nameEditText);
        final EditText emailEditText = (EditText) addAddressDialog.findViewById(R.id.emailEditText);
        final EditText phoneEditText = (EditText) addAddressDialog.findViewById(R.id.phoneEditText);
        final EditText pincodeEditText = (EditText) addAddressDialog.findViewById(R.id.pincodeEditText);
        final EditText addressEditText = (EditText) addAddressDialog.findViewById(R.id.addressEditText);
        final EditText cityEditText = (EditText) addAddressDialog.findViewById(R.id.cityEditText);


        addAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cityEditText.getText().toString().length() > 0 && nameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0 && pincodeEditText.getText().toString().length() > 0 && addressEditText.getText().toString().length() > 0 && phoneEditText.getText().toString().length() > 0) {

                    AddAddressRequest addAddressRequest = new AddAddressRequest();
                    AddAddressRequest address = new AddAddressRequest();
                    AddAddressRequest region = new AddAddressRequest();
                    region.setRegion_code("TG");
                    region.setRegion_id(564);
                    address.setCustomer_id(Integer.parseInt(shared.getUserID()));
                    address.setRegion(region);
                    address.setRegion_id(0);
                    address.setCountry_id("IN");
                    address.setStreet(addressEditText.getText().toString());
                    address.setTelephone(phoneEditText.getText().toString());
                    address.setPostcode(pincodeEditText.getText().toString());
                    address.setCity(cityEditText.getText().toString());
                    address.setFirstname(nameEditText.getText().toString());
                    address.setLastname(nameEditText.getText().toString());
                    address.setDefault_billing(true);
                    address.setDefault_shipping(true);
                    addAddressRequest.setAddress(address);
                    addAddressServiceCall(addAddressRequest);

                } else {
                    Toast.makeText(context, "Please enter all values", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addAddressDialog.show();
    }

    public void addAddressServiceCall(AddAddressRequest addAddressRequest) {
        ghUtil.showDialog(ShippingAddressActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        Gson gson = new Gson();
        Log.v("addAddressRequest", gson.toJson(addAddressRequest));

        Call<GeneralResponse> loginResponseCall = service.addAddress(shared.getToken(), addAddressRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    addAddressDialog.dismiss();
                    Toast.makeText(ShippingAddressActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    if (ghUtil.isConnectingToInternet()) {
                        customerDetailsServiceCall();
                    } else {
                        Toast.makeText(ShippingAddressActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
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

    public void customerDetailsServiceCall() {
        ghUtil.showDialog(ShippingAddressActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<GeneralResponse> loginResponseCall = service.customerDetails(shared.getToken());
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    userAddressListModelArrayList = new ArrayList<UserAddressListModel>();
                    userAddressListModelArrayList = response.body().getAddresses();

                    if (userAddressListModelArrayList != null && userAddressListModelArrayList.size() > 0) {
                        addressRecyclerView.setVisibility(View.VISIBLE);
                        noAddressTextView.setVisibility(View.GONE);

                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        addressRecyclerView.setLayoutManager(mLayoutManager);
                        addressListAdapter = new AddressListAdapter(context, userAddressListModelArrayList);
                        addressRecyclerView.setAdapter(addressListAdapter);
                        addressListAdapter.setClickListener(ShippingAddressActivity.this);
                    } else {
                        addressRecyclerView.setVisibility(View.GONE);
                        noAddressTextView.setVisibility(View.VISIBLE);
                    }
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

    @Override
    public void onClick(int position) {
        name = userAddressListModelArrayList.get(position).getFirstname();
        pincode = userAddressListModelArrayList.get(position).getPostcode();
        phone = userAddressListModelArrayList.get(position).getTelephone();
        address = userAddressListModelArrayList.get(position).getStreet();
        city = userAddressListModelArrayList.get(position).getCity();
        selectedAddressPosition = position;
        addressListAdapter.notifyDataSetChanged();
    }
}
