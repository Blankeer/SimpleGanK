package com.blanke.simplegank.test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.blanke.simplegank.R;
import com.blanke.simplegank.base.BaseActivity;

import butterknife.Bind;

public class TestToolbarActivity extends BaseActivity {

    @Bind(R.id.test_toolbar_listview)
    ListView mListView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String[] data = new String[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = "item" + i;
        }
        ListAdapter mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        mListView.setAdapter(mAdapter);

    }


}
