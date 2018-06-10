package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.PColumn;

/**
 * 自绘View
 */
public class ActivityCustomView extends BaseActivity {

    private PColumn column1, column2, column3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_extends_view);

        column1 = findViewById(R.id.pc_01);
        column2 = findViewById(R.id.pc_02);
        column3 = findViewById(R.id.pc_03);

        column1.setData(10, 100);
        column2.setData(50, 100);
        column3.setData(80, 100);
    }

}
