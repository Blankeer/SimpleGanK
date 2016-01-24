package com.blanke.simplegank.core.category.model;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;

import java.util.List;

/**
 * Created by Blanke on 16-1-19.
 */
public abstract class CateGoryModel {
    public interface CallBack {
        void onSuccess(List<GankBean> data);

        void onFail(Throwable e);
    }

    public abstract void loadGank(CateGoryBean cateGory, int size, int page, CateGoryModel.CallBack callBack);
}
