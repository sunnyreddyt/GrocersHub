package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.adapters.OnProductClickListener;
import com.grocers.hub.adapters.ProductsAdapter;
import com.grocers.hub.adapters.SubCategoriesAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.fragments.HomeFragment;
import com.grocers.hub.models.AddToCartOptionRequest;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.ProductsResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsActivity extends AppCompatActivity implements ItemClickListener, OnProductClickListener {

    RecyclerView productsRecyclerView;
    ProductsAdapter productsAdapter;
    RelativeLayout cartLayout;
    ImageView backImageView;
    Intent intent;
    CategoryModel categoryModel;
    String categoryID;
    GHUtil ghUtil;
    Shared shared;
    ArrayList<CategoryModel> categoryModelArrayList;
    ArrayList<ProductsResponse> productsResponseArrayList;
    RecyclerView subCategoriesRecyclerView;
    SubCategoriesAdapter subCategoriesAdapter;
    String selectedSubCategoryId = "";
    Context context;
    String categoryName = "";
    String quoteID = "", skuID = "";
    int addedPosition = 0, optionPosition = 0;
    TextView categoryNameTextView, cartCountTextView;
    ArrayList<String> productIsAddedCartArrayList;
    int activityCount = 0;
    ArrayList<String> skuListTem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        context = CategoryProductsActivity.this;
        shared = new Shared(CategoryProductsActivity.this);
        ghUtil = GHUtil.getInstance(CategoryProductsActivity.this);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        categoryNameTextView = (TextView) findViewById(R.id.categoryNameTextView);
        cartCountTextView = (TextView) findViewById(R.id.cartCountTextView);
        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        subCategoriesRecyclerView = (RecyclerView) findViewById(R.id.subCategoriesRecyclerView);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);

        intent = getIntent();
        categoryID = intent.getStringExtra("id");
        categoryName = intent.getStringExtra("name");
        categoryNameTextView.setText(categoryName);
        CategoryModel categoryModelResponse = ghUtil.getcategoryModel();
        for (int p = 0; p < categoryModelResponse.getChildren_data().size(); p++) {
            if (categoryModelResponse.getChildren_data().get(p).getId() == Integer.parseInt(categoryID)) {
                categoryModel = categoryModelResponse.getChildren_data().get(p);
            }
        }

        if (categoryModel != null) {
            categoryModelArrayList = new ArrayList<CategoryModel>();
            for (int p = 0; p < categoryModel.getChildren_data().size(); p++) {
                if (p == 0) {
                    categoryModel.getChildren_data().get(p).setCategoryBackground(true);
                    selectedSubCategoryId = String.valueOf(categoryModel.getChildren_data().get(p).getId());
                }
                categoryModelArrayList.add(categoryModel.getChildren_data().get(p));
            }

            if (categoryModelArrayList.size() == 0) {
                selectedSubCategoryId = String.valueOf(categoryModel.getId());
            }

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(CategoryProductsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            subCategoriesRecyclerView.setLayoutManager(mLayoutManager);
            subCategoriesAdapter = new SubCategoriesAdapter(CategoryProductsActivity.this, categoryModelArrayList);
            subCategoriesRecyclerView.setAdapter(subCategoriesAdapter);
            subCategoriesAdapter.setClickListener(CategoryProductsActivity.this);

            if (selectedSubCategoryId.length() > 0) {
                productsRecyclerView.setVisibility(View.GONE);
                getProductsServiceCall();
            }
        }

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryProductsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        subCategoriesAdapter.notifyDataSetChanged();
        selectedSubCategoryId = String.valueOf(categoryModelArrayList.get(position).getId());
        productsRecyclerView.setVisibility(View.GONE);
        getProductsServiceCall();
    }

    public void getProductsServiceCall() {
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Log.v("selectedSubCategoryId", selectedSubCategoryId);
        Call<ProductsResponse> loginResponseCall = service.getProducts(Integer.parseInt(selectedSubCategoryId), shared.getZipCode(), 1, 200);
        loginResponseCall.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    productsResponseArrayList = new ArrayList<ProductsResponse>();
                    productsResponseArrayList = response.body().getProducts();
                    if (productsResponseArrayList != null && productsResponseArrayList.size() > 0) {
                        productIsAddedCartArrayList = new ArrayList<String>();
                        for (int p = 0; p < productsResponseArrayList.size(); p++) {
                            productIsAddedCartArrayList.add("No");
                        }

                        getCartProductsOffline();

                    } else {
                        productsRecyclerView.setVisibility(View.GONE);
                        Toast.makeText(context, "No products available", Toast.LENGTH_SHORT).show();
                    }
                    /*if (shared.getUserID().length() > 0) {
                        getCartProductsServiceCall();
                    }*/

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCartProductsServiceCall() {
        // ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                //  ghUtil.dismissDialog();
                if (response.code() == 200) {

                    int cartCount = response.body().getItems().size();
                    if (cartCount > 0) {
                        cartCountTextView.setText(String.valueOf(cartCount));
                    } else {
                        cartCountTextView.setText("0");
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                // ghUtil.dismissDialog();
                //Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getQuoteIDServiceCall() {
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<QuoteIDResponse> loginResponseCall = service.getQuoteID(shared.getToken(), shared.getUserID());
        loginResponseCall.enqueue(new Callback<QuoteIDResponse>() {
            @Override
            public void onResponse(Call<QuoteIDResponse> call, Response<QuoteIDResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getStatus() == 200) {
                            quoteID = String.valueOf(response.body().getQuote_id());

                            if (productsResponseArrayList.get(addedPosition).getOptions().size() > 0) {
                                addToCartOptionServiceCall();
                            } else {
                                addToCartServiceCall();
                            }
                            addToCartServiceCall();
                        }
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteIDResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                ghUtil.dismissDialog();
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCartServiceCall() {
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        AddToCartRequest cartItem = new AddToCartRequest();
        cartItem.setQty(1);
        cartItem.setQuote_id(quoteID);
        cartItem.setSku(skuID);

        addToCartRequest.setCartItem(cartItem);
        Call<AddToCartResponse> loginResponseCall = service.addToCart("Bearer " + shared.getToken(), addToCartRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(CategoryProductsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    productIsAddedCartArrayList.set(addedPosition, "yes");
                    productsAdapter.notifyItemChanged(addedPosition);
                    getCartProductsServiceCall();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                cartCountTextView.setText("0");
                ghUtil.dismissDialog();
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToCartOptionServiceCall() {
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        AddToCartOptionRequest addToCartOptionRequest = new AddToCartOptionRequest();
        AddToCartOptionRequest.CartItem cartItem = new AddToCartOptionRequest.CartItem();
        cartItem.setQty(1);
        cartItem.setQuote_id(Integer.parseInt(quoteID));
        cartItem.setSku(skuID);

        AddToCartOptionRequest.ProductOption product_option = new AddToCartOptionRequest.ProductOption();
        AddToCartOptionRequest.ExtensionAttributes extension_attributes = new AddToCartOptionRequest.ExtensionAttributes();
        ArrayList<AddToCartOptionRequest.ConfigurableItemOptions> configurable_item_options = new ArrayList<AddToCartOptionRequest.ConfigurableItemOptions>();
        AddToCartOptionRequest.ConfigurableItemOptions obj = new AddToCartOptionRequest.ConfigurableItemOptions();
        obj.setOption_id("140");
        obj.setOption_value(Integer.parseInt(productsResponseArrayList.get(addedPosition).getOptions().get(optionPosition).getValue_index()));
        configurable_item_options.add(obj);
        extension_attributes.setConfigurable_item_options(configurable_item_options);
        product_option.setExtension_attributes(extension_attributes);
        cartItem.setProduct_option(product_option);
        cartItem.setProduct_type("configurable");

        addToCartOptionRequest.setCartItem(cartItem);

        Gson gson = new Gson();
        Log.v("addToCartOptionRequest", gson.toJson(addToCartOptionRequest));

        Call<AddToCartResponse> loginResponseCall = service.addToCartOption("Bearer " + shared.getToken(), addToCartOptionRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    Toast.makeText(CategoryProductsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    productIsAddedCartArrayList.set(addedPosition, "yes");
                    productsAdapter.notifyItemChanged(addedPosition);
                    getCartProductsServiceCall();
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductClick(int position, int option) {
        skuID = productsResponseArrayList.get(position).getSku();
        addedPosition = position;
        optionPosition = option;
        getQuoteIDServiceCall();
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

                for (int p = 0; p < productsResponseArrayList.size(); p++) {
                    for (int q = 0; q < offlineCartProductList.size(); q++) {
                        if (productsResponseArrayList.get(p).getOptions() != null && productsResponseArrayList.get(p).getOptions().size() > 0) {
                            for (int r = 0; r < productsResponseArrayList.get(p).getOptions().size(); r++) {
                                if (offlineCartProductList.get(q).getSkuID().equalsIgnoreCase(productsResponseArrayList.get(p).getOptions().get(r).getSku())) {
                                    productsResponseArrayList.get(p).getOptions().get(r).setCartQuantity(offlineCartProductList.get(q).getQty());
                                }
                            }
                        } else if (offlineCartProductList.get(q).getSkuID().equalsIgnoreCase(productsResponseArrayList.get(p).getSku())) {
                            productsResponseArrayList.get(p).setCartQuantity(offlineCartProductList.get(q).getQty());
                        }
                    }
                }

                for (int h = 0; h < productsResponseArrayList.size(); h++) {
                    double originalPrice = productsResponseArrayList.get(h).getPrice();
                    int originalPriceInt = (int) originalPrice;
                    double discount = 0;
                    if (originalPriceInt > 0) {
                        double finalPrice = productsResponseArrayList.get(h).getFinalPrice();
                        int finalPriceInt = (int) finalPrice;
                        int decreaseAmount = originalPriceInt - finalPriceInt;
                        double divisionValue = (double) decreaseAmount / originalPrice;
                        discount = divisionValue * 100.0;
                    }
                    productsResponseArrayList.get(h).setDiscountPercentage(discount);
                }

                productsRecyclerView.setVisibility(View.VISIBLE);
                productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                productsAdapter = new ProductsAdapter(context, productsResponseArrayList/*, productIsAddedCartArrayList*/);
                productsRecyclerView.setAdapter(productsAdapter);
                productsAdapter.setClickListener(CategoryProductsActivity.this);

            }
        }
        new GetCartProductOffline().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityCount > 0 && shared.getUserID().length() > 0) {
            updateCartProductsCount();
        } else {
            activityCount++;
        }
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

                if (productsAdapter != null && productsResponseArrayList != null) {
                    for (int i = 0; i < productsResponseArrayList.size(); i++) {
                        if (productsResponseArrayList.get(i).getOptions() != null && productsResponseArrayList.get(i).getOptions().size() > 0) {
                            for (int m = 0; m < productsResponseArrayList.get(i).getOptions().size(); m++) {
                                if (skuListTem.contains(productsResponseArrayList.get(i).getOptions().get(m).getSku())) {
                                    for (int t = 0; t < skuListTem.size(); t++) {
                                        productsResponseArrayList.get(i).getOptions().get(m).setCartQuantity(offlineCartProductList.get(t).getQty());
                                    }
                                } else {
                                    productsResponseArrayList.get(i).getOptions().get(m).setCartQuantity(0);
                                }
                            }
                        } else {
                            if (skuListTem.contains(productsResponseArrayList.get(i).getSku())) {
                                for (int t = 0; t < skuListTem.size(); t++) {
                                    productsResponseArrayList.get(i).setCartQuantity(offlineCartProductList.get(t).getQty());
                                }
                            } else {
                                productsResponseArrayList.get(i).setCartQuantity(0);
                            }
                        }
                    }
                    productsAdapter.notifyDataSetChanged();
                }
            }
        }
        new GetCartProductOffline().execute();
    }
}
