package com.grocers.hub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.utils.ABUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunnyreddy on 27/02/18.
 */

public class RegistrationActivity extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, mobileNumberEditText, passwordEditText;
    TextView registerTextView;
    ABUtil abUtil;
    Shared shared;
    ImageView backImageView;
    String mobile_numberString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        abUtil = ABUtil.getInstance(RegistrationActivity.this);
        shared = new Shared(RegistrationActivity.this);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        mobileNumberEditText = (EditText) findViewById(R.id.mobileNumberEditText);
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

                if (firstNameEditText.getText().toString().length() > 0 && lastNameEditText.getText().toString().length() > 0 && mobileNumberEditText.getText().toString().length() > 0 && passwordEditText.getText().toString().length() > 0) {

                    // new Register().execute();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
