package com.grocers.hub.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.grocers.hub.CartActivity;
import com.grocers.hub.R;
import com.grocers.hub.ViewPagerAdapter;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.CityListAdapter;
import com.grocers.hub.adapters.HomeAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.adapters.OnCartChangeListener;
import com.grocers.hub.adapters.OnCategoryClickListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ctel-cpu-78 on 4/20/2017.
 */

public class HomeFragment extends Fragment implements ItemClickListener, OnCategoryClickListener, OnCartChangeListener {

    private RecyclerView categoriesRecyclerView, homeRecyclerView;
    public static ImageView recyclerLayout;
    private TextView locationTextView, cartCountTextView;
    private GHUtil ghUtil;
    private Shared shared;
    private RelativeLayout cartLayout;
    private CategoriesListAdapter categoriesListAdapter;
    private OfferProductsListAdapter offerProductsListAdapter;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private AutoScrollViewPager viewPager;
    private Context context;
    private ArrayList<LocationsModel> cityArrayList;
    private LinearLayout sliderDotspanel, locationLayout;
    private int dotscount;
    private ImageView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    private Dialog citiesDialog;
    private HomeAdapter homeAdapter;
    ArrayList<CartResponse> cartResponseArrayList = new ArrayList<>();
    private ArrayList<HomeResponse> homeResponseArrayList = new ArrayList<HomeResponse>();
    private ArrayList<String> skuListTem;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        cartCountTextView.setText("0");

        if (shared.getCity().length() > 0) {
            locationTextView.setText(shared.getCity().substring(0, 1).toUpperCase() + shared.getCity().substring(1, shared.getCity().toString().length()));
        }

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocations();
            }
        });

        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocations();
            }
        });

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (shared.getUserID().length() > 0) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                /*} else {
                    Toast.makeText(getActivity(), "Please login to view cart", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            getCategoriesServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void init(View view) {
        context = getActivity();
        categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesRecyclerView);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        cartLayout = (RelativeLayout) view.findViewById(R.id.cartLayout);
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.viewPager);
        cartCountTextView = (TextView) view.findViewById(R.id.cartCountTextView);
        locationLayout = (LinearLayout) view.findViewById(R.id.locationLayout);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
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
        shared.setCity(cityArrayList.get(position).getCity());
        shared.setZipCode(cityArrayList.get(position).getPostcode());
        locationTextView.setText(shared.getCity());
        if (citiesDialog != null) {
            citiesDialog.dismiss();
        }
        if (ghUtil.isConnectingToInternet()) {
            getCategoriesServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
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
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getHomeDetailsServiceCall() {
        ghUtil.showDialog(getActivity());
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<HomeResponse> loginResponseCall = service.getHomeDetails(shared.getZipCode());
        loginResponseCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                ghUtil.dismissDialog();

                if (response.code() == 200) {

                    viewPager.startAutoScroll();
                    viewPager.setInterval(3000);
                    viewPager.setCycle(true);
                    viewPager.setStopScrollWhenTouch(true);
                    viewPagerAdapter = new ViewPagerAdapter(getActivity(), response.body().getBanners());
                    viewPager.setAdapter(viewPagerAdapter);
                    //setUpViewPager();

                    homeResponseArrayList = response.body().getCategoryProducts();

                    getCartProductsOffline();

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

    public void setUpViewPager() {
        try {
            dotscount = viewPagerAdapter.getCount();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {
                dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);
                sliderDotspanel.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    if (cityArrayList.size() > 0)
                        selectCity(cityArrayList);

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
    public void onCategoryClick(int position) {
        if (position == 0) {
            ((MainActivity) getActivity()).loadCategoriesFragment();
        } else {
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

                    int cartCount = response.body().getItems().size();
                    if (cartCount > 0) {
                        cartResponseArrayList = response.body().getItems();
                        cartCountTextView.setText(String.valueOf(cartCount));
                    } else {
                        cartCountTextView.setText("0");
                    }
                } else {
                    //  Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
                getHomeDetailsServiceCall();
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                ghUtil.dismissDialog();
                cartResponseArrayList = new ArrayList<CartResponse>();
                getHomeDetailsServiceCall();

                //  Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public void onCartChange(int count) {
        cartCountTextView.setText(String.valueOf(count));
    }

    public void updateCartCount(int cartCount) {
        cartCountTextView.setText(String.valueOf(cartCount));
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

                LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                homeRecyclerView.setLayoutManager(mLayoutManager1);
                homeAdapter = new HomeAdapter(getActivity(), homeResponseArrayList, HomeFragment.this);
                homeRecyclerView.setAdapter(homeAdapter);
                homeAdapter.setCartListener(HomeFragment.this);

            }
        }
        new GetCartProductOffline().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartProductsCount();
    }

    public void updateCartProductsCount() {
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

                skuListTem = new ArrayList<String>();
                for (int g = 0; g < offlineCartProductList.size(); g++) {
                    skuListTem.add(offlineCartProductList.get(g).getSkuID());
                }

                if (homeAdapter != null && homeResponseArrayList != null) {
                    for (int a = 0; a < homeResponseArrayList.size(); a++) {
                        for (int b = 0; b < homeResponseArrayList.get(a).getProducts().size(); b++) {

                            if (homeResponseArrayList.get(a).getProducts().get(b).getOptions() != null && homeResponseArrayList.get(a).getProducts().get(b).getOptions().size() > 0) {
                                for (int m = 0; m < homeResponseArrayList.get(a).getProducts().get(b).getOptions().size(); m++) {
                                    if (skuListTem.contains(homeResponseArrayList.get(a).getProducts().get(b).getOptions().get(m).getSku())) {
                                        for (int t = 0; t < skuListTem.size(); t++) {
                                            homeResponseArrayList.get(a).getProducts().get(b).getOptions().get(m).setCartQuantity(offlineCartProductList.get(t).getQty());
                                        }
                                    } else {
                                        homeResponseArrayList.get(a).getProducts().get(b).getOptions().get(m).setCartQuantity(0);
                                    }
                                }
                            } else {
                                if (skuListTem.contains(homeResponseArrayList.get(a).getProducts().get(b).getSku())) {
                                    for (int t = 0; t < skuListTem.size(); t++) {
                                        homeResponseArrayList.get(a).getProducts().get(b).setCartQuantity(offlineCartProductList.get(t).getQty());
                                    }
                                } else {
                                    homeResponseArrayList.get(a).getProducts().get(b).setCartQuantity(0);
                                }
                            }
                        }
                    }
                    homeAdapter.notifyDataSetChanged();
                }
            }
        }
        new GetCartProductOffline().execute();
    }

}
