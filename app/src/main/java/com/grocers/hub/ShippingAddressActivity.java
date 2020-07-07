package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.grocers.hub.models.FinalOrderResponse;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.models.UserAddressListModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    String name = "", phone = "", pincode = "", address = "", city = "", addressId = "";
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
                        new ShippingAddressServiceCall().execute();
                        ghUtil.showDialog(ShippingAddressActivity.this);
                        //setShippingAddressServiceCall();
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

    /*public void setShippingAddressServiceCall() {
        ghUtil.showDialog(ShippingAddressActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        ShippingAddressRequest shippingAddressRequest = new ShippingAddressRequest();

        ShippingAddressRequest.Shipping_address shipping_address = new ShippingAddressRequest.Shipping_address();
        shipping_address.setCustomerAddressId(addressId);
        shipping_address.setCountryId("IN");
        shipping_address.setRegionId("564");
        shipping_address.setRegionCode("TG");
        shipping_address.setRegion("Telangana");
        shipping_address.setCustomerId(shared.getUserID());
        shipping_address.setStreet(address);
        shipping_address.setTelephone(phone);
        shipping_address.setPostcode(pincode);
        shipping_address.setCity(city);
        shipping_address.setFirstname(name);
        shipping_address.setLastname(name);


        ShippingAddressRequest.Billing_address billing_address = new ShippingAddressRequest.Billing_address();
        billing_address.setCustomerAddressId(addressId);
        billing_address.setCountryId("IN");
        billing_address.setRegionId("564");
        billing_address.setRegionCode("TG");
        billing_address.setRegion("Telangana");
        billing_address.setCustomerId(shared.getUserID());
        billing_address.setStreet(address);
        billing_address.setTelephone(phone);
        billing_address.setPostcode(pincode);
        billing_address.setCity(city);
        billing_address.setFirstname(name);
        billing_address.setLastname(name);
        billing_address.setSaveInAddressBook(null);

        ShippingAddressRequest.ExtensionAttributes extensionAttributes = new ShippingAddressRequest.ExtensionAttributes();

//address info
        ShippingAddressRequest.AddressInformation addressInformation = new ShippingAddressRequest.AddressInformation();
        addressInformation.setShipping_address(shipping_address);
        addressInformation.setExtension_attributes(extensionAttributes);
        addressInformation.setBilling_address(billing_address);
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
                    intent.putExtra("city", city);
                    intent.putExtra("phone", phone);
                    intent.putExtra("postcode", pincode);
                    intent.putExtra("address", address);
                    intent.putExtra("name", name);
                    intent.putExtra("addressId", addressId);
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
    }*/

    private class ShippingAddressServiceCall extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String str = postData();
            return str;
        }

        protected void onPostExecute(String json) {

            try {
                ghUtil.dismissDialog();

                if (json.length() > 0) {
                    Log.v("mainObject:response", json);
                    JSONObject jsonObjectMain;

                    try {
                        jsonObjectMain = new JSONObject(json);

                        if (jsonObjectMain.has("payment_methods") && jsonObjectMain.has("totals")) {
                            ghUtil.setShippingResponse(json);
                            Gson gson = new Gson();
                            Log.v("ShippingResponse", gson.toJson(json));
                            Intent intent = new Intent(ShippingAddressActivity.this, CheckoutActivity.class);
                            intent.putExtra("email", shared.getUserEmail());
                            intent.putExtra("city", city);
                            intent.putExtra("phone", phone);
                            intent.putExtra("postcode", pincode);
                            intent.putExtra("address", address);
                            intent.putExtra("name", name);
                            intent.putExtra("addressId", addressId);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @SuppressWarnings("deprecation")
        public String postData() {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://www.grocershub.in/homeapi/shippinginfo?token=" + shared.getToken());
            JSONObject mainObject = new JSONObject();

            try {
                JSONObject billing_address = new JSONObject();
                billing_address.put("city", city);
                billing_address.put("countryId", "IN");
                billing_address.put("customerAddressId", addressId);
                billing_address.put("customerId", shared.getUserID());
                billing_address.put("firstname", name);
                billing_address.put("lastname", name);
                billing_address.put("postcode", pincode);
                billing_address.put("region", "Telangana");
                billing_address.put("region_code", "TG");
                billing_address.put("region_id", "564");
                billing_address.put("street", address);
                billing_address.put("telephone", phone);

                JSONObject shipping_address = new JSONObject();
                shipping_address.put("city", city);
                shipping_address.put("countryId", "IN");
                shipping_address.put("customerAddressId", addressId);
                shipping_address.put("customerId", shared.getUserID());
                shipping_address.put("firstname", name);
                shipping_address.put("lastname", name);
                shipping_address.put("postcode", pincode);
                shipping_address.put("region", "Telangana");
                shipping_address.put("region_code", "TG");
                shipping_address.put("region_id", "564");
                shipping_address.put("street", address);
                shipping_address.put("telephone", phone);


                JSONObject extension_attributes = new JSONObject();

                JSONObject addressInformation = new JSONObject();
                addressInformation.put("billing_address", billing_address);
                addressInformation.put("shipping_address", shipping_address);
                addressInformation.put("extension_attributes", extension_attributes);
                addressInformation.put("shipping_carrier_code", "flatrate");
                addressInformation.put("shipping_method_code", "flatrate");

                mainObject.put("addressInformation", addressInformation);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v("mainObject:request", mainObject.toString());
            StringEntity se = null;
            try {
                se = new StringEntity(mainObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            httpPost.setEntity(se);
            httpPost.setHeader("Content-type", "application/json");
            String json = "";
            try {

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httpPost);
                HttpEntity httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
                //  Log.e("objJsonMain", "" + json);
            } catch (Exception e) {
                ghUtil.dismissDialog();
                e.printStackTrace();
            }
            return json;
        }
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

                    if (pincodeEditText.getText().toString().length() == 6) {

                        if (ghUtil.isValidEmail(emailEditText.getText().toString())) {

                            if (ghUtil.isValidPhone(phoneEditText.getText().toString())) {
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
                                Toast.makeText(ShippingAddressActivity.this, "Please provide valid phone", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShippingAddressActivity.this, "Please provide valid email", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ShippingAddressActivity.this, "Please provide valid pincode", Toast.LENGTH_SHORT).show();
                    }

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
        addressId = String.valueOf(userAddressListModelArrayList.get(position).getId());
        addressListAdapter.notifyDataSetChanged();
    }
}
