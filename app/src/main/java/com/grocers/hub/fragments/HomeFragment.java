package com.grocers.hub.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CartActivity;
import com.grocers.hub.R;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

/**
 * Created by ctel-cpu-78 on 4/20/2017.
 */

public class HomeFragment extends Fragment implements ItemClickListener {

    RecyclerView categoriesRecyclerView, offerProductsRecyclerView;
    public static ImageView recyclerLayout;
    TextView locationTextView;
    GHUtil abUtil;
    Shared shared;
    RelativeLayout cartLayout;
    CategoriesListAdapter categoriesListAdapter;
    OfferProductsListAdapter offerProductsListAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;
    Integer[] icons = {R.drawable.ic_categories_black, R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs,
            R.drawable.ic_personal_care, R.drawable.ic_household_needs, R.drawable.ic_personal_care, R.drawable.ic_household_needs};
    String categories[] = {"All Categories", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household", "Personal Care", "Household"};

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

        return view;
    }

    public void init(View view) {
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);
        offerProductsRecyclerView = (RecyclerView) view.findViewById(R.id.offerProductsRecyclerView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        cartLayout = (RelativeLayout) view.findViewById(R.id.cartLayout);
        shared = new Shared(getActivity());
        abUtil = GHUtil.getInstance(getActivity());

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
        categoriesRecyclerView.setLayoutManager(mLayoutManager);
        categoriesListAdapter = new CategoriesListAdapter(getActivity(), categoryModelArrayList);
        categoriesRecyclerView.setAdapter(categoriesListAdapter);
        categoriesListAdapter.setClickListener(this);

        //products List
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        offerProductsRecyclerView.setLayoutManager(mLayoutManager1);
        offerProductsListAdapter = new OfferProductsListAdapter(getActivity(), categoryModelArrayList);
        offerProductsRecyclerView.setAdapter(offerProductsListAdapter);

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
}
