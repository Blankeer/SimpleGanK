package com.blanke.simplegank.app;

import dagger.Component;

/**
 * Created by Blanke on 16-1-13.
 */


@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(BaseApplication app);

}
