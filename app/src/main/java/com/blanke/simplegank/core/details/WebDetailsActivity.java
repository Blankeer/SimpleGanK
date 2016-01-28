package com.blanke.simplegank.core.details;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.GankBean;

import butterknife.Bind;

public class WebDetailsActivity extends BaseActivity {
    public static String ARG_NAME = "WebDetailsActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_web_webview)
    WebView mWebView;

    private GankBean mGankBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mGankBean = getIntent().getParcelableExtra(ARG_NAME);
        setTitle(mGankBean.getDesc());
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
