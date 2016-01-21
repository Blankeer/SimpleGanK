package com.blanke.simplegank.core.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.bean.CateGoryBean;
import com.blanke.simplegank.consts.StaticData;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    private List<CateGoryBean> mCateGoryBeens;//分类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, activityMainDrawerlayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        activityMainDrawerlayout.setDrawerListener(mActionBarDrawerToggle);
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
    }


    private void replaceFragment(int index) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_framelayout, CateGoryFragment.getInstance(mCateGoryBeens.get(index)))
                .commit();

    }

    private void initNavigationMenu() {
        Menu menu = activityMainNavigation.getMenu();
        int random = (int) (Math.random() * 9 + 1);
        int idbase = random << 10;
        int i = 0;
        for (CateGoryBean item : mCateGoryBeens) {
            MenuItem temp = menu.add(0, idbase + i, i, item.getName());
            if (i == 0) {
                temp.setChecked(true);
            }
            i++;
        }
        menu.setGroupCheckable(0, true, true);//single
    }

    //透明状态栏
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

    @OnClick(R.id.fab)
    public void onFabClick(View v) {
//        Snackbar.make(v, "snack  ", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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