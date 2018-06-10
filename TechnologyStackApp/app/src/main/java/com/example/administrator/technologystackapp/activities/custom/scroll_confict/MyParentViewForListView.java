package com.example.administrator.technologystackapp.activities.custom.scroll_confict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 带弹性的LinearLayout，里面如果放一个ListView 则可以让这个ListView具有下拉的弹性
 *
 * 这里涉及到了事件分发机制以及内外层的滑动冲突处理;
 *
 * 主要思路：让外层layout知道  何时应该拦截事件，进行弹性动画处理。何时应该不拦截事件，而让listView自己去处理滑动。
 */
public class MyParentViewForListView extends LinearLayout {

    private int mMove;
    private int yDown, yMove;
    private int i = 0;
    private boolean isIntercept = false;

    public MyParentViewForListView(Context context) {
        super(context);
    }

    public MyParentViewForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyParentViewForListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ListView listView;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        listView = (ListView) getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        //开始判断listView是否在顶端
        boolean ifOnTheTop = false;
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        View firstVisibleItemView = listView.getChildAt(0);
        if (firstVisiblePosition == 0)
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                ifOnTheTop = true;
            }

        // 再进行事件拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown > 0 && ifOnTheTop) {//关键是，拦截的时候，要凭条件，首先只拦截下滑的，而且，必须要是内部的listView滑到了顶端，才能拦截
                    if (!isIntercept) {
                        yDown = (int) ev.getY();
                        isIntercept = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                i = 0;
                isIntercept = false;
                break;
        }
        if (isIntercept) {
            mMove = yMove - yDown;
            i += mMove;
            layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
        }
        return isIntercept;
    }
}