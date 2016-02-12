package com.blanke.simplegank.core.category.model;

import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.bean.GankBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Blanke on 16-1-19.
 */
public abstract class CateGoryModel {

    public abstract Observable<List<GankBean>> loadGank(CateGoryBean cateGory, int size, int page);
}
