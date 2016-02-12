package com.blanke.simplegank.core.category.dagger;

import com.blanke.simplegank.core.category.model.CateGoryModel;
import com.blanke.simplegank.core.category.model.CateGoryModelImpl;
import com.blanke.simplegank.core.category.presenter.CateGoryPresenter;
import com.blanke.simplegank.core.category.presenter.CateGoryPresenterImpl;
import com.blanke.simplegank.core.category.retrofit.GankApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Blanke on 16-1-19.
 */
@Module
public class CateGoryMVPModule {

    @Provides
    public CateGoryModel provideCateGoryModel(GankApi gankApi) {
        return new CateGoryModelImpl(gankApi);
    }

    @Provides
    public CateGoryPresenter provideCateGoryPresenter(CateGoryModel model) {
        return new CateGoryPresenterImpl(model);
    }
}
