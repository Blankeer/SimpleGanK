package com.blanke.simplegank.core.details;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.view.CustomSmoothProgressBar;
import com.socks.library.KLog;

import butterknife.Bind;

public class WebDetailsActivity extends BaseActivity {
    public static String ARG_NAME = "WebDetailsActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_web_webview)
    WebView mWebView;
    @Bind(R.id.loadingSmoothView)
    CustomSmoothProgressBar mSmoothProgressBar;
    private GankBean mGankBean;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void start(Activity activity, GankBean bean) {
        Explode mExplode = new Explode();
        mExplode.setDuration(500);
        activity.getWindow().setExitTransition(mExplode);
        activity.getWindow().setEnterTransition(mExplode);
        ActivityOptionsCompat optionsCompat2 = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent2 = new Intent(activity, WebDetailsActivity.class);
        intent2.putExtra(WebDetailsActivity.ARG_NAME, bean);
        activity.startActivity(intent2, optionsCompat2.toBundle());
    }

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
        setTitle(mGankBean.getDesc());

        mSmoothProgressBar.setShow(true);
        mWebView.loadUrl(mGankBean.getUrl());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
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
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KLog.d();
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mWebView.stopLoading();
        mWebView.destroy();
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
