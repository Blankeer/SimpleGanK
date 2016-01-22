package com.blanke.simplegank.test;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.view.CustomSmoothProgressBar;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class TestLoadViewActivity extends BaseActivity {

    public CustomSmoothProgressBar loadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadView = (CustomSmoothProgressBar) LayoutInflater.from(this).inflate(R.layout.view_loading_smooth, null);
        setContentView(loadView);
    }
}
