package com.grocers.hub.fragments;

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
import com.grocers.hub.adapters.AllCategoriesListAdapter;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.models.CategoryModel;
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

public class CategoriesFragment extends Fragment implements ItemClickListener {

    GHUtil ghUtil;
    RecyclerView categoriesRecyclerView;
    ArrayList<CategoryModel> categoryModelArrayList;
    Integer[] icons = {R.drawable.ic_categories_black, R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs,
            R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs};
    String categories[] = {"All Categories", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household"};
    AllCategoriesListAdapter allCategoriesListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        init(view);

        getCategoriesServiceCall();
        return view;
    }

    public void init(View view) {
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);
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
        Call<CategoryModel> loginResponseCall = service.getCategories();
        loginResponseCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                ghUtil.dismissDialog();
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

                    categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    allCategoriesListAdapter = new AllCategoriesListAdapter(getActivity(), categoryModelArrayList);
                    categoriesRecyclerView.setAdapter(allCategoriesListAdapter);
                    allCategoriesListAdapter.setClickListener(CategoriesFragment.this);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(getActivity(), "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
