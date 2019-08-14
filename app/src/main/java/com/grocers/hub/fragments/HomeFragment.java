package com.grocers.hub.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CartActivity;
import com.grocers.hub.R;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.HomeAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ctel-cpu-78 on 4/20/2017.
 */

public class HomeFragment extends Fragment implements ItemClickListener {

    RecyclerView categoriesRecyclerView, homeRecyclerView;
    public static ImageView recyclerLayout;
    TextView locationTextView;
    GHUtil ghUtil;
    Shared shared;
    RelativeLayout cartLayout;
    CategoriesListAdapter categoriesListAdapter;
    OfferProductsListAdapter offerProductsListAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;
    /* Integer[] icons = {R.drawable.ic_categories_black, R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs,
             R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs};
     String categories[] = {"All Categories", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household"};
     */
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), LocationChangeActivity.class);
                startActivityForResult(intent, 1);*/
            }
        });

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        getCategoriesServiceCall();

        return view;
    }

    public void init(View view) {
        context = getActivity();
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        cartLayout = (RelativeLayout) view.findViewById(R.id.cartLayout);
        shared = new Shared(getActivity());
        ghUtil = GHUtil.getInstance(getActivity());

    }

    public static void alpha(int alphaValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            recyclerLayout.setImageAlpha(alphaValue);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            String location = data.getStringExtra("location");

            if (location.length() > 0) {
                locationTextView.setText(location);
            }
        }
    }

    @Override
    public void onClick(int position) {
        for (int p = 0; p < categoryModelArrayList.size(); p++) {
            CategoryModel categoryModel = categoryModelArrayList.get(p);
            if (p == position) {
                categoryModel.setCategoryBackground(true);
            } else {
                categoryModel.setCategoryBackground(false);
            }
            categoryModelArrayList.set(p, categoryModel);
        }
        categoriesListAdapter.notifyDataSetChanged();
    }


    public void getCategoriesServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CategoryModel> loginResponseCall = service.getCategories();
        loginResponseCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                if (response.code() == 200) {

                    categoryModelArrayList = new ArrayList<CategoryModel>();
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setId(0);
                    categoryModel.setCategoryBackground(true);
                    categoryModel.setName("All Categories");
                    categoryModelArrayList.add(categoryModel);

                    for (int p = 0; p < response.body().getChildren_data().size(); p++) {
                        categoryModelArrayList.add(response.body().getChildren_data().get(p));
                    }

                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    categoriesRecyclerView.setLayoutManager(mLayoutManager);
                    categoriesListAdapter = new CategoriesListAdapter(getActivity(), categoryModelArrayList);
                    categoriesRecyclerView.setAdapter(categoriesListAdapter);
                    categoriesListAdapter.setClickListener(HomeFragment.this);

                    ghUtil.setcategoryModel(response.body());

                    getHomeDetailsServiceCall();

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getHomeDetailsServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<HomeResponse> loginResponseCall = service.getHomeDetails("hyderabad");
        loginResponseCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
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
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
