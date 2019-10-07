package com.grocers.hub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.fragments.HomeFragment;
import com.grocers.hub.fragments.OffersFragment;
import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> implements OnOfferProductClickListener {

    private Context mContext;
    ItemClickListener itemClickListener;
    private ArrayList<HomeResponse> categoryProducts;
    ArrayList<OfferProductsListAdapter> offerProductsListAdapterArrayList = new ArrayList<OfferProductsListAdapter>();
    OfferProductsListAdapter offerProductsListAdapter;
    GHUtil ghUtil;
    String quoteID;
    int parentPosition, productPosition;
    Shared shared;
    // public static ArrayList<CartResponse> cartResponseArrayList;
    OnCartChangeListener onCartChangeListener;
    OffersFragment fragment;

    public void setCartListener(OnCartChangeListener onCartChangeListener) {
        this.onCartChangeListener = onCartChangeListener;
    }

    public OffersAdapter(Context mContext,
                         ArrayList<HomeResponse> categoryProducts, OffersFragment fragment) {
        this.mContext = mContext;
        ghUtil = GHUtil.getInstance(mContext);
        this.categoryProducts = categoryProducts;
        shared = new Shared(mContext);
        //this.cartResponseArrayList = cartResponseArrayList;
        this.fragment = fragment;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onOfferProductClick(int parentPosition, int productPosition) {
        this.parentPosition = parentPosition;
        this.productPosition = productPosition;
        Log.v("positions", String.valueOf(parentPosition) + String.valueOf(productPosition));
        //getQuoteIDServiceCall();
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
    public OffersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        return new OffersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OffersAdapter.MyViewHolder holder, final int position) {

        holder.categoryNameTextView.setText(categoryProducts.get(position).getName());

//        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.productsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        offerProductsListAdapter = new OfferProductsListAdapter(mContext, categoryProducts.get(position).getProducts(), position, fragment);
        holder.productsRecyclerView.setAdapter(offerProductsListAdapter);
        offerProductsListAdapterArrayList.add(offerProductsListAdapter);
        //offerProductsListAdapter.setClickListener(OffersAdapter.this);

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
                    offerProductsListAdapterArrayList.get(parentPosition).notifyItemChanged(productPosition);
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

