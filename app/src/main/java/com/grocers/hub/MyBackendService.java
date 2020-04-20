package com.grocers.hub;

import com.grocers.hub.models.GatewayOrderStatus;
import com.grocers.hub.models.GetOrderIDRequest;
import com.grocers.hub.models.GetOrderIDResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyBackendService {

    @POST("/order")
    Call<GetOrderIDResponse> createOrder(@Body GetOrderIDRequest request);

    @GET("/status")
    Call<GatewayOrderStatus> orderStatus(@Query("env") String env, @Query("order_id") String orderID,
                                         @Query("transaction_id") String transactionID);

    @POST("/refund")
    Call<ResponseBody> refundAmount(@Query("env") String env,
                                    @Query("transaction_id") String transaction_id,
                                    @Query("amount") String amount);
}
