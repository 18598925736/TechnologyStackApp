package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.adapter.MyAdapter;

public class ActivityRecyclerView extends BaseActivity {

    private RecyclerView rv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);

        rv_1 = findViewById(R.id.rv_1);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_1.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter(this);
        rv_1.setAdapter(adapter);
    }


}
