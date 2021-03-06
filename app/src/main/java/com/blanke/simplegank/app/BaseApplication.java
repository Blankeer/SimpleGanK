package com.blanke.simplegank.app;

import android.app.Application;
import android.content.Context;

import com.blanke.simplegank.consts.StaticData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.socks.library.KLog;
import com.squareup.leakcanary.RefWatcher;

import java.io.IOException;

/**
 * Created by Blanke on 16-1-13.
 */
public class BaseApplication extends Application {

    private AppComponent mAppComponent;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initComponent();
        initImageLoader();
        KLog.init(true);
//        refWatcher = LeakCanary.install(this);
    }

    private void initData() {
        try {
            StaticData.init(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(800,1000)
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 10 * 1000))
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
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

