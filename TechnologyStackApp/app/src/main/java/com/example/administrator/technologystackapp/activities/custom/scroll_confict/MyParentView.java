package com.example.administrator.technologystackapp.activities.custom.scroll_confict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.technologystackapp.activities.custom.MyListView;

public class MyParentView extends LinearLayout {

    private int mMove;
    private int yDown, yMove;
    private int i = 0;

    public MyParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        boolean isIntercept = false;// 默认不拦截,这个变量只能放在方法内部作为局部变量，因为如果作为全局变量的话，子组件内部有可能划不动;至于是啥原因，我还没想明白
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown < 0) {// 上滑动作不拦截
                    isIntercept = false;
                } else if (yMove - yDown > 0) { // 下滑动作拦截住，不要让下层知道有一个下滑事件
                    //下滑动作？除非我这里可以判断内部的listView是不是已经到了顶端
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        Log.d("onInterceptTouchEvent", "isIntercept:" + isIntercept);

        return isIntercept;
    }

    /**
     * 重写onTouchEvent获取屏幕事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();// 取得Y轴坐标值
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if ((yMove - yDown) > 0) {
                    mMove = yMove - yDown;
                    i += mMove;
                    layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
                }
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                i = 0;
                break;
        }
        return true;
    }

}