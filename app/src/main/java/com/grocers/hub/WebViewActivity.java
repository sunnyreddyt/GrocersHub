package com.grocers.hub;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grocers.hub.utils.GHUtil;

public class WebViewActivity extends AppCompatActivity {


    private ImageView backImageView;
    private WebView aboutUsWebview;
    private GHUtil ghUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        ghUtil = GHUtil.getInstance(WebViewActivity.this);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        aboutUsWebview = (WebView) findViewById(R.id.aboutUsWebview);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ghUtil.showDialog(WebViewActivity.this);
        aboutUsWebview.clearCache(true);
        aboutUsWebview.clearHistory();
        aboutUsWebview.getSettings().setJavaScriptEnabled(true);
        aboutUsWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        aboutUsWebview.loadUrl(getIntent().getStringExtra("url"));

        aboutUsWebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                ghUtil.dismissDialog();
            }
        });
    }
}
