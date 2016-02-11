package com.blanke.simplegank.core.category.presenter;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;
import com.blanke.simplegank.core.category.model.CateGoryModel;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by Blanke on 16-1-19.
 */
public class CateGoryPresenterImpl extends CateGoryPresenter {

    private boolean pullToRefresh;

    public CateGoryPresenterImpl(CateGoryModel mCateGoryModel) {
        super(mCateGoryModel);
    }

    @Override
    public void loadGank(boolean pullToRefresh, CateGoryBean cateGory, int size, int page) {
        this.pullToRefresh = pullToRefresh;
        getView().showLoading(pullToRefresh);
        mCateGoryModel.loadGank(cateGory, size, page, this);
    }


    @Override
    public void onSuccess(List<GankBean> data) {
        if (isViewAttached()) {
            KLog.d("数据获取完成，data.size=" + data.size());
            getView().setData(data);
            if (!pullToRefresh) {
                getView().showContent();
            }
            getView().stopRefreshing();
        }
    }

    @Override
    public void onFail(Throwable e) {
        if (isViewAttached()) {
            KLog.d("数据获取失败，error:" + e.getMessage());
//            getView().showError(e, pullToRefresh);
//            if (pullToRefresh) {
            getView().stopRefreshing();
            getView().onFail(e);
//            }
        }
    }
}
