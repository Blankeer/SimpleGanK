package com.blanke.simplegank.core.category.view;

import com.blanke.simplegank.bean.GankBean;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Blanke on 16-1-19.
 */
public interface CateGoryView extends MvpLceView<List<GankBean>> {

    void stopRefreshing();
    void onFail(Throwable e);
}
