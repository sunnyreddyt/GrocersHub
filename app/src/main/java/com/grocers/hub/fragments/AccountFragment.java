package com.grocers.hub.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.grocers.hub.CartActivity;
import com.grocers.hub.EditProfileActivity;
import com.grocers.hub.LoginActivity;
import com.grocers.hub.MainActivity;
import com.grocers.hub.OrderHistoryActivity;
import com.grocers.hub.R;
import com.grocers.hub.SplashActivity;
import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ArrayList<LocationsModel> cityArrayList;
    Dialog citiesDialog;
    Context context;
    GHUtil ghUtil;
    TextView userNameTextView, loginTextView, userMobileTextView, userMailTextView, changeLocationTextView, selectedLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_duplicate, container, false);

        ghUtil = GHUtil.getInstance(getActivity());
        signOutLayout = (LinearLayout) view.findViewById(R.id.signOutLayout);
        userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        loginTextView = (TextView) view.findViewById(R.id.loginTextView);
        userMobileTextView = (TextView) view.findViewById(R.id.userMobileTextView);
        changeLocationTextView = (TextView) view.findViewById(R.id.changeLocationTextView);
        editLayout = (RelativeLayout) view.findViewById(R.id.editLayout);
        userMailTextView = (TextView) view.findViewById(R.id.userMailTextView);
        selectedLocation = (TextView) view.findViewById(R.id.selectedLocation);
        ordersLayout = (LinearLayout) view.findViewById(R.id.ordersLayout);
        loginLayout = (ScrollView) view.findViewById(R.id.loginLayout);
        logoutLayout = (RelativeLayout) view.findViewById(R.id.logoutLayout);
        context = getActivity();
        shared = new Shared(getActivity());


        changeLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocations();
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
                                clearOfflineCart();
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

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
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
        if (shared.getCity().length() > 0) {
            selectedLocation.setText(shared.getCity().substring(0, 1).toUpperCase() + shared.getCity().substring(1, shared.getCity().toString().length()));
        }
        userMobileTextView.setText(shared.getUserMobile());
    }

    public void getLocations() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<LocationsModel> loginResponseCall = service.getLocations();
        loginResponseCall.enqueue(new Callback<LocationsModel>() {
            @Override
            public void onResponse(Call<LocationsModel> call, Response<LocationsModel> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    cityArrayList = new ArrayList<LocationsModel>();

                    for (int p = 0; p < response.body().getData().size(); p++) {
                        LocationsModel locationsModel = new LocationsModel();
                        locationsModel.setCity(response.body().getData().get(p).getCity());
                        locationsModel.setPostcode(response.body().getData().get(p).getPostcode());
                        cityArrayList.add(locationsModel);
                    }
                    if (cityArrayList.size() > 0)
                        selectCity(cityArrayList);

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LocationsModel> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void selectCity(ArrayList<LocationsModel> tempCityArrayList) {
        citiesDialog = new Dialog(getActivity());
        citiesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        citiesDialog.setContentView(R.layout.dialog_cities);

        RecyclerView citiesRecyclerView = citiesDialog.findViewById(R.id.citiesRecyclerView);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        citiesRecyclerView.setLayoutManager(mLayoutManager1);
        CityListAdapter cityListAdapter = new CityListAdapter(context, tempCityArrayList);
        citiesRecyclerView.setAdapter(cityListAdapter);
        cityListAdapter.setClickListener(this);
        citiesDialog.show();
    }

    @Override
    public void onClick(int position) {
        shared.setCity(cityArrayList.get(position).getCity());
        shared.setZipCode(cityArrayList.get(position).getPostcode());
        if (citiesDialog != null) {
            citiesDialog.dismiss();
        }
        if (shared.getCity().length() > 0) {
            selectedLocation.setText(shared.getCity().substring(0, 1).toUpperCase() + shared.getCity().substring(1, shared.getCity().toString().length()));
        }
    }

    public void clearOfflineCart() {
        class ClearCartProductOffline extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {

                DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .truncate();

                return "";
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);

                shared.clearPreferences();
                Toast.makeText(getActivity(), "Signout Successful", Toast.LENGTH_SHORT).show();
                shared.setCity("Hyderabad");
                shared.setZipCode("500081");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        new ClearCartProductOffline().execute();
    }

}
