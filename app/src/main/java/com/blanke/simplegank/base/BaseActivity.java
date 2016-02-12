package com.blanke.simplegank.base;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 15-12-30.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
//        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
//        refWatcher.watch(this);
    }
}
