package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CustomAttributes;
import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.customer;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunnyreddy on 27/02/18.
 */

public class RegistrationActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, mobileEditText, confirmPasswordEditText;
    TextView registerTextView, loginTextView;
    GHUtil ghUtil;
    Shared shared;
    ImageView backImageView;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        context = RegistrationActivity.this;
        ghUtil = GHUtil.getInstance(context);
        shared = new Shared(context);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerTextView = (TextView) findViewById(R.id.registerTextView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobileEditText.getText().toString().length() > 0 && firstNameEditText.getText().toString().length() > 0 && lastNameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0 && passwordEditText.getText().toString().length() > 0) {
                    if (ghUtil.isPasswordValid(passwordEditText.getText().toString().trim()) && passwordEditText.getText().toString().length() > 6) {
                        if (passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                            registerServiceCall();
                        } else {
                            Toast.makeText(context, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Password must contain atleast min 6 characters with one number,special character, lower and upper case", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void registerServiceCall() {
        ghUtil.showDialog(RegistrationActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        GeneralRequest generalRequest = new GeneralRequest();
        customer customerReq = new customer();
        customerReq.setEmail(emailEditText.getText().toString().trim());
        customerReq.setFirstname(firstNameEditText.getText().toString().trim());
        customerReq.setLastname(lastNameEditText.getText().toString().trim());
        customerReq.setMobile(mobileEditText.getText().toString().trim());
        generalRequest.setPassword(passwordEditText.getText().toString().trim());

        ArrayList<CustomAttributes> customAttributesArrayList = new ArrayList<CustomAttributes>();
        CustomAttributes customAttributes = new CustomAttributes();
        customAttributes.setAttribute_code("mobile");
        customAttributes.setValue("");
        customAttributesArrayList.add(customAttributes);

        customerReq.setCustom_attributes(customAttributesArrayList);
        generalRequest.setCustomer(customerReq);

        Gson gson = new Gson();
        Log.v("registrationRequest", gson.toJson(generalRequest));

        Call<GeneralResponse> loginResponseCall = service.userRegistration(generalRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    ghUtil.dismissDialog();
                    if (response.code() == 200 && response.body().getStatus().equalsIgnoreCase("200")) {

                        shared.setUserID(String.valueOf(response.body().getResult().getId()));
                        shared.setUserFirstName(response.body().getResult().getFirstname());
                        shared.setUserLastName(response.body().getResult().getLastname());
                        shared.setUserName(shared.getUserFirstName() + " " + shared.getUserLastName());
                        shared.setUserEmail(response.body().getResult().getEmail());
                        shared.setUserMobile(response.body().getResult().getCustom_attributes().get(0).getValue());

                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (response.code() == 200 && response.body().getStatus() == "400") {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(context, "User already exists with this email", Toast.LENGTH_SHORT).show();
                    } else {
                        if (response.body().getMessage() != null) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
