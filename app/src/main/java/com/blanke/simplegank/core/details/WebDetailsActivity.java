package com.blanke.simplegank.core.details;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.utils.ResUtils;
import com.blanke.simplegank.view.CustomSmoothProgressBar;
import com.socks.library.KLog;

import butterknife.Bind;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class WebDetailsActivity extends BaseActivity {
    public static String ARG_NAME = "WebDetailsActivity";

    @Bind(R.id.toolbar2)
    Toolbar toolbar;
    @Bind(R.id.activity_web_webview)
    WebView mWebView;
    @Bind(R.id.loadingSmoothView)
    CustomSmoothProgressBar mSmoothProgressBar;
//    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private GankBean mGankBean;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_details);

        Explode mExplode = new Explode();
        mExplode.setDuration(500);
        getWindow().setExitTransition(mExplode);
        getWindow().setEnterTransition(mExplode);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mGankBean = getIntent().getParcelableExtra(ARG_NAME);

//        mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
//        mCollapsingToolbarLayout.setExpandedTitleColor(ResUtils.getColorById(this, R.color.colorPrimary));
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
//
//        mCollapsingToolbarLayout.setTitle(mGankBean.getDesc());

        mSmoothProgressBar.setShow(true);
        mWebView.loadUrl(mGankBean.getUrl());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mSmoothProgressBar != null) {
                    mSmoothProgressBar.setShow(false);
                    mSmoothProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
