package com.blanke.simplegank.core.category.retrofit;

import com.blanke.simplegank.consts.StaticData;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Blanke on 16-1-19.
 */
@Module
public class RetroFitModule {

    @Provides
    public GankApi provideGankApi() {
        return new Retrofit.Builder()
                .baseUrl(StaticData.ROOT_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder()
                                .setDateFormat(StaticData.GANK_DATE_FORMAT)
                                .create()))
                .build()
                .create(GankApi.class);
    }
}
