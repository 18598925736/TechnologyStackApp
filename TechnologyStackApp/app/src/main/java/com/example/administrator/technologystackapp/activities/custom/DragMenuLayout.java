package com.example.administrator.technologystackapp.activities.custom;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;

public class DragMenuLayout extends LinearLayout {

    private TextView tv_left, tv_right_menu;
    private int w1, w2;

    public DragMenuLayout(Context context) {
        this(context, null);
    }

    public DragMenuLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 0)
                child.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
            else
                child.layout(getMeasuredWidth(), 0, getMeasuredWidth() + child.getMeasuredWidth(), getMeasuredHeight());
        }
    }


    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                //取得横向滑动的距离
                float distance = startX - event.getX();
                slideLeft(distance);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void slideLeft(float distance) {
        scrollTo((int) distance, 0);
    }
}
