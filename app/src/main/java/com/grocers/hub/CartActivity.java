package com.grocers.hub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.CartProductsAdapter;
import com.grocers.hub.adapters.OnCartUpdateListener;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.AppDatabase;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.database.entities.OfflineCartProduct;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.DeleteCartResponse;
import com.grocers.hub.models.UpdateCartRequest;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements OnCartUpdateListener {

    RecyclerView cartRecyclerView;
    ImageView backImageView;
    TextView paymentTextView, noDataTextView;
    Shared shared;
    Context context;
    GHUtil ghUtil;
    public static int totalAmount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ghUtil = GHUtil.getInstance(CartActivity.this);
        shared = new Shared(CartActivity.this);
        context = CartActivity.this;
        backImageView = (ImageView) findViewById(R.id.backImageView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        paymentTextView = (TextView) findViewById(R.id.paymentTextView);

        totalAmount = 0;

        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getUserID().length() > 0) {
                    if (totalAmount > 300) {
                        Intent intent = new Intent(CartActivity.this, ShippingAddressActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Minimum order should be greater than Rs.300", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        if (ghUtil.isConnectingToInternet()) {
            /*if (shared.getUserID().length() > 0) {
                getCartProductsServiceCall();
            } else {
                getCartProductsOfline();
            }*/
            getCartProductsOfline();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*public void getCartProductsServiceCall() {
        ghUtil.showDialog(CartActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                    cartRecyclerView.setLayoutManager(mLayoutManager);
                    CartProductsAdapter cartProductsAdapter = new CartProductsAdapter(CartActivity.this, response.body().getItems());
                    cartRecyclerView.setAdapter(cartProductsAdapter);
                    cartProductsAdapter.setItemClickListener(CartActivity.this);

                    if (response.body().getItems().size() == 0) {
                        paymentTextView.setVisibility(View.INVISIBLE);
                        cartRecyclerView.setVisibility(View.GONE);
                        noDataTextView.setVisibility(View.VISIBLE);
                    } else {
                        paymentTextView.setVisibility(View.VISIBLE);
                        cartRecyclerView.setVisibility(View.VISIBLE);
                        noDataTextView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(context, "No products added to cart", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "No products added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void getCartProductsOfline() {
        class GetCartProductOffline extends AsyncTask<Void, Void, List<OfflineCartProduct>> {
            @Override
            protected List<OfflineCartProduct> doInBackground(Void... voids) {

                /*OfflineCartProduct offlineCartProductList[] = new OfflineCartProduct[10];
                offlineCartProductList[0] = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .getAll();*/
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

                ArrayList<CartResponse> cartResponseList = new ArrayList<>();
                for (int g = 0; g < offlineCartProductList.size(); g++) {
                    CartResponse cartResponse = new CartResponse();
                    cartResponse.setSku(offlineCartProductList.get(g).getSkuID());
                    cartResponse.setQty(offlineCartProductList.get(g).getQty());
                    cartResponse.setName(offlineCartProductList.get(g).getName());
                    cartResponse.setPrice(Double.parseDouble(offlineCartProductList.get(g).getPrice()));
                    cartResponse.setImage(offlineCartProductList.get(g).getImage());
                    cartResponse.setProduct_type(offlineCartProductList.get(g).getProduct_type());
                    cartResponseList.add(cartResponse);
                }

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                cartRecyclerView.setLayoutManager(mLayoutManager);
                CartProductsAdapter cartProductsAdapter = new CartProductsAdapter(CartActivity.this, cartResponseList, offlineCartProductList);
                cartRecyclerView.setAdapter(cartProductsAdapter);
                cartProductsAdapter.setItemClickListener(CartActivity.this);

                if (offlineCartProductList.size() == 0) {
                    paymentTextView.setVisibility(View.INVISIBLE);
                    cartRecyclerView.setVisibility(View.GONE);
                    noDataTextView.setVisibility(View.VISIBLE);
                } else {
                    paymentTextView.setVisibility(View.VISIBLE);
                    cartRecyclerView.setVisibility(View.VISIBLE);
                    noDataTextView.setVisibility(View.GONE);
                }
            }
        }
        new GetCartProductOffline().execute();
    }

    @Override
    public void onCartUpdate() {

        getCartProductsOfline();

        /*if (type.equalsIgnoreCase("add") || type.equalsIgnoreCase("remove")) {
            UpdateCartRequest updateCartRequest = new UpdateCartRequest();
            UpdateCartRequest cartItem = new UpdateCartRequest();
            cartItem.setItem_id(cartResponse.getItem_id());
            cartItem.setQty(count);
            cartItem.setQuote_id(cartResponse.getQuote_id());
            updateCartRequest.setCartItem(cartItem);
            updateCartServiceCall(updateCartRequest);
        } else if (type.equalsIgnoreCase("delete")) {
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("")
                    .setMessage("Are you sure, you want to remove from cart?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteCartServiceCall(cartResponse.getItem_id());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    })
                    .show();
        }*/
    }

   /* public void updateCartServiceCall(UpdateCartRequest updateCartRequest) {
        ghUtil.showDialog(CartActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<AddToCartResponse> loginResponseCall = service.updateCart("Bearer " + shared.getToken(), updateCartRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    getCartProductsServiceCall();
                } else {
                    getCartProductsServiceCall();
                    Toast.makeText(context, "more Products are not available at store", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

   /* public void deleteCartServiceCall(int itemId) {
        ghUtil.showDialog(CartActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        Call<DeleteCartResponse> loginResponseCall = service.deleteCartItem(String.valueOf(itemId), shared.getToken());
        loginResponseCall.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200 && response.body().getStatus() == 200) {
                    Toast.makeText(context, "Deleted from cart", Toast.LENGTH_SHORT).show();
                    getCartProductsServiceCall();
                } else {
                    getCartProductsServiceCall();
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void displayAdressesInLog(OfflineCartProduct[] offlineCartProducts) {

    }

}
