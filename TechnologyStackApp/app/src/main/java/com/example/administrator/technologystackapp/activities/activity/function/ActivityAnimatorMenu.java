package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.MyAnimatorMenu;

/**
 * 动态菜单-属性动画
 */
public class ActivityAnimatorMenu extends BaseActivity {

    private MyAnimatorMenu myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animator_menu);

        myLinearLayout = findViewById(R.id.ml_menu);
        myLinearLayout.preset(getResources().getDrawable(R.drawable.f6), lis01);// 预设3个按钮，指定它的图片和事件
        myLinearLayout.preset(getResources().getDrawable(R.drawable.f3), lis02);
        myLinearLayout.preset(getResources().getDrawable(R.drawable.f4), lis03);
        myLinearLayout.initLayout(this, getResources().getDrawable(R.drawable.f1));
    }

    View.OnClickListener lis01 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("myLinearLayout", "click 01");
        }
    };

    View.OnClickListener lis02 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("myLinearLayout", "click 02");
        }
    };

    View.OnClickListener lis03 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("myLinearLayout", "click 03");
        }
    };

}
