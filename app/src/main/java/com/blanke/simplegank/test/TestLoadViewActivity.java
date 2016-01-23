package com.blanke.simplegank.test;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.view.CustomSmoothProgressBar;

import butterknife.Bind;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class TestLoadViewActivity extends BaseActivity {
    @Bind(R.id.activity_test_loadview)
    public CustomSmoothProgressBar loadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loadview);
    }
}
