package com.latinmaps.app.ui.activities;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;

/**
 * Created by Administrator on 1/27/2016.
 */
public class BusinessWebsite extends LatinMaps {

    String url;
    WebView webView;
    LinearLayout progress;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_business_website;
    }

    @Override
    public void init() {
        super.init();

        url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.web_view);
        progress = (LinearLayout) findViewById(R.id.url_progress);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });



    }
}
