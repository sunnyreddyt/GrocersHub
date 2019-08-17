package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText userNameEditText, passwordEditText;
    TextView registerTextView, loginTextView, forgetPasswordTextView;
    Shared shared;
    String mobile_numberString;
    GHUtil ghUtil;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        ghUtil = GHUtil.getInstance(context);
        shared = new Shared(context);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerTextView = (TextView) findViewById(R.id.registerTextView);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        forgetPasswordTextView = (TextView) findViewById(R.id.forgetPasswordTextView);

        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        userNameEditText.setText("sunnyreddyt@gmail.com");
        passwordEditText.setText("$Sunny12");

        Intent intent = getIntent();
        mobile_numberString = intent.getStringExtra("mobile_number");

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameEditText.getText().toString().length() > 0 && passwordEditText.getText().toString().length() > 0) {
                    //  if (ghUtil.isPasswordValid(passwordEditText.getText().toString().trim())) {
                    loginServiceCall();
                   /* } else {
                        Toast.makeText(context, "Inavlid Password", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Toast.makeText(context, "Enter Username and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loginServiceCall() {
        ghUtil.showDialog(LoginActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        GeneralRequest generalRequest = new GeneralRequest();
        generalRequest.setUsername(userNameEditText.getText().toString().trim());
        generalRequest.setPassword(passwordEditText.getText().toString().trim());
        Call<GeneralResponse> loginResponseCall = service.userLogin(generalRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        shared.setToken(response.body().getToken());
                        customerDetailsServiceCall();
                    } else {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
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
        ghUtil.showDialog(LoginActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<GeneralResponse> loginResponseCall = service.customerDetails("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                    shared.setUserID(String.valueOf(response.body().getId()));
                    shared.setUserFirstName(response.body().getFirstname());
                    shared.setUserLastName(response.body().getLastname());
                    shared.setUserName(shared.getUserFirstName() + " " + shared.getUserLastName());
                    shared.setUserUserEmail(response.body().getEmail());
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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
}

