package com.blanke.simplegank.core.main;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.consts.StaticData;
import com.blanke.simplegank.core.category.CateGoryFragment;
import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.melnykov.fab.FloatingActionButton;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    private String KEY_SAVESTATUS = "main_savestatus";
    @Bind(R.id.activity_main_testlayout)
    ScalpelFrameLayout mTestLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_main_framelayout)
    FrameLayout activityMainFramelayout;
    @Bind(R.id.activity_main_navigation)
    NavigationView activityMainNavigation;
    @Bind(R.id.activity_main_drawerlayout)
    DrawerLayout activityMainDrawerlayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    ImageView mSvg;

    private List<CateGoryBean> mCateGoryBeens;//分类

    private int mSelectPostion = -1;
    private CateGoryFragment mSelectFragment;
    private AnimatedVectorDrawable mAnimatedVectorDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, activityMainDrawerlayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        activityMainDrawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDrawerOpened(View drawerView) {// 打开drawer
                mActionBarDrawerToggle.onDrawerOpened(drawerView);//开关状态改为opened
                if (mAnimatedVectorDrawable != null) {
                    mAnimatedVectorDrawable.start();
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDrawerClosed(View drawerView) {// 关闭drawer
                mActionBarDrawerToggle.onDrawerClosed(drawerView);//开关状态改为closed
                if (mAnimatedVectorDrawable != null) {
                    mAnimatedVectorDrawable.stop();
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {// drawer滑动的回调
                mActionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {// drawer状态改变的回调
                mActionBarDrawerToggle.onDrawerStateChanged(newState);
            }
        });
        mCateGoryBeens = new ArrayList<>(StaticData.getCateGoryBeens());
        activityMainNavigation.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            replaceFragment(item.getOrder());
            activityMainDrawerlayout.closeDrawers();
            return true;
        });


        setTranslucentStatus();

        initNavigationMenu();

        replaceFragment(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//svg动画
            activityMainNavigation.post(() -> {
                mSvg = (ImageView) activityMainNavigation.findViewById(R.id.head_svg);
                mAnimatedVectorDrawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.svg_an);
                if (mAnimatedVectorDrawable != null) {
                    mSvg.setImageDrawable(mAnimatedVectorDrawable);
                }
            });
        }

    }


    private void replaceFragment(int index) {
        if (index != mSelectPostion) {
            mSelectPostion = index;
            CateGoryBean item = mCateGoryBeens.get(index);
            toolbar.setTitle(item.getName());
            mSelectFragment = CateGoryFragment.getInstance(item);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_framelayout, mSelectFragment)
                    .commit();
            EventBus.getDefault().postSticky(fab, CateGoryFragment.fab_init);//传递一个延迟任务给fragment,当fragment注册之后获得fab，并attch recyclerview
        }
    }

    private void initNavigationMenu() {
        Menu menu = activityMainNavigation.getMenu();
        int random = (int) (Math.random() * 9 + 1);
        int idbase = random << 10;
        int i = 0;
        for (CateGoryBean item : mCateGoryBeens) {
            MenuItem temp = menu.add(0, idbase + i, i, item.getName());
            temp.setIcon(item.getIconResId());
            if (i == 0) {
                temp.setChecked(true);
            }
            i++;
        }
        menu.setGroupCheckable(0, true, true);//single
    }

    //状态栏
    private void setTranslucentStatus() {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(R.color.colorPrimary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}