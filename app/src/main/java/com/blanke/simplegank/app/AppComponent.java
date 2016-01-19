package com.blanke.simplegank.app;

import com.blanke.simplegank.core.retrofit.GankApi;
import com.blanke.simplegank.core.retrofit.RetroFitModule;

import dagger.Component;

/**
 * Created by Blanke on 16-1-13.
 */


@Component(modules = {AppModule.class, RetroFitModule.class})
public interface AppComponent {
    void inject(BaseApplication app);

    GankApi getGankApi();
}
