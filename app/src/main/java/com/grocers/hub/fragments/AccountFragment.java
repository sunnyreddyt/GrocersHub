package com.grocers.hub.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.LoginActivity;
import com.grocers.hub.OrderHistoryActivity;
import com.grocers.hub.R;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;

import java.util.ArrayList;

import static android.view.View.GONE;


/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class AccountFragment extends Fragment implements ItemClickListener {

    LinearLayout signOutLayout, ordersLayout;
    Shared shared;
    ScrollView loginLayout;
    RelativeLayout logoutLayout;
    RelativeLayout editLayout;
    String[] cities = {"Hyderabad", "Khammam", "Mahabubnagar", "Karimnagar", "Secunderabad", "Kurnool", "Tirupathi", "Adilabad", "Vijayawada", "Vizag"};
    ArrayList<City> cityArrayList;
    Dialog citiesDialog;
    TextView userNameTextView, loginTextView, userNumberTextView, userMailTextView, changeLocationTextView, selectedLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_duplicate, container, false);

        signOutLayout = (LinearLayout) view.findViewById(R.id.signOutLayout);
        userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        loginTextView = (TextView) view.findViewById(R.id.loginTextView);
        userNumberTextView = (TextView) view.findViewById(R.id.userNumberTextView);
        changeLocationTextView = (TextView) view.findViewById(R.id.changeLocationTextView);
        editLayout = (RelativeLayout) view.findViewById(R.id.editLayout);
        userMailTextView = (TextView) view.findViewById(R.id.userMailTextView);
        selectedLocation = (TextView) view.findViewById(R.id.selectedLocation);
        ordersLayout = (LinearLayout) view.findViewById(R.id.ordersLayout);
        loginLayout = (ScrollView) view.findViewById(R.id.loginLayout);
        logoutLayout = (RelativeLayout) view.findViewById(R.id.logoutLayout);
        shared = new Shared(getActivity());

        selectedLocation.setText(shared.getCity());

        changeLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCity();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


       /* userNameTextView.setText(shared.getUserName());
        userNumberTextView.setText(shared.getUserMobile());
        userMailTextView.setText(shared.getUserEmail());*/

        signOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                shared.clearPreferences();
                                Toast.makeText(getActivity(), "Signout Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        })
                        .show();

            }
        });

        ordersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shared.getUserName().length() > 0) {
            loginLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(GONE);
        } else {
            loginLayout.setVisibility(GONE);
            editLayout.setVisibility(GONE);
            logoutLayout.setVisibility(View.VISIBLE);
        }

        userNameTextView.setText(shared.getUserName());
        userMailTextView.setText(shared.getUserEmail());
    }


    public void selectCity() {
        citiesDialog = new Dialog(getActivity());
        citiesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        citiesDialog.setContentView(R.layout.dialog_cities);

        cityArrayList = new ArrayList<City>();
        for (int p = 0; p < cities.length; p++) {
            City city = new City();
            city.setId(p);
            city.setCity_name(cities[p]);
            cityArrayList.add(city);
        }

        RecyclerView citiesRecyclerView = citiesDialog.findViewById(R.id.citiesRecyclerView);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        citiesRecyclerView.setLayoutManager(mLayoutManager1);
        CityListAdapter cityListAdapter = new CityListAdapter(getActivity(), cityArrayList);
        citiesRecyclerView.setAdapter(cityListAdapter);
        cityListAdapter.setClickListener(AccountFragment.this);
        citiesDialog.show();
    }

    @Override
    public void onClick(int position) {
        shared.setCity(cityArrayList.get(position).getCity_name());
        if (citiesDialog != null) {
            citiesDialog.dismiss();
        }
        selectedLocation.setText(shared.getCity());
    }
}
