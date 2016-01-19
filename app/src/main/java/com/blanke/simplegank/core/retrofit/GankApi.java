package com.blanke.simplegank.core.retrofit;

import com.blanke.simplegank.bean.BaseGankResponseBean;
import com.blanke.simplegank.consts.StaticData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Blanke on 16-1-19.
 */
public interface GankApi {
    String API_URL = StaticData.GANK_URL;

    @GET(API_URL + "{type}/{size}/{page}")
    Observable<BaseGankResponseBean> getGankList(@Path("type") String type,
                                                 @Path("size") int size,
                                                 @Path("page") int page);
}
