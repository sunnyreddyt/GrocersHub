package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocers.hub.adapters.CouponListAdapter;
import com.grocers.hub.adapters.ItemClickListener;
import com.grocers.hub.adapters.OnCouponClick;
import com.grocers.hub.adapters.OrderProductsAdapter;
import com.grocers.hub.adapters.PaymentAdapter;
import com.grocers.hub.constants.Shared;
import com.grocers.hub.database.DatabaseClient;
import com.grocers.hub.fragments.MainActivity;
import com.grocers.hub.instamojo.Instamojo;
import com.grocers.hub.instamojo.helpers.Constants;
import com.grocers.hub.models.ApplyCouponResponse;
import com.grocers.hub.models.CouponListResponseModel;
import com.grocers.hub.models.FinalOrderResponse;
import com.grocers.hub.models.GatewayOrderStatus;
import com.grocers.hub.models.GetOrderIDRequest;
import com.grocers.hub.models.Payment;
import com.grocers.hub.models.PaymentRequest;
import com.grocers.hub.models.QuoteIDResponse;
import com.grocers.hub.models.ShippingResponse;
import com.grocers.hub.models.UpdateOrderStatusRequest;
import com.grocers.hub.models.UpdateOrderStatusResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity implements ItemClickListener, OnCouponClick, Instamojo.InstamojoPaymentCallback {

    ImageView backImageView;
    RecyclerView paymentMethodsRecyclerView, productsRecyclerView;
    GHUtil ghUtil;
    TextView orderTextView, couponMessageTextView, subTotalTextView, discountTextView, shippingTextView, taxTextView, grandTotalTextView, applyCouponTextView;
    Context context;
    Shared shared;
    String quoteID = "", orderID = "";
    String email, phone, postcode, address, name;
    Dialog couponDialog, orderSuccessDialog;
    String selectedPaymentMethod = "";
    public static int selectedPaymentPosition = -1;
    PaymentAdapter paymentAdapter;
    ShippingResponse shippingResponse;
    ArrayList<CouponListResponseModel> couponListResponseModelArrayList;
    EditText couponCodeEditText;
    private MyBackendService myBackendService;
    private AlertDialog dialog;
    private Instamojo.Environment mCurrentEnv;
    FinalOrderResponse finalOrderResponse;
    private String orderAmount = "0";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        shared = new Shared(CheckoutActivity.this);
        context = CheckoutActivity.this;
        ghUtil = GHUtil.getInstance(CheckoutActivity.this);

        orderTextView = (TextView) findViewById(R.id.orderTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        paymentMethodsRecyclerView = (RecyclerView) findViewById(R.id.paymentMethodsRecyclerView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);
        shippingTextView = (TextView) findViewById(R.id.shippingTextView);
        applyCouponTextView = (TextView) findViewById(R.id.applyCouponTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
        subTotalTextView = (TextView) findViewById(R.id.subTotalTextView);
        couponMessageTextView = (TextView) findViewById(R.id.couponMessageTextView);

        mCurrentEnv = Instamojo.Environment.PRODUCTION;
        Instamojo.getInstance().initialize(CheckoutActivity.this, mCurrentEnv);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
        // Initialize the backend service client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sample-sdk-server.instamojo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myBackendService = retrofit.create(MyBackendService.class);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        postcode = intent.getStringExtra("postcode");
        address = intent.getStringExtra("address");
        name = intent.getStringExtra("name");

        shippingResponse = ghUtil.getShippingResponse();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        paymentMethodsRecyclerView.setLayoutManager(mLayoutManager);
        paymentAdapter = new PaymentAdapter(context, shippingResponse.getPayment_methods());
        paymentMethodsRecyclerView.setAdapter(paymentAdapter);
        paymentAdapter.setCLickListener(this);

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        OrderProductsAdapter orderProductsAdapter = new OrderProductsAdapter(CheckoutActivity.this, shippingResponse.getTotals().getItems());
        productsRecyclerView.setAdapter(orderProductsAdapter);

        subTotalTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getBase_subtotal()));
        discountTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getDiscount_amount()));
        shippingTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getBase_shipping_amount()));
        taxTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getTax_amount()));
        grandTotalTextView.setText("₹ " + String.valueOf(shippingResponse.getTotals().getGrand_total()));
        orderAmount = String.valueOf(shippingResponse.getTotals().getGrand_total());
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPaymentPosition != -1) {
                    if (quoteID.length() > 0) {
                        if (ghUtil.isConnectingToInternet()) {
                            setPaymentServiceCall();
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getQuoteIDServiceCall();
                        Toast.makeText(context, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please select payment method", Toast.LENGTH_SHORT).show();
                }
            }
        });

        applyCouponTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (couponListResponseModelArrayList != null && couponListResponseModelArrayList.size() > 0) {
                    couponDialog();
                } else {
                    Toast.makeText(context, "No Coupons available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (ghUtil.isConnectingToInternet()) {
            getQuoteIDServiceCall();
            getCouponsServiceCall();
        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void setPaymentServiceCall() {
        ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCartId(Integer.parseInt(quoteID));

        PaymentRequest.PaymentMethod paymentMethod = new PaymentRequest.PaymentMethod();
        paymentMethod.setMethod(selectedPaymentMethod);

        PaymentRequest.BillingAddress billingAddress = new PaymentRequest.BillingAddress();
        billingAddress.setEmail(email);
        billingAddress.setRegion("Telangana");
        billingAddress.setRegion_id(564);
        billingAddress.setRegion_code("TG");
        billingAddress.setCountry_id("IN");
        billingAddress.setStreet(address);
        billingAddress.setPostcode(postcode);
        billingAddress.setCity(shared.getCity());
        billingAddress.setTelephone(phone);
        billingAddress.setFirstname(name);
        billingAddress.setLastname(name);

        paymentRequest.setBilling_address(billingAddress);
        paymentRequest.setPaymentMethod(paymentMethod);

        Gson gson = new Gson();
        Log.v("paymentRequest", gson.toJson(paymentRequest));

        Call<FinalOrderResponse> loginResponseCall = service.setPayment(shared.getToken(), paymentRequest);
        loginResponseCall.enqueue(new Callback<FinalOrderResponse>() {
            @Override
            public void onResponse(Call<FinalOrderResponse> call, Response<FinalOrderResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200 && response.body().getStatus() != 400) {
                    orderID = response.body().getOrderId();
                    finalOrderResponse = response.body();
                    if (selectedPaymentMethod.equalsIgnoreCase("instamojo")) {
                        //createOrderOnServer();
                        initiateSDKPayment(response.body().getInstamojo_order_id());
                    } else {
                        orderSuccessDialog(response.body());
                    }
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FinalOrderResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearOfflineCart() {
        class ClearCartProductOffline extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {

                DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .offlineCartDao()
                        .truncate();

                return "";
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);
            }
        }
        new ClearCartProductOffline().execute();
    }

    public void getQuoteIDServiceCall() {
        //ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<QuoteIDResponse> loginResponseCall = service.getQuoteID(shared.getToken(), shared.getUserID());
        loginResponseCall.enqueue(new Callback<QuoteIDResponse>() {
            @Override
            public void onResponse(Call<QuoteIDResponse> call, Response<QuoteIDResponse> response) {
                // ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getStatus() == 200) {
                            quoteID = String.valueOf(response.body().getQuote_id());
                        }
                    }
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteIDResponse> call, Throwable t) {
                // ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void couponDialog() {
        ghUtil.dismissDialog();
        couponDialog = new Dialog(CheckoutActivity.this);
        couponDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        couponDialog.setContentView(R.layout.dialog_coupon);

        couponCodeEditText = (EditText) couponDialog.findViewById(R.id.couponCodeEditText);
        TextView skipTextView = (TextView) couponDialog.findViewById(R.id.skipTextView);
        TextView applyTextView = (TextView) couponDialog.findViewById(R.id.applyTextView);
        RecyclerView couponListRecyclerView = (RecyclerView) couponDialog.findViewById(R.id.couponListRecyclerView);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CheckoutActivity.this, LinearLayoutManager.HORIZONTAL, false);
        couponListRecyclerView.setLayoutManager(mLayoutManager);
        CouponListAdapter couponListAdapter = new CouponListAdapter(CheckoutActivity.this, couponListResponseModelArrayList);
        couponListRecyclerView.setAdapter(couponListAdapter);
        couponListAdapter.setClickListener(CheckoutActivity.this);

        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponDialog.dismiss();
            }
        });

        applyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String couponCode = couponCodeEditText.getText().toString();
                if (couponCode.length() > 0) {
                    applyCouponServiceCall(couponCode);
                }
            }
        });

        couponDialog.show();
    }

    public void orderSuccessDialog(FinalOrderResponse finalOrderResponse) {
        orderSuccessDialog = new Dialog(CheckoutActivity.this);
        orderSuccessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        orderSuccessDialog.setContentView(R.layout.dialog_order_success);

        TextView titleTextView = (TextView) orderSuccessDialog.findViewById(R.id.titleTextView);
        TextView okTextView = (TextView) orderSuccessDialog.findViewById(R.id.okTextView);
        TextView orderIDEditText = (TextView) orderSuccessDialog.findViewById(R.id.orderIDEditText);

        if (finalOrderResponse.getOrderId() != null) {
            orderIDEditText.setText("Order ID: " + finalOrderResponse.getOrderId());
        }
        titleTextView.setText("Hi " + shared.getUserFirstName() + ", ");

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderSuccessDialog.dismiss();
                Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        clearOfflineCart();
        orderSuccessDialog.show();
    }

    public void applyCouponServiceCall(final String couponCode) {
        ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        Log.v("quoteID", quoteID);
        Call<ApplyCouponResponse> loginResponseCall = service.applyCoupon(quoteID, couponCode);
        loginResponseCall.enqueue(new Callback<ApplyCouponResponse>() {
            @Override
            public void onResponse(Call<ApplyCouponResponse> call, Response<ApplyCouponResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    couponDialog.dismiss();

                    if (response.body().getCartDetails() != null && response.body().getStatus() == 200) {
                        // discountTextView.setText("₹ " + String.valueOf(response.body().getTotals().getBase_discount_amount()));
                        shippingTextView.setText("₹ " + String.valueOf(response.body().getCartDetails().getBase_shipping_amount()));
                        // taxTextView.setText("₹ " + String.valueOf(response.body().getTotals().getBase_tax_amount()));
                        grandTotalTextView.setText("₹ " + String.valueOf(response.body().getCartDetails().getBase_subtotal_with_discount()));

                        couponMessageTextView.setText("Coupon Applied: " + response.body().getCartDetails().getCoupon_code());
                        couponMessageTextView.setTextColor(Color.parseColor("#01d365"));

                    } else {
                        couponMessageTextView.setText(couponCode + " " + response.body().getMessage());
                        couponMessageTextView.setTextColor(Color.parseColor("#ff0013"));
                    }
                    couponMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApplyCouponResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onlineOrderSuccessDialog(FinalOrderResponse finalOrderResponse) {
        orderSuccessDialog = new Dialog(CheckoutActivity.this);
        orderSuccessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        orderSuccessDialog.setContentView(R.layout.dialog_online_order_success);
        orderSuccessDialog.setCancelable(false);
        orderSuccessDialog.setCanceledOnTouchOutside(false);

        TextView titleTextView = (TextView) orderSuccessDialog.findViewById(R.id.titleTextView);
        TextView okTextView = (TextView) orderSuccessDialog.findViewById(R.id.okTextView);
        TextView orderIDEditText = (TextView) orderSuccessDialog.findViewById(R.id.orderIDEditText);

        if (finalOrderResponse.getOrderId() != null) {
            orderIDEditText.setText("Order ID: " + finalOrderResponse.getOrderId());
        }
        titleTextView.setText("Hi " + shared.getUserFirstName() + ", ");

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderSuccessDialog.dismiss();
                Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        clearOfflineCart();
        orderSuccessDialog.show();
    }

    @Override
    public void onClick(int position) {
        selectedPaymentPosition = position;
        selectedPaymentMethod = shippingResponse.getPayment_methods().get(position).getCode();
        paymentAdapter.notifyDataSetChanged();
    }

    public void getCouponsServiceCall() {
        ghUtil.showDialog(CheckoutActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);

        Call<CouponListResponseModel> loginResponseCall = service.getCoupons("Bearer 34s77aiqvcmc84v65s1tpnwip9dtvtqk", 1);
        loginResponseCall.enqueue(new Callback<CouponListResponseModel>() {
            @Override
            public void onResponse(Call<CouponListResponseModel> call, Response<CouponListResponseModel> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200) {
                    if (response.body().getItems() != null && response.body().getItems().size() > 0) {
                        couponListResponseModelArrayList = new ArrayList<>();
                        couponListResponseModelArrayList = response.body().getItems();

                        // couponDialog();

                    } else {
                        Toast.makeText(context, "No Coupons available", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CouponListResponseModel> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCouponClick(int position) {
        couponCodeEditText.setText(couponListResponseModelArrayList.get(position).getCode());
    }

    private void createOrderOnServer() {
        GetOrderIDRequest request = new GetOrderIDRequest();
        request.setEnv(mCurrentEnv.name());
        request.setBuyerName(name);
        request.setBuyerEmail(email);
        request.setBuyerPhone(phone);
        request.setDescription(orderID + "payment");
        request.setAmount(/*orderAmount*/"10");

        initiateSDKPayment(orderID);
        /*Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(request);
        getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
            @Override
            public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
                if (response.isSuccessful()) {
                    String orderId = response.body().getOrderID();

                    //if (!mCustomUIFlow) {
                        // Initiate the default SDK-provided payment activity
                        initiateSDKPayment(orderId);
*//*
                    } else {
                        // OR initiate a custom UI activity
                        initiateCustomPayment(orderId);
                    }*//*

                } else {
                    // Handle api errors
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("payment", "Error in response" + jObjError.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
                // Handle call failure
                Log.d("payment", "Failure");
            }
        });*/
    }

    private void initiateSDKPayment(String orderID) {
        Instamojo.getInstance().initiatePayment(CheckoutActivity.this, orderID, this);
    }

    private void initiateCustomPayment(String orderID) {
        /*Intent intent = new Intent(getBaseContext(), CustomUIActivity.class);
        intent.putExtra(Constants.ORDER_ID, orderID);
        startActivityForResult(intent, Constants.REQUEST_CODE);*/
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Will check for the transaction status of a particular Transaction
     *
     * @param transactionID Unique identifier of a transaction ID
     */
    private void checkPaymentStatus(final String transactionID, final String orderID) {
        if (transactionID == null && orderID == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Checking transaction status");
        Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(mCurrentEnv.name().toLowerCase(),
                orderID, transactionID);
        getOrderStatusCall.enqueue(new retrofit2.Callback<GatewayOrderStatus>() {
            @Override
            public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    GatewayOrderStatus orderStatus = response.body();
                    if (orderStatus.getStatus().equalsIgnoreCase("successful")) {
                        if (finalOrderResponse != null) {
                            //orderSuccessDialog(finalOrderResponse);
                        }
                        clearOfflineCart();
                        showToast("Transaction still pending");
                        return;
                    }

                    showToast("Transaction successful for id - " + orderStatus.getPaymentID());
                    refundTheAmount(transactionID, orderStatus.getAmount());

                } else {
                    showToast("Error occurred while fetching transaction status");
                }
            }

            @Override
            public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        showToast("Failed to fetch the transaction status");
                    }
                });
            }
        });
    }

    /**
     * Will initiate a refund for a given transaction with given amount
     *
     * @param transactionID Unique identifier for the transaction
     * @param amount        amount to be refunded
     */
    private void refundTheAmount(String transactionID, String amount) {
        if (transactionID == null || amount == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Initiating a refund for - " + amount);
        Call<ResponseBody> refundCall = myBackendService.refundAmount(
                mCurrentEnv.name().toLowerCase(),
                transactionID, amount);
        refundCall.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    showToast("Refund initiated successfully");

                } else {
                    showToast("Failed to initiate a refund");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                showToast("Failed to Initiate a refund");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID);
            } else {
                showToast("Oops!! Payment was cancelled");
            }
        }
    }

    @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d("payment", "Payment complete");
        showToast("Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
        if (transactionID != null || paymentID != null) {
            ghUtil.showDialog(CheckoutActivity.this);
            updateOrderStatus(paymentID, paymentStatus);
            //checkPaymentStatus(transactionID, orderID);
        } else {
            showToast("Oops!! Payment was cancelled");
        }
    }

    @Override
    public void onPaymentCancelled() {
        Log.d("payment", "Payment cancelled");
        showToast("Payment cancelled by user");
    }

    @Override
    public void onInitiatePaymentFailure(String errorMessage) {
        Log.d("payment", "Initiate payment failed");
        showToast("Initiating payment failed. Error: " + errorMessage);
    }

    public void updateOrderStatus(String paymentID, final String paymentStatus) {
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        final Payment payment = new Payment();
        payment.setOrder_id(orderID);
        payment.setPayment_id(paymentID);
        payment.setPayment_status(paymentStatus);
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setPayment(payment);
        Gson gson = new Gson();
        Log.v("updateOrderStatus", gson.toJson(updateOrderStatusRequest));
        Call<UpdateOrderStatusResponse> loginResponseCall = service.updateOrderStatus(shared.getToken(), updateOrderStatusRequest);
        loginResponseCall.enqueue(new Callback<UpdateOrderStatusResponse>() {
            @Override
            public void onResponse(Call<UpdateOrderStatusResponse> call, Response<UpdateOrderStatusResponse> response) {
                ghUtil.dismissDialog();
                if (response.code() == 200 && response.body().getStatus() != 400) {
                    if (paymentStatus.equalsIgnoreCase("Credit")) {
                        onlineOrderSuccessDialog(finalOrderResponse);
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Payment failed, please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed to update payment status", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateOrderStatusResponse> call, Throwable t) {
                ghUtil.dismissDialog();
                Toast.makeText(context, "Failed to update payment status", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
