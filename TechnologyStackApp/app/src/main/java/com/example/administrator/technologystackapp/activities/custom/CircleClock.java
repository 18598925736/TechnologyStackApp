package com.example.administrator.technologystackapp.activities.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Date;

public class CircleClock extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Paint mPaint, mPaint_face, mPaint_second, mPaint_minute, mPaint_hour;
    private static final String cloclColor = "#000000";
    // 子线程标志位
    private boolean mIsDrawing;//控制绘制过程的停和走
    private Canvas mCanvas;// 保存画布对象为全局变量

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(cloclColor));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPaint_face = new Paint();
        mPaint_face.setColor(Color.parseColor(cloclColor));
        mPaint_face.setStrokeWidth(4);
        mPaint_face.setStyle(Paint.Style.STROKE);
        mPaint_face.setAntiAlias(true);

        mPaint_second = new Paint();
        mPaint_second.setColor(Color.parseColor(cloclColor));
        mPaint_second.setStrokeWidth(4);
        mPaint_second.setStyle(Paint.Style.STROKE);
        mPaint_second.setAntiAlias(true);

        mPaint_minute = new Paint();
        mPaint_minute.setColor(Color.parseColor(cloclColor));
        mPaint_minute.setStrokeWidth(5);
        mPaint_minute.setStyle(Paint.Style.STROKE);
        mPaint_minute.setAntiAlias(true);

        mPaint_hour = new Paint();
        mPaint_hour.setColor(Color.parseColor(cloclColor));
        mPaint_hour.setStrokeWidth(6);
        mPaint_hour.setStyle(Paint.Style.STROKE);
        mPaint_hour.setAntiAlias(true);
    }

    public CircleClock(Context context) {
        super(context);
        initPaint();
        initView();
    }

    public CircleClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initView();
    }

    public CircleClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initView();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 一旦被创建成功，就启动动画
        reset();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            drawClock();//无限循环绘制指针
            try {
                Thread.sleep(500);//每隔1000MS绘制一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private SurfaceHolder holder;

    private void initView() {
        holder = getHolder();//获得holder对象
        holder.addCallback(this);//添加callback
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }


    private int radiusTarget = 80;
    private int currentRadius = 0;

    //详细的绘制过程

    /**
     * 这个myDraw方法会无限循环调用
     */
    private void drawClock() {
        try {
            mCanvas = holder.lockCanvas();//
            mCanvas.drawColor(Color.parseColor("#FFFFFF"));//绘制背景
            drawClockFace();
            drawPointer();

        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                holder.unlockCanvasAndPost(mCanvas);//释放，并且刷新surface
            }
        }
    }

    private void drawPointer() {

        //这里逻辑会发生变化，因为我打算将当前系统时间的时分秒提取出来，然后计算出各自的角度，再将3个指针绘制出来
        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();
        int second = date.getSeconds();

        //先把秒钟指针画出来
        // 如何把秒钟转化成角度
        mCanvas.rotate(second * 6);// 表盘一共360度。 一共60秒，所以每走一秒，度数就走6度
        mCanvas.drawLine(0, 0, radiusTarget * 7 / 10, 0, mPaint_second);//刻度的长度，设定为半径的1/10

        //再把分钟指针画出来

        //其实分钟数是一个小数，而不是int
        //算出真正的分钟数
        float realMinute = minute + second / 60.0f;
        Log.d("drawPointer", "" + realMinute);
        mCanvas.rotate(-second * 6);// 还得先把原来的角度转回去
        mCanvas.rotate((realMinute * 6));//再旋转分钟的角度,表盘一共360度。 一共60分，所以每走一分，度数就走6度
        mCanvas.drawLine(0, 0, radiusTarget * 6 / 10, 0, mPaint_minute);//

        hour = hour % 12;

        float realHour = hour + minute / 60.0f;
        mCanvas.rotate(-realMinute * 6);// 还得先把原来的角度转回去
        mCanvas.rotate((realHour * 30));//再旋转时钟的角度,表盘上一共12个小时，一共360度，所以每一个小时代表的是30度
        mCanvas.drawLine(0, 0, radiusTarget * 5 / 10, 0, mPaint_hour);//
    }

    /**
     * 画出表盘
     */
    private void drawClockFace() {
        //这些东西都是只需要绘制一次的
        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        mCanvas.drawCircle(cx, cy, currentRadius, mPaint);
        mCanvas.drawPoint(cx, cy, mPaint);

        mCanvas.translate(cx, cy);// 转移坐标轴中心，到原点处
        mCanvas.rotate(-90);//让指针从12点位置开始走,因为原始的是从3点位置。中间差了90度，所以需要逆时针旋转坐标90度
        for (int i = 1; i <= 60; i++) {//120次循环，绘制表盘
            mCanvas.rotate(6);//每一次旋转3度,
            if (i % 5 == 0) {//如果遇到整点，，1，2,3,4,5,6,7,8,9,10,11，12
                mCanvas.drawLine(radiusTarget * 8 / 10, 0, radiusTarget, 0, mPaint_face);//就用较粗的画笔画出较长的线条
            } else
                mCanvas.drawLine(radiusTarget * 9 / 10, 0, radiusTarget, 0, mPaint);//否则，就用较细的画笔画出较短的线条
        }
    }

    public void reset() {
        radiusTarget = getWidth() / 3;
        currentRadius = radiusTarget;
        mIsDrawing = true;
        new Thread(this).start();
    }
}
