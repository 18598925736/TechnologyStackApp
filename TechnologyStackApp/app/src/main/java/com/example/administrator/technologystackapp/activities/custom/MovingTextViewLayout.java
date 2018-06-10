package com.example.administrator.technologystackapp.activities.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 移动的TextView效果
 */
public class MovingTextViewLayout extends LinearLayout {

    private Context context;

    public MovingTextViewLayout(Context context) {
        this(context, null);
    }

    public MovingTextViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MovingTextViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 这个控件，它自身是一个layout，内部包含一个TextView
     */
    private void init(Context context) {
        final TextView textView = new TextView(context);
        textView.setText("这是测试用的");
//        textView.setTextColor(Color.parseColor("#FFFFFF"));
//        textView.setBackgroundColor(Color.parseColor("#AAAAAA"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(textView, params);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int bigW = getMeasuredWidth();//整个layout的宽度
                int w = textView.getMeasuredWidth();//textView的宽度
                Log.d("textView", bigW + "-" + w);

                // 开始运行走马灯
                marquee(0, -(bigW - w));
            }
        });
    }

    ValueAnimator va;

    private void marquee(int start, int end) {
        va = ValueAnimator.ofInt(start, end, start);//让textView从左到右，再到左
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                Log.d("MarqueeLayout", "current:" + current);
                scrollTo(current, 0);
            }
        });
        va.setDuration(5000);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.start();
    }

    public void release() {
        Log.d("MarqueeLayout", "release");
        va.end();
        va.removeAllUpdateListeners();
    }

    RecyclerView.LayoutManager layoutManager;

    private void test() {
        layoutManager.getFocusedChild();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }
}
