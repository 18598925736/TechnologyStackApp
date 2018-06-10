package com.example.administrator.technologystackapp.activities.custom.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class MyDanMuLayout extends RelativeLayout {

    private Context mContext;

    /*********************重写构造函数********************/
    public MyDanMuLayout(Context context) {
        this(context, null);
    }

    public MyDanMuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDanMuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setWidthObserverOfLayout();
    }

    private int measuredWidthOfDanMuView;
    private int layoutWidth, layoutHeight;
    private boolean ifQueueOpen = true;
    private Handler mHandler = new Handler();

    /**
     * 全局监听，为了得出自定义layout的实际宽度和高度(这个宽度会用来计算弹幕需要移动多长距离，高度会用来计算 每条弹幕离顶部的距离)
     */
    private void setWidthObserverOfLayout() {
        //以本layout为根进行监听，当它的布局完成之后，执行逻辑，并且移除监听器
        ViewTreeObserver observer = this.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {// 当全局布局发生变化，或者子view的可见状态发生变化时，它就会被回调

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {//当检测到布局变化时，这个监听器就会回调
                Log.d("WidthObserverOfLayout", "执行widthObserverOfLayout");
                getViewTreeObserver().removeGlobalOnLayoutListener(this);//一旦被回调，立即移除监听,不然就会无限执行
                layoutWidth = getMeasuredWidth(); //获得当前自定义layout的实际宽度
                layoutHeight = getMeasuredHeight();//实际高度
                startDanMuLooper();//开始队列循环
            }
        });
    }


    //在这里设计一个队列机制，目的就是 将外界传进来的弹幕信息，都封装成Danmu对象，然后队列无限循环，显示弹幕
    private Queue<DanMu> danMuQueue = new LinkedList<>();
    private Timer timer;

    /**
     * 开始弹幕队列循环
     */
    private void startDanMuLooper() {
        if (null != timer) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //这里有一个无限循环的timer，它会无限地将danmuQueue中的弹幕，
                final DanMu danmu = danMuQueue.poll();
                if (null != danmu) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            initDanMu(mContext, danmu.content, danmu.distanceToTop, danmu.duration, danmu.textColor, danmu.bgColor, danmu.drawable, danmu.ifMine);
                        }
                    });
                }
            }
        }, 50, 100);//每100毫秒检测一次队列里面有没有弹幕
    }

    /**
     * 开始对View进行动画处理
     *
     * @param danMuView
     * @param duration
     * @param distanceToTopPercent
     */
    private void animate(final View danMuView, final int duration, final float distanceToTopPercent) {
        ViewTreeObserver observer = danMuView.getViewTreeObserver();//增加监听, 为了获得弹幕view的实际宽度
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                danMuView.getViewTreeObserver().removeOnGlobalLayoutListener(this);//一旦被回调，立即移除监听,不然就会无限执行
                measuredWidthOfDanMuView = danMuView.getMeasuredWidth();//得出测量之后的宽度值

                // 借助RelativeLayout.LayoutParam 让它总是在右侧外围
                LayoutParams layoutParams = (LayoutParams) danMuView.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                layoutParams.topMargin = (int) (distanceToTopPercent * layoutHeight);//设定离父组件最上方的距离
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = -measuredWidthOfDanMuView;
                danMuView.requestLayout();//强制刷新布局

                //然后执行动画
                startMovement(danMuView, duration);//拿到宽度之后才能进行动画执行，因为弹幕textview的移动距离依赖 layoutWidth
            }
        });
    }

    /**
     * 弹幕初始化
     */
    private void initDanMu(Context ctx, String danMuContent, final float distanceToTopPercent, final int duration, int textColor, int bgColor, Drawable drawable, boolean ifMine) {
        View danMuView;
        if (ifQueueOpen) {
            if (drawable == null) {//先看看有没有图，有图的话，就创建ImageView，否则就创建TextView

                TextView tv_danMuTemp = new TextView(ctx);//创建一个弹幕textView,这里的TextView是原生的，其实我可以做成自定义的TextView
                tv_danMuTemp.setText(danMuContent);//设置文字
                tv_danMuTemp.setTextColor(textColor);//设置文字颜色
                tv_danMuTemp.setBackgroundColor(bgColor);//设置边框颜色

                if (ifMine)//如果是本人发的，就加上边框
                    if (Build.VERSION.SDK_INT > 19)
                        tv_danMuTemp.setBackground(ctx.getDrawable(R.drawable.textview_background));
                    else
                        tv_danMuTemp.setBackground(ContextCompat.getDrawable(mContext, R.drawable.textview_background));

                danMuView = tv_danMuTemp;

            } else {
                ImageView iv_danMuTemp = new ImageView(ctx);
                iv_danMuTemp.setImageDrawable(drawable);
                if (ifMine)//如果是本人发的，就加上边框
                    if (Build.VERSION.SDK_INT > 19)
                        iv_danMuTemp.setBackground(ctx.getDrawable(R.drawable.textview_background));
                    else
                        iv_danMuTemp.setBackground(ContextCompat.getDrawable(mContext, R.drawable.textview_background));
                iv_danMuTemp.setMaxWidth(100);
                iv_danMuTemp.setMaxHeight(100);

                danMuView = iv_danMuTemp;
            }

            //下面的代码是为了将新产生的view 刚好 隐藏到本layout最右边
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//设置初始布局
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//相对布局的参数：靠右
            layoutParams.rightMargin = -layoutWidth;//并且继续往右，直到隐藏, 这里的layoutWidth实际上一般超出了组件的宽度，但是为了保证初始位置在视野之外，必须用超出组建宽度的值
            addView(danMuView, layoutParams);//添加弹幕textView

            animate(danMuView, duration, distanceToTopPercent);
        }
    }


    List<Animator> allAnimator = new ArrayList<>();// 为了对所有的动画对象进行管理而设定的list

    /**
     * 开始移动
     *
     * @param tv_danMuTemp
     */
    private void startMovement(final View tv_danMuTemp, int duration) {
        float targetDistance = layoutWidth + measuredWidthOfDanMuView;// 计算总共需要移动的距离
        final ObjectAnimator animator = ObjectAnimator.ofFloat(tv_danMuTemp, "translationX", -targetDistance);//定义移动动画ObjectAnimator
        animator.setDuration(duration * 1000);//时长
        animator.setInterpolator(new LinearInterpolator());//匀速移动
        animator.addListener(new AnimatorListenerAdapter() {// 动画监听器，重写适配器
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(tv_danMuTemp);//一旦动画执行完毕，立即移除该view
                Log.d("onAnimationEnd", "getChildCount:" + getChildCount());//打印移除之后还剩下多少
                allAnimator.remove(animator);
            }
        });
        animator.start();//一切就绪，开始动画
        allAnimator.add(animator);
    }

    /******************************************************* 以下是所有公开接口 ************************************************/

    /**
     * 向外开放一个接口，发送弹幕
     *
     * @param content   文字内容
     * @param textColor 文字颜色
     * @param bgColor   textView背景颜色
     */
    public void sendDanMu(String content, int textColor, int bgColor, boolean ifMine) {
        if (!ifQueueOpen)
            return;
        DanMu danmu = new DanMu();
        danmu.content = content;
        danmu.distanceToTop = (float) Math.random();
        danmu.duration = 10;
        danmu.textColor = textColor;
        danmu.bgColor = bgColor;
        danmu.ifMine = ifMine;
        danMuQueue.add(danmu);//将弹幕对象添加到队列中
    }

    /**
     * 送飞机
     */
    public void sendPlane(boolean ifMine) {
        if (!ifQueueOpen)
            return;
        DanMu danmu = new DanMu();
        danmu.distanceToTop = (float) Math.random();
        danmu.duration = 10;
        danmu.ifMine = ifMine;
        danmu.drawable = mContext.getResources().getDrawable(R.drawable.plane);
        danMuQueue.add(danmu);//将弹幕对象添加到队列中
    }

    /**
     * 隐藏所有弹幕view
     */
    public void hideDanMu() {
        ifQueueOpen = false;
        danMuQueue.clear();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示所有弹幕view
     */
    public void showDanMu() {
        ifQueueOpen = true;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 引用本layout的activity，在onDestroy中一定要调用资源释放接口
     */
    public void release() {
        if (null != timer) {
            timer.cancel();
            timer.purge();
        }
        //这里还要取消所有未完成的动画
        Log.d("allAnimator", "" + allAnimator.size());
        // 逐个删除元素，不要用foreach，因为foreach内部机制是使用 iterator 遍历器，可能会报错ConcurrentModificationException
        for (int i = 0; i < allAnimator.size(); i++) {
            allAnimator.get(i).cancel();
        }
    }

}
