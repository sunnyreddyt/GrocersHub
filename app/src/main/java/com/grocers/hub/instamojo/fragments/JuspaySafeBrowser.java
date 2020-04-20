package com.grocers.hub.instamojo.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.grocers.hub.instamojo.activities.PaymentActivity;
import com.grocers.hub.instamojo.helpers.Logger;
import com.grocers.hub.instamojo.network.JavaScriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import in.juspay.godel.ui.JuspayBrowserFragment;

/**
 * JuspaySafe Browser Fragment extending {@link in.juspay.godel.ui.JuspayBrowserFragment}.
 */
public class JuspaySafeBrowser extends JuspayBrowserFragment {

    private static final String TAG = JuspaySafeBrowser.class.getSimpleName();

    /**
     * Creates new instance of Fragment.
     */
    public JuspaySafeBrowser() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void loadWebView() {
        setupJuspayBackButtonCallbackInterface(new JuspayBackButtonCallback() {
            @Override
            public void transactionCancelled(JSONObject jsonObject) throws JSONException {
                ((PaymentActivity) getActivity()).returnResult(Activity.RESULT_CANCELED);
            }
        });
        getWebView().addJavascriptInterface(new JavaScriptInterface(getActivity()), "AndroidScriptInterface");
        getWebView().getSettings().setJavaScriptEnabled(true);
        Logger.d(TAG, "Loaded Webview");
    }
}
