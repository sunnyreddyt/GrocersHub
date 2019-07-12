package com.grocers.hub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grocers.hub.fragments.AccountFragment;
import com.grocers.hub.fragments.CartFragment;
import com.grocers.hub.fragments.HomeFragment;
import com.grocers.hub.fragments.SearchFragment;
import com.grocers.hub.fragments.CategoriesFragment;


public class MainActivity extends AppCompatActivity {

    LinearLayout homeLayout, searchLayout, offersLayout, accountLayout, categoriesLayout;
    ImageView homeImageView, searchImageView, offersImageView, accountImageView, categoriesImageView;
    TextView homeTextview, searchTextview, offersTextView, accountTextview, categoriesTextview;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeLayout = (LinearLayout) findViewById(R.id.homeLayout);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        offersLayout = (LinearLayout) findViewById(R.id.offersLayout);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        categoriesLayout = (LinearLayout) findViewById(R.id.categoriesLayout);

        homeImageView = (ImageView) findViewById(R.id.homeImageView);
        searchImageView = (ImageView) findViewById(R.id.searchImageView);
        offersImageView = (ImageView) findViewById(R.id.offersImageView);
        accountImageView = (ImageView) findViewById(R.id.accountImageView);
        categoriesImageView = (ImageView) findViewById(R.id.categoriesImageView);

        homeTextview = (TextView) findViewById(R.id.homeTextview);
        searchTextview = (TextView) findViewById(R.id.searchTextview);
        offersTextView = (TextView) findViewById(R.id.offersTextView);
        accountTextview = (TextView) findViewById(R.id.accountTextview);
        categoriesTextview = (TextView) findViewById(R.id.categoriesTextview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            homeImageView.setImageResource(R.drawable.ic_home);
            searchImageView.setImageResource(R.drawable.ic_search_black);
            categoriesImageView.setImageResource(R.drawable.ic_categories_black);
            offersImageView.setImageResource(R.drawable.ic_offers_black);
            accountImageView.setImageResource(R.drawable.ic_account_black);


            homeTextview.setTextColor(Color.parseColor("#01d365"));
            searchTextview.setTextColor(Color.parseColor("#000000"));
            categoriesTextview.setTextColor(Color.parseColor("#000000"));
            offersTextView.setTextColor(Color.parseColor("#000000"));
            accountTextview.setTextColor(Color.parseColor("#000000"));

            fragment = new HomeFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.mainFramelayout, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commitAllowingStateLoss();


        }


        categoriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new CategoriesFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainFramelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();


                homeImageView.setImageResource(R.drawable.ic_home_black);
                searchImageView.setImageResource(R.drawable.ic_search_black);
                categoriesImageView.setImageResource(R.drawable.ic_category);
                offersImageView.setImageResource(R.drawable.ic_offers_black);
                accountImageView.setImageResource(R.drawable.ic_account_black);


                homeTextview.setTextColor(Color.parseColor("#000000"));
                searchTextview.setTextColor(Color.parseColor("#000000"));
                categoriesTextview.setTextColor(Color.parseColor("#01d365"));
                offersTextView.setTextColor(Color.parseColor("#000000"));
                accountTextview.setTextColor(Color.parseColor("#000000"));


            }
        });


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new HomeFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainFramelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();

                homeImageView.setImageResource(R.drawable.ic_home);
                searchImageView.setImageResource(R.drawable.ic_search_black);
                categoriesImageView.setImageResource(R.drawable.ic_categories_black);
                offersImageView.setImageResource(R.drawable.ic_offers_black);
                accountImageView.setImageResource(R.drawable.ic_account_black);


                homeTextview.setTextColor(Color.parseColor("#01d365"));
                searchTextview.setTextColor(Color.parseColor("#000000"));
                categoriesTextview.setTextColor(Color.parseColor("#000000"));
                offersTextView.setTextColor(Color.parseColor("#000000"));
                accountTextview.setTextColor(Color.parseColor("#000000"));


            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new SearchFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainFramelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();

                homeImageView.setImageResource(R.drawable.ic_home_black);
                searchImageView.setImageResource(R.drawable.ic_search);
                categoriesImageView.setImageResource(R.drawable.ic_categories_black);
                offersImageView.setImageResource(R.drawable.ic_offers_black);
                accountImageView.setImageResource(R.drawable.ic_account_black);

                homeTextview.setTextColor(Color.parseColor("#000000"));
                searchTextview.setTextColor(Color.parseColor("#01d365"));
                categoriesTextview.setTextColor(Color.parseColor("#000000"));
                offersTextView.setTextColor(Color.parseColor("#000000"));
                accountTextview.setTextColor(Color.parseColor("#000000"));


            }
        });

        offersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new CartFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainFramelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();


                homeImageView.setImageResource(R.drawable.ic_home_black);
                searchImageView.setImageResource(R.drawable.ic_search_black);
                categoriesImageView.setImageResource(R.drawable.ic_categories_black);
                offersImageView.setImageResource(R.drawable.ic_offers);
                accountImageView.setImageResource(R.drawable.ic_account_black);

                homeTextview.setTextColor(Color.parseColor("#000000"));
                searchTextview.setTextColor(Color.parseColor("#000000"));
                categoriesTextview.setTextColor(Color.parseColor("#000000"));
                offersTextView.setTextColor(Color.parseColor("#01d365"));
                accountTextview.setTextColor(Color.parseColor("#000000"));


            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new AccountFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainFramelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();

                homeImageView.setImageResource(R.drawable.ic_home_black);
                searchImageView.setImageResource(R.drawable.ic_search_black);
                categoriesImageView.setImageResource(R.drawable.ic_categories_black);
                offersImageView.setImageResource(R.drawable.ic_offers_black);
                accountImageView.setImageResource(R.drawable.ic_account);


                homeTextview.setTextColor(Color.parseColor("#000000"));
                searchTextview.setTextColor(Color.parseColor("#000000"));
                categoriesTextview.setTextColor(Color.parseColor("#000000"));
                offersTextView.setTextColor(Color.parseColor("#000000"));
                accountTextview.setTextColor(Color.parseColor("#01d365"));


            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
