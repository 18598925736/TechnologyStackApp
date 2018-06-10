package com.example.administrator.technologystackapp.activities.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.technologystackapp.activities.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAnimatorMenu extends LinearLayout {

    /****************************构造函数****************/
    public MyAnimatorMenu(Context context) {
        this(context, null);
    }

    public MyAnimatorMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAnimatorMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayoutParameters(context);
    }

    /****************************私有函数********************************/
    private void initLayoutParameters(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);// 线性布局-横向
        setPadding(DensityUtils.dipTopx(context, 10f), DensityUtils.dipTopx(context, 10f),
                DensityUtils.dipTopx(context, 10f), DensityUtils.dipTopx(context, 10f));
    }

    private ImageView iv_head;// 主按钮
    private float startDegree, endDegree;//开始旋转的角度以及结束的角度
    private List<View> childrenMenuIconList = new ArrayList<>();// 已经绘制好的子view
    private boolean ifMenuOpened = false;//菜单是否打开了
    private float startX, endX;
    private boolean ifAnimateOver = true;//动画是否执行完毕(未完毕期间不接受点击事件)
    private int animationDuration = 300;// 动画执行的时长

    /**
     * 旋转主按钮
     *
     * @param iv_01
     */
    private void rotateMenuIcon(ImageView iv_01) {
        if (ifMenuOpened) {
            startDegree = 90;
            endDegree = 0;
        } else {
            startDegree = 0;
            endDegree = 90;
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_01, "rotation", startDegree, endDegree);
        objectAnimator.setDuration(animationDuration);
        objectAnimator.start();
    }

    private void setIfAnimateOver(boolean temp) {
        ifAnimateOver = temp;
    }

    /**
     * 绘制子menu
     */
    private void drawChildMenuIcon(Context context, Drawable drawable, OnClickListener listener) {
        setIfAnimateOver(false);
        AnimatorSet animatorSet = new AnimatorSet();
        final ImageView iv_menu = new ImageView(context);
        iv_menu.setOnClickListener(listener);
        iv_menu.setImageDrawable(drawable);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);//宽高自适应
        layoutParams.width = DensityUtils.dipTopx(context, 60);
        layoutParams.height = DensityUtils.dipTopx(context, 60);
        addView(iv_menu, layoutParams);
        childrenMenuIconList.add(iv_menu);

        //我需要的是横向的起始X和终点X
        startX = -layoutParams.width;
        endX = 0;

        //ObjectAnimator 的 value参数，它这里有个坑，如果你是想让它慢慢显示，必须写0,1，而不能只写1.
        //但是如果需要让它慢慢隐藏，可以只写0
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv_menu, "alpha", 0, 1);
        objectAnimator2.setDuration(animationDuration);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_menu, "translationX", startX, endX);
        objectAnimator.setDuration(animationDuration);

        animatorSet.playTogether(objectAnimator2, objectAnimator);//同时执行
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setIfAnimateOver(true);
            }
        });
        animatorSet.start();
        Log.d("iv_menu", "click lis end");
    }

    private List<Animator> listAnimator = new ArrayList<>();

    private void hideChildMenu() {
        listAnimator.clear();
        setIfAnimateOver(false);
        for (final View v : childrenMenuIconList) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "translationX", endX, startX);
            objectAnimator.setDuration(animationDuration);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Log.d("hideChildMenu", "" + v.getHeight() + "-" + v.getWidth());
                    removeView(v);
                }
            });
            listAnimator.add(objectAnimator);

            objectAnimator = ObjectAnimator.ofFloat(v, "alpha", 0);
            objectAnimator.setDuration(animationDuration);
            listAnimator.add(objectAnimator);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(listAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setIfAnimateOver(true);
            }
        });
        animatorSet.start();
        childrenMenuIconList.clear();//最后将childrenList清空
    }

    /*******************************辅助内部类***************************************/
    private List<DrawLis> drawLisList = new ArrayList<>();//即将绘制的view的相关参数

    class DrawLis {
        Drawable drawable;
        OnClickListener listener;

        DrawLis(Drawable drawableT, OnClickListener listenerT) {
            drawable = drawableT;
            listener = listenerT;
        }
    }
    /****************************公开接口********************************/
    /**
     * 新增一个按钮以及一个监听
     *
     * @param drawable
     * @param listener
     */
    public void preset(Drawable drawable, OnClickListener listener) {
        drawLisList.add(new DrawLis(drawable, listener));
    }

    public void initLayout(final Context context, Drawable drawable) {
        iv_head = new ImageView(context);
        iv_head.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//设置等比例缩放，不会改变原图的纵横比
        iv_head.setImageDrawable(drawable);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = DensityUtils.dipTopx(context, 60);//设置为60dp宽
        params.height = DensityUtils.dipTopx(context, 60);//设置为60dp高
        addView(iv_head, params);//将主按钮加入布局

        iv_head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 它的点击事件，点击之后，展开其他3个按钮
                if (ifAnimateOver)//如果动画执行完毕了，那就可以进行下面的操作
                {
                    rotateMenuIcon(iv_head);// 让主按钮产生旋转效果
                    if (!ifMenuOpened) {
                        for (DrawLis lis : drawLisList) {
                            drawChildMenuIcon(context, lis.drawable, lis.listener);//逐一添加child
                        }
                        ifMenuOpened = true;
                    } else {
                        hideChildMenu();
                        ifMenuOpened = false;
                    }
                }
            }
        });
    }

}
