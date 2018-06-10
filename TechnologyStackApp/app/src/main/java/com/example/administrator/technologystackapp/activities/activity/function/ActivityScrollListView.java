package com.example.administrator.technologystackapp.activities.activity.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.adapter.TitleListAdapter;
import com.example.administrator.technologystackapp.activities.bean.TextAndListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityScrollListView extends BaseActivity {
    private Context mContext;
    private ListView lv_title;
    private List<TextAndListener> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list_view);
        mContext = this;
        initData();
        initView();
    }

    private void initData() {
        list_data = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list_data.add(new TextAndListener("Content" + i, null));
        }

    }

    private void initView() {
        lv_title = findViewById(R.id.lv_title);
        TitleListAdapter titleListAdapter = new TitleListAdapter(mContext, list_data);
        lv_title.setAdapter(titleListAdapter);
    }
}
