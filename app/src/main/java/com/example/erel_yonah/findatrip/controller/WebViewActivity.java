package com.example.erel_yonah.findatrip.controller;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.erel_yonah.findatrip.R;

import static java.lang.Thread.sleep;

public class WebViewActivity extends AppCompatActivity {

    final String WEB_URL = "website";
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        browser = (WebView) findViewById(R.id.webView);
        //browser.getSettings().setJavaScriptEnabled(true);
        //browser.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        browser.loadUrl(getIntent().getExtras().getString(WEB_URL));
        setTitle(browser.getUrl());
    }

}
