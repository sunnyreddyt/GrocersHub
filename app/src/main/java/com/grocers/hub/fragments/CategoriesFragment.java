package com.grocers.hub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.adapters.AllCategoriesListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class CategoriesFragment extends Fragment implements ItemClickListener {

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


        return view;
    }

    public void init(View view) {
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);


        // categories ArrayList
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
        allCategoriesListAdapter.setClickListener(this);
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
        allCategoriesListAdapter.notifyDataSetChanged();
    }

}
