package com.grocers.hub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.grocers.hub.adapters.CategoriesListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OfferProductsListAdapter;
import com.grocers.hub.adapters.OnProductClickListener;
import com.grocers.hub.adapters.ProductsAdapter;
import com.grocers.hub.adapters.SubCategoriesAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.fragments.HomeFragment;
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
    int addedPosition = 0;
    TextView categoryNameTextView, cartCountTextView;
    ArrayList<String> productIsAddedCartArrayList;

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

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(CategoryProductsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            subCategoriesRecyclerView.setLayoutManager(mLayoutManager);
            subCategoriesAdapter = new SubCategoriesAdapter(CategoryProductsActivity.this, categoryModelArrayList);
            subCategoriesRecyclerView.setAdapter(subCategoriesAdapter);
            subCategoriesAdapter.setClickListener(CategoryProductsActivity.this);

            if (selectedSubCategoryId.length() > 0) {
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
        getProductsServiceCall();
    }

    public void getProductsServiceCall() {
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<ProductsResponse> loginResponseCall = service.getProducts(Integer.parseInt(selectedSubCategoryId), "500081");
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
                        productsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        productsAdapter = new ProductsAdapter(context, productsResponseArrayList, productIsAddedCartArrayList);
                        productsRecyclerView.setAdapter(productsAdapter);
                        productsAdapter.setClickListener(CategoryProductsActivity.this);
                    } else {
                        Toast.makeText(context, "No products available", Toast.LENGTH_SHORT).show();
                    }
                    if (shared.getUserID().length() > 0) {
                        getCartProductsServiceCall();
                    }

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
        ghUtil.showDialog(CategoryProductsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                ghUtil.dismissDialog();
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
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
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
                            addToCartServiceCall();
                        }
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteIDResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
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
    public void onProductClick(int position) {
        skuID = productsResponseArrayList.get(position).getSku();
        addedPosition = position;
        getQuoteIDServiceCall();
    }
}
