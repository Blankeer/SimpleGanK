package com.blanke.simplegank.base;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import butterknife.ButterKnife;

/**
 * Created by Blanke on 16-1-7.
 */
public abstract class BaseMvpViewStateActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpViewStateActivity<V, P> {
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
