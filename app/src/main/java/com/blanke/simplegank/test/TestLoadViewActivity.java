package com.blanke.simplegank.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;
import com.blanke.simplegank.view.CustomSmoothProgressBar;

import butterknife.Bind;

public class TestLoadViewActivity extends BaseActivity {
    //    @Bind(R.id.activity_test_loadview)
    public CustomSmoothProgressBar loadView;

    @Bind(R.id.activity_test_listview)
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loadview);


        loadView = (CustomSmoothProgressBar) LayoutInflater.from(this).inflate(R.layout.view_loading_smooth, null);

        mListView.addFooterView(loadView);

        String[] data = new String[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = new String("item" + i);
        }

        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
        mListView.deferNotifyDataSetChanged();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisitPosition = firstVisibleItem + visibleItemCount - 1;
                if (lastVisitPosition == data.length - 1) {
                    loadView.setShow(true);
                    mListView.setSelection(lastVisitPosition);
                    loadView.postDelayed(() -> loadView.setShow(false), 5000);
                }
            }
        });
    }
}
