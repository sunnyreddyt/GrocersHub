package com.grocers.hub.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CartActivity;
import com.grocers.hub.R;
import com.grocers.hub.adapters.AllCategoriesListAdapter;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.HomeAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
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

public class CategoriesFragment extends Fragment implements ItemClickListener {

    GHUtil ghUtil;
    RecyclerView categoriesRecyclerView;
    ArrayList<CategoryModel> categoryModelArrayList;
    AllCategoriesListAdapter allCategoriesListAdapter;
    TextView cartCountTextView;
    RelativeLayout cartLayout;
    Shared shared;
    Context context;
    ArrayList<CartResponse> cartResponseArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        init(view);

        cartCountTextView.setText("0");

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserID().length() > 0) {
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please login to view cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getCategoriesServiceCall();
        return view;
    }

    public void init(View view) {
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);
        cartLayout = (RelativeLayout) view.findViewById(R.id.cartLayout);
        cartCountTextView = (TextView) view.findViewById(R.id.cartCountTextView);
        shared = new Shared(getActivity());
        context = getActivity();
        ghUtil = GHUtil.getInstance(getActivity());
        /*// categories ArrayList
        categoryModelArrayList = new ArrayList<CategoryModel>();
        for (int p = 0; p < 9; p++) {
            CategoryModel categoryModel = new CategoryModel();
            if (p == 0) {
                categoryModel.setCategoryBackground(true);
            } else {
                categoryModel.setCategoryBackground(false);
            }
            categoryModel.setCategoryName(categories[p]);
            categoryModel.setCategoryIcon(icons[p]);
            categoryModelArrayList.add(categoryModel);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allCategoriesListAdapter = new AllCategoriesListAdapter(getActivity(), categoryModelArrayList);
        categoriesRecyclerView.setAdapter(allCategoriesListAdapter);
        allCategoriesListAdapter.setClickListener(this);*/

    }

    @Override
    public void onClick(int position) {
        /*for (int p = 0; p < categoryModelArrayList.size(); p++) {
            CategoryModel categoryModel = categoryModelArrayList.get(p);
            if (p == position) {
                categoryModel.setCategoryBackground(true);
            } else {
                categoryModel.setCategoryBackground(false);
            }
            categoryModelArrayList.set(p, categoryModel);
        }
        allCategoriesListAdapter.notifyDataSetChanged();*/
    }


    public void getCategoriesServiceCall() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CategoryModel> loginResponseCall = service.getCategories("Bearer 34s77aiqvcmc84v65s1tpnwip9dtvtqk");
        loginResponseCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    categoryModelArrayList = new ArrayList<CategoryModel>();
                    for (int p = 0; p < response.body().getChildren_data().size(); p++) {
                        categoryModelArrayList.add(response.body().getChildren_data().get(p));
                    }

                    categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    allCategoriesListAdapter = new AllCategoriesListAdapter(getActivity(), categoryModelArrayList);
                    categoriesRecyclerView.setAdapter(allCategoriesListAdapter);
                    allCategoriesListAdapter.setClickListener(CategoriesFragment.this);
                }
                getCartProductsOffline();
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                ghUtil.dismissDialog();
                //  Toast.makeText(getActivity(), "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

                int cartCount = offlineCartProductList.size();
                if (cartCount > 0) {
                    cartCountTextView.setText(String.valueOf(cartCount));
                } else {
                    cartCountTextView.setText("0");
                }

            }
        }
        new GetCartProductOffline().execute();
    }

}
