package com.blanke.simplegank.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 15-12-30.
 */
public abstract class BaseMvpLceActivity<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>> extends MvpLceActivity<CV, M, V, P> {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
