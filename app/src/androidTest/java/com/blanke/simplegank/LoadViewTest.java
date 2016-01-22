package com.blanke.simplegank;

import android.test.InstrumentationTestCase;

import com.blanke.simplegank.test.TestLoadViewActivity;
import com.blanke.simplegank.view.CustomSmoothProgressBar;
import com.socks.library.KLog;

import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

/**
 * Created by Blanke on 16-1-22.
 */
public class LoadViewTest extends InstrumentationTestCase {


    private TestLoadViewActivity testLoadViewActivity;
    private long time = 7 * 1000;
    private CustomSmoothProgressBar loadView;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testLoadViewActivity = launchActivity("com.blanke.simplegank", TestLoadViewActivity.class, null);
        loadView = testLoadViewActivity.loadView;
    }

    public void testLoadView() throws Exception {
//        SmoothProgressDrawable progressDrawable = (SmoothProgressDrawable) loadView.getIndeterminateDrawable();
        Thread.sleep(2000);
        loadView.setSmoothProgressDrawableUseGradients(true);

        loadView.setSmoothProgressDrawableCallbacks(loadView.new CustomCallBack() {
            @Override
            public void animStop() {
                KLog.d();
            }
        });
        getInstrumentation().runOnMainSync(() -> loadView.setShow(true));


        Thread.sleep(3000);
        getInstrumentation().runOnMainSync(() -> loadView.setShow(false));

//        loadView.progressiveStop();
//        progressDrawable.stop();
//        for(int i=0;i<100;i++){
//            loadView.setProgress(i);
//            Thread.sleep(100);
//        }

//        progressDrawable.stop();
//        loadView.progressiveStop();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        Thread.sleep(time);
    }
}
