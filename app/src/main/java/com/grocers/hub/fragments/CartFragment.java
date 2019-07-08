package com.grocers.hub.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.ItemClickListenerTwo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class CartFragment extends Fragment {

    RecyclerView cartProductsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        return view;
    }


}
