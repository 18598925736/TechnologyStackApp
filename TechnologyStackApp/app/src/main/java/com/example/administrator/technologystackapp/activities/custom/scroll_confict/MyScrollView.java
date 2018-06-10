package com.example.administrator.technologystackapp.activities.custom.scroll_confict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("onInterceptTouchEvent", "MyScrollView:onTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int scrollY = getScrollY();//纵向滑动的顶端Y轴坐标值
                if (scrollY == 0) {//如果已经scroll到了顶端
                    //允许父View进行事件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);//是否禁止父组件拦截事件. false表示不禁止，也就是允许
                } else {
                    //禁止父View进行事件拦截
                    getParent().requestDisallowInterceptTouchEvent(true);//true表示禁止，不允许
                }
                break;
        }
        return super.onTouchEvent(ev);

    }
}