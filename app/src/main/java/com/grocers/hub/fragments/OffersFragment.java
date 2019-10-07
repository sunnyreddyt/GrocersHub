package com.grocers.hub.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.adapters.OffersAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class OffersFragment extends Fragment {

    RecyclerView homeRecyclerView;
    Context context;
    Shared shared;
    GHUtil ghUtil;
    private ArrayList<HomeResponse> homeResponseArrayList = new ArrayList<HomeResponse>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        ghUtil = GHUtil.getInstance(getActivity());
        shared = new Shared(getActivity());
        context = getActivity();

        if ((ghUtil.isConnectingToInternet())) {
            getHomeDetailsServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    public void getHomeDetailsServiceCall() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<HomeResponse> loginResponseCall = service.getOffersDetails(shared.getZipCode());
        loginResponseCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    homeResponseArrayList = response.body().getCategoryProducts();
                    getCartProductsOffline();
                } else {
                    // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                //  Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public void getCartProductsServiceCall() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                ghUtil.dismissDialog();
                cartResponseArrayList = new ArrayList<CartResponse>();
                if (response.code() == 200) {
                    cartResponseArrayList = response.body().getItems();
                } else {
                    //    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
                getHomeDetailsServiceCall();
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                cartResponseArrayList = new ArrayList<CartResponse>();
                getHomeDetailsServiceCall();

                //  Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void getCartProductsOffline() {
        class GetCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {

                List<OfflineCartProduct> offlineCartProductArrayList = new ArrayList<OfflineCartProduct>();
                offlineCartProductArrayList = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAllProducts();

                return offlineCartProductArrayList;
            }

            @Override
            protected void onPostExecute(List<OfflineCartProduct> offlineCartProductList) {
                super.onPostExecute(offlineCartProductList);

                /*int cartCount = offlineCartProductList.size();
                if (cartCount > 0) {
                    cartCountTextView.setText(String.valueOf(cartCount));
                } else {
                    cartCountTextView.setText("0");
                }*/

                for (int p = 0; p < homeResponseArrayList.size(); p++) {
                    for (int k = 0; k < homeResponseArrayList.get(p).getProducts().size(); k++) {
                        for (int q = 0; q < offlineCartProductList.size(); q++) {
                            if (homeResponseArrayList.get(p).getProducts().get(k).getOptions() != null && homeResponseArrayList.get(p).getProducts().get(k).getOptions().size() > 0) {
                                for (int r = 0; r < homeResponseArrayList.get(p).getProducts().get(k).getOptions().size(); r++) {
                                    if (offlineCartProductList.get(q).getSkuID().equalsIgnoreCase(homeResponseArrayList.get(p).getProducts().get(k).getOptions().get(r).getSku())) {
                                        homeResponseArrayList.get(p).getProducts().get(k).getOptions().get(r).setCartQuantity(offlineCartProductList.get(q).getQty());
                                    }
                                }
                            } else if (offlineCartProductList.get(q).getSkuID().equalsIgnoreCase(homeResponseArrayList.get(p).getProducts().get(k).getSku())) {
                                homeResponseArrayList.get(p).getProducts().get(k).setCartQuantity(offlineCartProductList.get(q).getQty());
                            }
                        }
                    }
                }

//                LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                homeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                OffersAdapter homeAdapter = new OffersAdapter(getActivity(), homeResponseArrayList, OffersFragment.this);
                homeRecyclerView.setAdapter(homeAdapter);
            }
        }
        new GetCartProductOffline().execute();
    }

}
