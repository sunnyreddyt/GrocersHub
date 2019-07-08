package com.grocers.hub.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
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

public class StoresFragment extends Fragment {

    RecyclerView storesRecyclerView;
    ABUtil abUtil;
    Shared shared;
    TextView storescountTextView, locationTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);

        shared = new Shared(getActivity());
        abUtil = ABUtil.getInstance(getActivity());
        storesRecyclerView = (RecyclerView) view.findViewById(R.id.storesRecyclerView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);

        locationTextView.setText(shared.getUserLocation());

        if (abUtil.isConnectingToInternet()) {
           // new GetStores().execute();
        }

        return view;
    }


    private class GetStores extends AsyncTask<String, Integer, String> {

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
                            if (jsonObjectMain.has("stores")) {

                                JSONArray storesArray = jsonObjectMain.getJSONArray("stores");

                                if (storesArray.length() > 0) {
                                   /* LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    storesRecyclerView.setLayoutManager(mLayoutManager);
                                    StoresListAdapter storesListAdapter = new StoresListAdapter(getActivity(), storesArray);
                                    storesRecyclerView.setAdapter(storesListAdapter);
                                    storescountTextView.setText(storesArray.length() + " Stores ");*/
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

            HttpPost httppost = new HttpPost(Constants.BASE_URL + "getStores");

            String json = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


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
                e.printStackTrace();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }
    }

}
