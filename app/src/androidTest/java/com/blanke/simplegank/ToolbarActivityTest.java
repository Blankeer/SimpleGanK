package com.blanke.simplegank;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import com.blanke.simplegank.test.TestToolbarActivity;

/**
 * Created by Blanke on 16-1-22.
 */
public class ToolbarActivityTest extends InstrumentationTestCase {
    Instrumentation instrumentation;
    private TestToolbarActivity mActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        mActivity = launchActivity("com.blanke.simplegank", TestToolbarActivity.class, null);
    }

    public void testName() throws Exception {
        Thread.sleep(3000);

    }

    private void runOnMain(Runnable runnable) {
        instrumentation.runOnMainSync(runnable);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        Thread.sleep(100000);
    }

}

