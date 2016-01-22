package com.blanke.simplegank;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.Toast;

import com.blanke.simplegank.core.main.MainActivity;
import com.socks.library.KLog;

/**
 * Created by Blanke on 16-1-22.
 */
public class MainActivityTest extends InstrumentationTestCase {
    Instrumentation instrumentation;
    private MainActivity mainActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        mainActivity = launchActivity("com.blanke.simplegank", MainActivity.class, null);
        KLog.d("mainactivity  test start");
    }

    public void testName() throws Exception {
        View fab = mainActivity.findViewById(R.id.fab);
        Thread.sleep(3000);
        runOnMain(() -> fab.performClick());
        runOnMain(() -> Toast.makeText(mainActivity, "test end", Toast.LENGTH_SHORT).show());

    }

    private void runOnMain(Runnable runnable) {
        instrumentation.runOnMainSync(runnable);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        KLog.d("mainactivity  test end");
        Thread.sleep(10000);
    }

}

