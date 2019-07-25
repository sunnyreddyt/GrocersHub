package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.customer;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunnyreddy on 27/02/18.
 */

public class RegistrationActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    TextView registerTextView;
    GHUtil ghUtil;
    Shared shared;
    ImageView backImageView;
    String mobile_numberString;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        context = RegistrationActivity.this;

        ghUtil = GHUtil.getInstance(context);
        shared = new Shared(context);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerTextView = (TextView) findViewById(R.id.registerTextView);

        Intent intent = getIntent();
        mobile_numberString = intent.getStringExtra("mobile_number");

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstNameEditText.getText().toString().length() > 0 && lastNameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0 && passwordEditText.getText().toString().length() > 0) {

                    if (ghUtil.isPasswordValid(passwordEditText.getText().toString().trim())) {
                        registerServiceCall();
                    } else {
                        Toast.makeText(context, "Password must contain atleast one number,special character, lower case and upper case", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registerServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        GeneralRequest generalRequest = new GeneralRequest();
        customer customerReq = new customer();
        customerReq.setEmail(emailEditText.getText().toString().trim());
        customerReq.setFirstname(firstNameEditText.getText().toString().trim());
        customerReq.setLastname(lastNameEditText.getText().toString().trim());
        generalRequest.setCustomer(customerReq);
        generalRequest.setPassword(passwordEditText.getText().toString().trim());
        Call<GeneralResponse> loginResponseCall = service.userRegistration(generalRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.body().getMessage() != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
