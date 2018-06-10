package com.example.administrator.technologystackapp.activities.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.util.DensityUtils;

public class PColumn extends View {
    int MAX = 100;//最大
    int corner = 40;
    int data = 0;//显示的数
    int tempData = 0;
    int textPadding = 20;
    Paint mPaint;
    int mColor;

    Context mContext;

    //首先，构造函数和 编译器自动生成的方式有所不同
    public PColumn(Context context) {
        super(context);
        mContext = context;
    }

    public PColumn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public PColumn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mColor = mContext.getResources().getColor(R.color.colorPrimary);
        mPaint.setColor(mColor);
        setData(80, 100);
    }


    private int defaultHeight = 800;
    private int defaultWidth = 180;

    /**
     * 重写onMeasure，设定控件最小宽高值。
     * <p>
     * 因为当布局xml中对这个控件设置wrap_content，而 onMeasure方法并没有指定最小宽高值的话，该控件就会默认match_parent.
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(defaultWidth, widthMeasureSpec);
        int height = measureDimension(defaultHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);//重写onMeasure一定要调用setMeasuredDimension（）。
    }


    public int measureDimension(int defaultSize, int measureSpec) {
        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {//如果直接指定了宽度,比如100dp
            result = specSize;
        } else {
            result = defaultSize;   //UNSPECIFIED 设定一个默认值
            if (specMode == MeasureSpec.AT_MOST) {//如果设定宽度match_parent
                result = Math.min(result, specSize);
            }
        }
        //如果既没有指定宽度，也没有设定match_parent，那么，就用之前设定好的默认值
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == 0) {
            mPaint.setTextSize(getWidth() / 2);
            RectF oval3 = new RectF(0, getHeight() - DensityUtils.pxTodip(mContext, 20), getWidth(), getHeight());// 设置个新的长方形
            //圆角长方形，醉了，drawRoundRect
            canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner), DensityUtils.pxTodip(mContext, corner), mPaint);

            canvas.drawText("0",
                    getWidth() * 0.5f - mPaint.measureText("0") * 0.5f,
                    getHeight() - DensityUtils.pxTodip(mContext, 20) - 2 * DensityUtils.pxTodip(mContext, textPadding),
                    mPaint);
            return;
        }

        //防止数值很大的的时候，动画时间过长
        int step = data / 100 + 1;

        if (tempData < data - step) {
            tempData = tempData + step;
        } else {
            tempData = data;
        }
        //画圆角矩形
        String S = tempData + "";
        //一个字和两,三个字的字号相同
        if (S.length() < 4) {
            mPaint.setTextSize(getWidth() / 2);
        } else {
            mPaint.setTextSize(getWidth() / (S.length() - 1));
        }

        float textH = mPaint.ascent() + mPaint.descent();
        float MaxH = getHeight() - textH - 2 * DensityUtils.pxTodip(mContext, textPadding);
        //圆角矩形的实际高度
        float realH = MaxH / MAX * tempData;
        RectF oval3 = new RectF(0, getHeight() - realH, getWidth(), getHeight());// 设置个新的长方形
        canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner), DensityUtils.pxTodip(mContext, corner), mPaint);
        //写数字
        canvas.drawText(S,
                getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
                getHeight() - realH - 2 * DensityUtils.pxTodip(mContext, textPadding),
                mPaint);
        if (tempData != data) {
            postInvalidate();
        }
    }

    /**
     * 如果只是自定义View，则onLayout方法不需要实现
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setData(int data, int MAX) {
        this.data = data;
        tempData = 0;
        this.MAX = MAX;
        postInvalidate();//进行画面刷新
    }


}
