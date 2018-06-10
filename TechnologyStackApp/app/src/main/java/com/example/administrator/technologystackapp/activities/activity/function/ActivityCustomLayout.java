package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.CommonTitleBarLayout;

/**
 * 自定义ViewGroup实现公共的标题栏
 */
public class ActivityCustomLayout extends BaseActivity {

    private CommonTitleBarLayout cb_01, cb_02, cb_03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_title_bar);
        initView();
        initListener();
    }

    private void initView() {
        cb_01 = findViewById(R.id.cb_01);
        cb_02 = findViewById(R.id.cb_02);
        cb_03 = findViewById(R.id.cb_03);
    }

    private void initListener() {
        CommonTitleBarLayout.ButtonClickListener listener01 = new CommonTitleBarLayout.ButtonClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 01 left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 01 right", Toast.LENGTH_SHORT).show();
            }
        };
        cb_01.registerListener(listener01);

        CommonTitleBarLayout.ButtonClickListener listener02 = new CommonTitleBarLayout.ButtonClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 02 left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 02 right", Toast.LENGTH_SHORT).show();
            }
        };
        cb_02.registerListener(listener02);

        CommonTitleBarLayout.ButtonClickListener listener03 = new CommonTitleBarLayout.ButtonClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 03 left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(ActivityCustomLayout.this, "click 03 right", Toast.LENGTH_SHORT).show();
            }
        };
        cb_03.registerListener(listener03);
    }


}
