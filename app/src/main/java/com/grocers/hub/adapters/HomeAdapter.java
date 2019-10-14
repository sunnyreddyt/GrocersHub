package com.grocers.hub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.CategoryProductsActivity;
import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.fragments.HomeFragment;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements OnOfferProductClickListener {

    private Context mContext;
    ItemClickListener itemClickListener;
    private ArrayList<HomeResponse> categoryProducts;
    ArrayList<HomeProductListAdapter> homeProductListAdapterArrayList = new ArrayList<HomeProductListAdapter>();
    HomeProductListAdapter homeProductListAdapter;
    GHUtil ghUtil;
    String quoteID;
    int parentPosition, productPosition;
    Shared shared;
    //public static ArrayList<CartResponse> cartResponseArrayList;
    OnCartChangeListener onCartChangeListener;
    HomeFragment fragment;

    public void setCartListener(OnCartChangeListener onCartChangeListener) {
        this.onCartChangeListener = onCartChangeListener;
    }

    public HomeAdapter(Context mContext,
                       ArrayList<HomeResponse> categoryProducts, HomeFragment fragment) {
        this.mContext = mContext;
        ghUtil = GHUtil.getInstance(mContext);
        this.categoryProducts = categoryProducts;
        shared = new Shared(mContext);
        // this.cartResponseArrayList = cartResponseArrayList;
        this.fragment = fragment;

        for (int h = 0; h < categoryProducts.size(); h++) {
            homeProductListAdapterArrayList.add(null);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onOfferProductClick(int parentPosition, int productPosition) {
        this.parentPosition = parentPosition;
        this.productPosition = productPosition;
        Log.v("positions", String.valueOf(parentPosition) + String.valueOf(productPosition));
        // getQuoteIDServiceCall();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView categoryNameTextView;
        RecyclerView productsRecyclerView;

        public MyViewHolder(View view) {
            super(view);
            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
            productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        }
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        return new HomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {

        holder.categoryNameTextView.setText(categoryProducts.get(position).getName());

        if (homeProductListAdapterArrayList.get(position) == null) {
            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holder.productsRecyclerView.setLayoutManager(mLayoutManager1);
            homeProductListAdapter = new HomeProductListAdapter(mContext, categoryProducts.get(position).getProducts(), position, fragment);
            holder.productsRecyclerView.setAdapter(homeProductListAdapter);
            homeProductListAdapterArrayList.set(position, homeProductListAdapter);
        } else {
            homeProductListAdapterArrayList.get(position).notifyDataSetChanged();
        }
        //offerProductsListAdapter.setClickListener(HomeAdapter.this);

    }

    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }

    /*public void getCartProductsServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<CartResponse> loginResponseCall = service.getCartProducts("Bearer " + shared.getToken());
        loginResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.code() == 200) {
                    cartResponseArrayList = response.body().getItems();
                    homeProductListAdapterArrayList.get(parentPosition).notifyItemChanged(productPosition);
                    if (onCartChangeListener != null) {
                        onCartChangeListener.onCartChange(response.body().getItems().size());
                    }
                } else {
                    Toast.makeText(mContext, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                //Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*public void getQuoteIDServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<QuoteIDResponse> loginResponseCall = service.getQuoteID(shared.getToken(), shared.getUserID());
        loginResponseCall.enqueue(new Callback<QuoteIDResponse>() {
            @Override
            public void onResponse(Call<QuoteIDResponse> call, Response<QuoteIDResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getStatus() == 200) {
                            quoteID = String.valueOf(response.body().getQuote_id());
                            addToCartServiceCall();
                        }
                    }
                } else {
                    Toast.makeText(mContext, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteIDResponse> call, Throwable t) {
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*public void addToCartServiceCall() {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        AddToCartRequest cartItem = new AddToCartRequest();
        cartItem.setQty(1);
        cartItem.setQuote_id(quoteID);
        cartItem.setSku(categoryProducts.get(parentPosition).getProducts().get(productPosition).getSku());

        addToCartRequest.setCartItem(cartItem);
        Call<AddToCartResponse> loginResponseCall = service.addToCart("Bearer " + shared.getToken(), addToCartRequest);
        loginResponseCall.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(mContext, "Added to Cart", Toast.LENGTH_SHORT).show();
                    getCartProductsServiceCall();
                } else {
                    Toast.makeText(mContext, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                // Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
