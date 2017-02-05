package com.example.erel_yonah.findatrip.controller;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.erel_yonah.findatrip.R;

import static java.lang.Thread.sleep;

public class WebViewActivity extends AppCompatActivity {

    //a final string for the intent extras
    final String WEB_URL = "website";

    //the web view
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //set activity title as the website address in the intent
        setTitle(getIntent().getExtras().getString(WEB_URL));

        //catch the web-view's view
        browser = (WebView) findViewById(R.id.webView);

        //set setting of the web view
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //load url
        browser.loadUrl("http://www."+getIntent().getExtras().getString(WEB_URL));

        //set the "back" button in the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //define how the web view opens the url
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    //define that clicking on the device back
    //button will work inside the web-view
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (browser.canGoBack()) {
                        browser.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    ////what happens when clicking on the "back" button of the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
