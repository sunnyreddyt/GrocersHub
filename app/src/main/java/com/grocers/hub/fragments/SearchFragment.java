package com.grocers.hub.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.MainActivity;
import com.grocers.hub.R;
import com.grocers.hub.adapters.SearchProductsAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.ProductsResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class SearchFragment extends Fragment {

    EditText searchEditText;
    GHUtil ghUtil;
    Shared shared;
    Context context;
    RecyclerView productsRecyclerView;
    SearchProductsAdapter searchProductsAdapter;
    ArrayList<ProductsResponse> productsResponseArrayList;
    ImageView closeImageView, backImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        context = getActivity();
        shared = new Shared(getActivity());
        ghUtil = GHUtil.getInstance(getActivity());
        productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        backImageView = (ImageView) view.findViewById(R.id.backImageView);
        closeImageView = (ImageView) view.findViewById(R.id.closeImageView);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditText.setText("");
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText.hasFocus()) {
                    ghUtil.hideKeyboard(getActivity());
                    searchEditText.clearFocus();
                } else {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.loadHomeFragment();
                    }
                }
            }
        });

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
                if (s.length() > 1 /*&& s.length() % 2 == 0*/) {
                    // ghUtil.hideKeyboard(getActivity());
                    getProductsServiceCall(s.toString());
                } else if (s.length() == 0) {
                    productsRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        if (!(ghUtil.isConnectingToInternet())) {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        searchEditText.requestFocus();
        showKeyboard(searchEditText);

        return view;
    }


    public void getProductsServiceCall(String key) {
        //ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<ProductsResponse> loginResponseCall = service.search(key, shared.getZipCode());
        loginResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                // ghUtil.dismissDialog();
                if (response.code() == 200) {
                    productsResponseArrayList = new ArrayList<ProductsResponse>();
                    productsResponseArrayList = response.body().getProducts();
                    if (productsResponseArrayList != null && productsResponseArrayList.size() > 0) {
                        //products List
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        searchProductsAdapter = new SearchProductsAdapter(context, productsResponseArrayList);
                        productsRecyclerView.setAdapter(searchProductsAdapter);

                        // searchEditText.requestFocus();
                        // showKeyboard(searchEditText);
                        // searchEditText.setSelection(searchEditText.getText().toString().length());
                    } else {
                        productsRecyclerView.setVisibility(View.GONE);
                        Toast.makeText(context, "No products available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager inputManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            // inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
