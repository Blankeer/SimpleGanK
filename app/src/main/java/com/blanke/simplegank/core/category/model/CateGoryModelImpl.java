package com.blanke.simplegank.core.category.model;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.core.category.retrofit.GankApi;

import java.util.List;

import rx.Observable;
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
    public Observable<List<GankBean>> loadGank(CateGoryBean cateGory, int size, int page) {
        return mGankApi.getGankList(cateGory.getPath(), size, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(response -> response.getResults());
    }
}
