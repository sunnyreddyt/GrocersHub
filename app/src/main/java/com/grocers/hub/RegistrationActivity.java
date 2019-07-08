package com.grocers.hub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    EditText userNameEditText, emailEditText, passwordEditText;
    TextView registerTextView;
    ABUtil abUtil;
    Shared shared;
    String mobile_numberString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        abUtil = ABUtil.getInstance(RegistrationActivity.this);
        shared = new Shared(RegistrationActivity.this);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerTextView = (TextView) findViewById(R.id.registerTextView);

        Intent intent = getIntent();
        mobile_numberString = intent.getStringExtra("mobile_number");
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameEditText.getText().toString().length() > 0 && emailEditText.getText().toString().length() > 0 && passwordEditText.getText().toString().length() > 0) {

                    // new Register().execute();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private class Register extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String str = postData();
            return str;
        }

        protected void onPostExecute(String json) {

            try {
                if (json.length() > 0) {

                    JSONObject jsonObjectMain;

                    try {

                        jsonObjectMain = new JSONObject(json);
                        String path = "";
                        Log.e("jsonObjectMain", "" + jsonObjectMain);

                        if (jsonObjectMain.getString("error").equalsIgnoreCase("false")) {
                            if (jsonObjectMain.has("userDetails")) {


                                JSONObject userDetailsObject = jsonObjectMain.getJSONObject("userDetails");
                                shared.setUserMobile(userDetailsObject.getString("user_mobile"));
                                shared.setUserUserEmail(userDetailsObject.getString("user_email"));
                                shared.setUserName(userDetailsObject.getString("user_name"));
                                shared.setUserLocation(userDetailsObject.getString("user_location"));
                                shared.setUserLatitude(userDetailsObject.getString("user_latitude"));
                                shared.setUserLongitude(userDetailsObject.getString("user_longitude"));


                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        } else {
                            Toast.makeText(RegistrationActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }

        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @SuppressWarnings("deprecation")
        public String postData() {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(Constants.BASE_URL + "createUser");

            String json = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("user_name", userNameEditText.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", emailEditText.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", passwordEditText.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("mobile_number", mobile_numberString));
                nameValuePairs.add(new BasicNameValuePair("'user_location'", "Madhapur, Hyderabad"));
                nameValuePairs.add(new BasicNameValuePair("'user_latitude'", "17.4483"));
                nameValuePairs.add(new BasicNameValuePair("'user_longitude'", "78.3915"));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
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
                Log.e("objJsonMain", "" + json);
                Log.e("nameValuePairs", "" + nameValuePairs.toString());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }
    }
}
