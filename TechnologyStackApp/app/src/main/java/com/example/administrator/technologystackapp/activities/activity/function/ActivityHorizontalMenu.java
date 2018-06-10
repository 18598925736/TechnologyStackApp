package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向拽出的菜单按钮
 */
public class ActivityHorizontalMenu extends BaseActivity {

    ListView lv_1, lv_2, lv_3, lv_4, lv_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_layout);
        initData();
        init();
    }

    private void init() {
        lv_1 = findViewById(R.id.lv_1);
        lv_2 = findViewById(R.id.lv_2);
        lv_3 = findViewById(R.id.lv_3);
        lv_4 = findViewById(R.id.lv_4);
        lv_5 = findViewById(R.id.lv_5);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        lv_1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);
        lv_2.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data3);
        lv_3.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data4);
        lv_4.setAdapter(adapter4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data5);
        lv_5.setAdapter(adapter5);
    }

    private List<String> data1, data2, data3, data4, data5;

    private void initData() {
        data1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data1.add("d1-" + i);
        }
        data2 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data2.add("d2-" + i);
        }
        data3 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data3.add("d3-" + i);
        }
        data4 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data4.add("d4-" + i);
        }
        data5 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data5.add("d5-" + i);
        }
    }
}
