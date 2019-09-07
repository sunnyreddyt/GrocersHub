package com.grocers.hub;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.grocers.hub.adapters.AllCategoriesListAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.fragments.CategoriesFragment;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView backImageView;
    EditText emailEditText;
    TextView loginTextView;
    GHUtil ghUtil;
    Shared shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        ghUtil = GHUtil.getInstance(ForgotPasswordActivity.this);
        shared = new Shared(ForgotPasswordActivity.this);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailEditText.getText().toString().length() > 0 && ghUtil.isValidEmail(emailEditText.getText().toString())) {
                    forgotPasswordServiceCall();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void forgotPasswordServiceCall() {
        ghUtil.showDialog(ForgotPasswordActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<GeneralResponse> loginResponseCall = service.forgotPassword(emailEditText.getText().toString(), shared.getToken());
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please check your mail for reset password", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
