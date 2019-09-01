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
import com.grocers.hub.models.CustomAttributes;
import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.UpdateProfileRequest;
import com.grocers.hub.models.customer;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, emailEditText, mobileEditText;
    TextView registerTextView;
    GHUtil ghUtil;
    Shared shared;
    ImageView backImageView;
    String mobile_numberString;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        context = EditProfileActivity.this;

        ghUtil = GHUtil.getInstance(context);
        shared = new Shared(context);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        registerTextView = (TextView) findViewById(R.id.registerTextView);

        mobileEditText.setText(shared.getUserMobile());
        firstNameEditText.setText(shared.getUserFirstName());
        lastNameEditText.setText(shared.getUserLastName());
        emailEditText.setText(shared.getUserEmail());

        emailEditText.setEnabled(false);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobileEditText.getText().toString().length() > 0 && firstNameEditText.getText().toString().length() > 0 && lastNameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0) {
                    updateProfileServiceCall();
                } else {
                    Toast.makeText(context, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateProfileServiceCall() {
        ghUtil.showDialog(EditProfileActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        UpdateProfileRequest.UpdateCustomer updateCustomer = new UpdateProfileRequest.UpdateCustomer();
        updateCustomer.setEmail(emailEditText.getText().toString().trim());
        updateCustomer.setFirstname(firstNameEditText.getText().toString().trim());
        updateCustomer.setLastname(lastNameEditText.getText().toString().trim());
        updateCustomer.setMobile(mobileEditText.getText().toString().trim());
        updateCustomer.setId(Integer.parseInt(shared.getUserID()));
        updateCustomer.setDob("");
        updateCustomer.setMiddlename("");
        updateCustomer.setWebsite_id(1);

        ArrayList<CustomAttributes> customAttributesArrayList = new ArrayList<CustomAttributes>();
        CustomAttributes customAttributes = new CustomAttributes();
        customAttributes.setAttribute_code("mobile");
        customAttributes.setValue(mobileEditText.getText().toString().trim());
        customAttributesArrayList.add(customAttributes);
        updateCustomer.setCustom_attributes(customAttributesArrayList);

        updateProfileRequest.setCustomer(updateCustomer);

        Call<GeneralResponse> loginResponseCall = service.updateProfile(shared.getUserID(), updateProfileRequest);
        loginResponseCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {

                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();

                    shared.setUserFirstName(response.body().getFirstname());
                    shared.setUserLastName(response.body().getLastname());
                    shared.setUserName(response.body().getFirstname() + " " + response.body().getLastname());
                    shared.setUserMobile(response.body().getCustom_attributes().get(0).getValue());
                    /*Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);*/
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
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
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

