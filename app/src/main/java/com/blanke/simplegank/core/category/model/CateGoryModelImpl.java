package com.blanke.simplegank.core.category.model;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.core.retrofit.GankApi;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryModelImpl extends CateGoryModel {
    private GankApi mGankApi;

    public CateGoryModelImpl(GankApi mGankApi) {
        this.mGankApi = mGankApi;
    }

    @Override
    public void loadGank(CateGoryBean cateGory, int size, int page, CallBack callBack) {
        mGankApi.getGankList(cateGory.getPath(), size, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(response -> response.getResults())
                .subscribe(callBack::onSuccess, callBack::onFail);
    }
}
