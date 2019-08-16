package com.grocers.hub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.ViewPagerAdapter;
import com.grocers.hub.adapters.HomeAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        ghUtil = GHUtil.getInstance(getActivity());
        shared = new Shared(getActivity());
        context = getActivity();
        getHomeDetailsServiceCall();

        return view;
    }


    public void getHomeDetailsServiceCall() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<HomeResponse> loginResponseCall = service.getHomeDetails("hyderabad");
        loginResponseCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {

                    LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    homeRecyclerView.setLayoutManager(mLayoutManager1);
                    HomeAdapter homeAdapter = new HomeAdapter(getActivity(), response.body().getCategoryProducts());
                    homeRecyclerView.setAdapter(homeAdapter);

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
