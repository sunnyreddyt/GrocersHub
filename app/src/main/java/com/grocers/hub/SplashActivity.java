package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunnyreddy on 26/06/19.
 */

public class SplashActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback, ItemClickListener {

    TextView appNameTextView, locationTextView;
    ImageView locationImageView;
    int count = 0;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    Shared shared;
    String[] cities = {"Hyderabad", "Khammam", "Mahabubnagar", "Karimnagar", "Secunderabad", "Kurnool", "Tirupathi", "Adilabad", "Vijayawada", "Vizag"};
    ArrayList<LocationsModel> cityArrayList;
    Dialog citiesDialog;
    Context context;

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
        animate();
    }

    @Override
    public void PermissionDenied(int request_code) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtils.check_permission(permissions, "Allow permission to have best Experience", 1);
        } else {
            animate();
        }
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtils.check_permission(permissions, "Allow permission to have best Experience", 1);
        } else {
            animate();
        }

        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        shared = new Shared(SplashActivity.this);
        permissionUtils = new PermissionUtils(SplashActivity.this);
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);

        appNameTextView = (TextView) findViewById(R.id.appNameTextView);
        locationImageView = (ImageView) findViewById(R.id.locationImageView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtils.check_permission(permissions, "Allow permissions to have best Experience", 1);
        } else {
            //animate();
            animation();
        }

    }

    public void animate() {
        animation();
        Animation animation = new TranslateAnimation(0, 0, -5, 5);
        animation.setDuration(800);
        animation.setRepeatCount(-2);
        animation.setRepeatMode(Animation.RELATIVE_TO_PARENT);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        locationImageView.setAnimation(animation);

        locationTextView.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in));
    }

    public void animation() {

        count++;
        final Handler handle = new Handler();
        final int delay = 2000; //milliseconds
        handle.postDelayed(new Runnable() {
            public void run() {
                if (count == 2) {
                    mainCode();
                } else {
                    animation();
                }
            }
        }, delay);

        //appNameTextView.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.pulse));

    }

    public void mainCode() {
        if (shared.getCity() != null && shared.getCity().length() > 0) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            getLocations();
        }
    }

    public void selectCity(ArrayList<LocationsModel> tempCityArrayList) {
        citiesDialog = new Dialog(SplashActivity.this);
        citiesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        citiesDialog.setContentView(R.layout.dialog_cities);

        RecyclerView citiesRecyclerView = citiesDialog.findViewById(R.id.citiesRecyclerView);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(SplashActivity.this, RecyclerView.VERTICAL, false);
        citiesRecyclerView.setLayoutManager(mLayoutManager1);
        CityListAdapter cityListAdapter = new CityListAdapter(SplashActivity.this, tempCityArrayList);
        citiesRecyclerView.setAdapter(cityListAdapter);
        cityListAdapter.setClickListener(this);
        citiesDialog.show();
    }

    @Override
    public void onClick(int position) {
        shared.setCity(cityArrayList.get(position).getCity());
        if (citiesDialog != null) {
            citiesDialog.dismiss();
        }
        mainCode();
    }


    public void getLocations() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<LocationsModel> loginResponseCall = service.getLocations();
        loginResponseCall.enqueue(new Callback<LocationsModel>() {
            @Override
            public void onResponse(Call<LocationsModel> call, Response<LocationsModel> response) {
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
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
