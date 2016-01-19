package com.blanke.simplegank.core.retrofit;

import com.blanke.simplegank.consts.StaticData;

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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GankApi.class);
    }
}
