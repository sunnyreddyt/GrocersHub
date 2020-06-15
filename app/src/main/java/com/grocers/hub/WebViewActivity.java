package com.grocers.hub;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {


    private ImageView backImageView;
    private WebView aboutUsWebview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        backImageView = (ImageView) findViewById(R.id.backImageView);
        aboutUsWebview = (WebView) findViewById(R.id.aboutUsWebview);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aboutUsWebview.clearCache(true);
        aboutUsWebview.clearHistory();
        aboutUsWebview.getSettings().setJavaScriptEnabled(true);
        aboutUsWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        aboutUsWebview.loadUrl(getIntent().getStringExtra("url"));

    }
}
