package com.grocers.hub.network;

import com.grocers.hub.models.AddToCartRequest;
import com.grocers.hub.models.AddToCartResponse;
import com.grocers.hub.models.CartResponse;
import com.grocers.hub.models.CategoryModel;
import com.grocers.hub.models.DeleteCartResponse;
import com.grocers.hub.models.FinalOrderResponse;
import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.HomeResponse;
import com.grocers.hub.models.LocationsModel;
import com.grocers.hub.models.OrdersResponse;
import com.grocers.hub.models.PaymentRequest;
import com.grocers.hub.models.ProductDetailsResponse;
import com.grocers.hub.models.ProductsResponse;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.models.ShippingAddressRequest;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.models.SimilarProductsResponse;
import com.grocers.hub.models.UpdateCartRequest;
import com.grocers.hub.models.UpdateProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*Interface for all api's*/
public interface APIInterface {

    @Headers({"Accept: application/json"})
    @GET("homeapi/sellerpincode")
    Call<LocationsModel> getLocations();

    @Headers({"Accept: application/json"})
    @POST("homeapi/customerlogin")
    Call<GeneralResponse> userLogin(@Body GeneralRequest generalRequest);

    @Headers({"Accept: application/json"})
    @POST("homeapi/customerregistration")
    Call<GeneralResponse> userRegistration(@Body GeneralRequest generalRequest);

    @Headers({"Accept: application/json"})
    @POST("homeapi/Updatecustomer?")
    Call<GeneralResponse> updateProfile(@Query("cusId") String cusId, @Body UpdateProfileRequest updateProfileRequest);

    @Headers({"Accept: application/json"})
    @GET("rest//V1/customers/me")
    Call<GeneralResponse> customerDetails(@Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("rest/default/V1/mma/categories")
    Call<CategoryModel> getCategories(@Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("homeapi?")
    Call<HomeResponse> getHomeDetails(@Query("city") String city);

    @Headers({"Accept: application/json"})
    @GET("homeapi/Categoryproducts?")
    Call<ProductsResponse> getProducts(@Query("categoryId") int categoryId, @Query("zipcode") String zipcode, @Query("p") int p, @Query("limit") int limit);

    @Headers({"Accept: application/json"})
    @GET("/rest/V1/products/{id}")
    Call<ProductDetailsResponse> getProductDetails(@Path("id") String Id);

    @Headers({"Accept: application/json"})
    @GET("homeapi/Similarproduct?")
    Call<SimilarProductsResponse> getSimilarProducts(@Query("sku") String sku);

    @Headers({"Accept: application/json"})
    @GET("homeapi/Quoteid?")
    Call<QuoteIDResponse> getQuoteID(@Query("token") String token, @Query("customer_id") String customer_id);

    @Headers({"Accept: application/json"})
    @POST("rest/V1/carts/mine/items")
    Call<AddToCartResponse> addToCart(@Header("Authorization") String auth, @Body AddToCartRequest addToCartRequest);

    @Headers({"Accept: application/json"})
    @GET("rest/V1/carts/mine")
    Call<CartResponse> getCartProducts(@Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("homeapi/shippinginfo?")
    Call<ShippingResponse> setShipping(@Query("token") String token, @Body ShippingAddressRequest shippingAddressRequest);

    @Headers({"Accept: application/json"})
    @POST("homeapi/placeorder?")
    Call<FinalOrderResponse> setPayment(@Query("token") String token, @Body PaymentRequest paymentRequest);

    @Headers({"Accept: application/json"})
    @GET("homeapi/listorders?")
    Call<OrdersResponse> getOrderHistory(@Query("customer_id") String customer_id);

    @Headers({"Accept: application/json"})
    @POST("rest/V1/carts/mine/items")
    Call<AddToCartResponse> updateCart(@Header("Authorization") String auth, @Body UpdateCartRequest updateCartRequest);

    @Headers({"Accept: application/json"})
    @DELETE("homeapi/deletecart?")
    Call<DeleteCartResponse> deleteCartItem(@Query("itemId") String itemId, @Query("token") String token);

    @Headers({"Accept: application/json"})
    @GET("homeapi/Searchapi?")
    Call<ProductsResponse> search(@Query("key") String key, @Query("zipcode") String zipcode);

    @Headers({"Accept: application/json"})
    @GET("homeapi/applycoupon?")
    Call<DeleteCartResponse> applyCoupon(@Query("cartId") String cartId, @Query("couponCode") String couponCode);

}