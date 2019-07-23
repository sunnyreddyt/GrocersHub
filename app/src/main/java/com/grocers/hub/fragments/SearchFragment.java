package com.grocers.hub.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grocers.hub.R;
import com.grocers.hub.constants.Constants;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.utils.GHUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class SearchFragment extends Fragment {

    EditText searchEditText;
    GHUtil abUtil;
    Shared shared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        shared = new Shared(getActivity());
        abUtil = GHUtil.getInstance(getActivity());
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);


        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    new GetProducts().execute();
                }
            }
        });


        return view;
    }


    public class GetProducts extends AsyncTask<String, Integer, String> {

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
                            if (jsonObjectMain.has("storeProducts")) {

                                JSONArray storeProducts = jsonObjectMain.getJSONArray("storeProducts");

                                if (storeProducts.length() > 0) {

/*                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(StoreDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
                                    productsRecyclerView.setLayoutManager(mLayoutManager);
                                    StoreProductsAdapter storeProductsAdapter = new StoreProductsAdapter(StoreDetailsActivity.this, storeProducts);
                                    productsRecyclerView.setAdapter(storeProductsAdapter);
                                    storeProductsAdapter.setClickListener(StoreDetailsActivity.this);*/

                                }
                            }

                        } else {

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

            HttpPost httppost = new HttpPost(Constants.BASE_URL + "getStoreProducts");

            String json = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("key", searchEditText.getText().toString().trim()));


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

            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

            }

            return json;
        }
    }

}
