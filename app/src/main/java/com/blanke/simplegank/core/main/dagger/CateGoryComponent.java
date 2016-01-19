package com.blanke.simplegank.core.main.dagger;

import com.blanke.simplegank.app.AppComponent;
import com.blanke.simplegank.core.main.CateGoryFragment;

import dagger.Component;

/**
 * Created by Blanke on 16-1-19.
 */
@Component(dependencies = AppComponent.class
        , modules = {CateGoryMVPModule.class})
public interface CateGoryComponent {
    void inject(CateGoryFragment fragment);
}
