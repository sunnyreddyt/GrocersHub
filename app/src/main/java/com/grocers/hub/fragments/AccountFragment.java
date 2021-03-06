package com.grocers.hub.fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.EditProfileActivity;
import com.grocers.hub.LoginActivity;
import com.grocers.hub.OrderHistoryActivity;
import com.grocers.hub.R;
import com.grocers.hub.SuggestionsActivity;
import com.grocers.hub.SupportActivity;
import com.grocers.hub.WebViewActivity;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class AccountFragment extends Fragment implements ItemClickListener {

    private LinearLayout signOutLayout, ordersLayout, rateUsLinearLayout, feedbackLinearLayout,
            aboutUsLinearLayout, wtsappLayout, shareLayout, tncLayout, privacyPolicyLayout,
            suggestionLayout, supportLayout, contactUsLayout;
    private Shared shared;
    private LinearLayout loginLayout;
    private LinearLayout logoutLayout;
    private RelativeLayout editLayout;
    private String[] cities = {"Hyderabad", "Khammam", "Mahabubnagar", "Karimnagar", "Secunderabad", "Kurnool", "Tirupathi", "Adilabad", "Vijayawada", "Vizag"};
    private ArrayList<LocationsModel> cityArrayList;
    private Dialog citiesDialog;
    private Context context;
    private GHUtil ghUtil;
    private TextView versionNameTextView, userNameTextView, loginTextView, userMobileTextView, userMailTextView, changeLocationTextView, selectedLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_duplicate, container, false);

        ghUtil = GHUtil.getInstance(getActivity());
        contactUsLayout = (LinearLayout) view.findViewById(R.id.contactUsLayout);
        supportLayout = (LinearLayout) view.findViewById(R.id.supportLayout);
        signOutLayout = (LinearLayout) view.findViewById(R.id.signOutLayout);
        userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        versionNameTextView = (TextView) view.findViewById(R.id.versionNameTextView);
        loginTextView = (TextView) view.findViewById(R.id.loginTextView);
        userMobileTextView = (TextView) view.findViewById(R.id.userMobileTextView);
        changeLocationTextView = (TextView) view.findViewById(R.id.changeLocationTextView);
        editLayout = (RelativeLayout) view.findViewById(R.id.editLayout);
        userMailTextView = (TextView) view.findViewById(R.id.userMailTextView);
        selectedLocation = (TextView) view.findViewById(R.id.selectedLocation);
        rateUsLinearLayout = (LinearLayout) view.findViewById(R.id.rateUsLinearLayout);
        ordersLayout = (LinearLayout) view.findViewById(R.id.ordersLayout);
        loginLayout = (LinearLayout) view.findViewById(R.id.loginLayout);
        logoutLayout = (LinearLayout) view.findViewById(R.id.logoutLayout);
        wtsappLayout = (LinearLayout) view.findViewById(R.id.wtsappLayout);
        aboutUsLinearLayout = (LinearLayout) view.findViewById(R.id.aboutUsLinearLayout);
        feedbackLinearLayout = (LinearLayout) view.findViewById(R.id.feedbackLinearLayout);
        shareLayout = (LinearLayout) view.findViewById(R.id.shareLayout);
        tncLayout = (LinearLayout) view.findViewById(R.id.tncLayout);
        privacyPolicyLayout = (LinearLayout) view.findViewById(R.id.privacyPolicyLayout);
        suggestionLayout = (LinearLayout) view.findViewById(R.id.suggestionLayout);
        context = getActivity();
        shared = new Shared(getActivity());

        changeLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocations();
            }
        });

        wtsappLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + "+91 8341836105";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong please try after sometime", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        supportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SupportActivity.class));
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        aboutUsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://www.grocershub.in/about-us");
                startActivity(intent);
            }
        });

        tncLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://www.grocershub.in/terms-conditions");
                startActivity(intent);
            }
        });

        privacyPolicyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://www.grocershub.in/privacy-policy-cookie-restriction-mode");
                startActivity(intent);
            }
        });

        suggestionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuggestionsActivity.class);
                intent.putExtra("type", "suggestion");
                startActivity(intent);
            }
        });

        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuggestionsActivity.class);
                intent.putExtra("type", "contactus");
                startActivity(intent);
            }
        });

        feedbackLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateOnPlaystore();
            }
        });

        rateUsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateOnPlaystore();
            }
        });

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            versionNameTextView.setText("Version: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

      /*userNameTextView.setText(shared.getUserName());
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

    public void rateOnPlaystore() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public void shareApp() {
        Intent intentInvite = new Intent(Intent.ACTION_SEND);
        intentInvite.setType("text/plain");
        intentInvite.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
        startActivity(Intent.createChooser(intentInvite, "Share using"));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shared.getUserName().length() > 0) {
            loginLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(GONE);

            signOutLayout.setVisibility(View.VISIBLE);
            ordersLayout.setVisibility(View.VISIBLE);
            wtsappLayout.setVisibility(View.VISIBLE);
        } else {
            loginLayout.setVisibility(GONE);
            editLayout.setVisibility(GONE);
            logoutLayout.setVisibility(View.VISIBLE);
            signOutLayout.setVisibility(GONE);
            ordersLayout.setVisibility(GONE);
            wtsappLayout.setVisibility(GONE);
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
                    if (cityArrayList.size() == 1) {
                        shared.setCity(cityArrayList.get(0).getCity());
                        shared.setZipCode(cityArrayList.get(0).getPostcode());
                        if (shared.getCity().length() > 0) {
                            selectedLocation.setText(shared.getCity().substring(0, 1).toUpperCase() + shared.getCity().substring(1, shared.getCity().toString().length()));
                        }
                        Toast.makeText(context, "Currently we are serving in only one location", Toast.LENGTH_LONG).show();
                    } else if (cityArrayList.size() > 0) {
                        selectCity(cityArrayList);
                    }

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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        new ClearCartProductOffline().execute();
    }

}
