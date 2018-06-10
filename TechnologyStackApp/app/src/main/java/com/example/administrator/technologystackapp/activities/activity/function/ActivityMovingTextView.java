package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.MovingTextViewLayout;

/**
 * 横向拽出的菜单按钮
 */
public class ActivityMovingTextView extends BaseActivity {

    private MovingTextViewLayout marquee_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moving_textview);
        initView();
    }


    private void initView() {
        marquee_layout = findViewById(R.id.marquee_layout);
    }

}
