package com.blanke.simplegank.app;

import android.app.Application;
import android.content.Context;

import com.blanke.simplegank.consts.StaticData;

import java.io.IOException;

/**
 * Created by Blanke on 16-1-13.
 */
public class BaseApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        initData();
    }

    private void initData() {
        try {
            StaticData.init(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static BaseApplication getApplication(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }
}

