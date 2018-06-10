package com.example.administrator.technologystackapp.activities.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;

public class CommonTitleBarLayout extends LinearLayout {

    public CommonTitleBarLayout(Context context) {
        this(context, null);
    }

    public CommonTitleBarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private Button btn_left, btn_right;

    private void initView(Context context, AttributeSet attrs) {
        //关键代码:看以下3个参数
        //第一个，是 将要实例化的布局
        //第二个，是 设置rootView（注：布局被实例化之后，可以单独作为一块， 也可以跟随在某一个View下面，作为子view）
        //第三个，设置是否放置在rootView下面，作为子view
        // 在这里，我们后面两个参数都必须加上。一个是this，本Layout，一个是true，确定添加到LinearLayout下面去作为子view
        View titleView = LayoutInflater.from(context).inflate(R.layout.title_merge, this, true);

        btn_left = titleView.findViewById(R.id.btn_left);
        TextView tv_mid = titleView.findViewById(R.id.tv_mid);
        btn_right = titleView.findViewById(R.id.btn_right);

        LinearLayout ll_title = titleView.findViewById(R.id.ll_title);

        //1，集合转换，将attrs转换成TypedArray，两个参数，第一个是 attrs，第二个，则是在attrs.xml里面定义的styleable，
        // 两个参数结合，就是要将布局xml里面获得的参数，与之前定义好的参数对照起来
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        //下面来解析attrs（来自xml的可选参数）
        if (typedArray != null) {

            String text_mid = typedArray.getString(R.styleable.MyTitleBar_mid_text);
            tv_mid.setText(text_mid);


            int right_drawable = typedArray.getResourceId(R.styleable.MyTitleBar_right_drawable, -1);
            if (right_drawable != -1) {
                btn_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, right_drawable, 0);
            } else {
                String text_right = typedArray.getString(R.styleable.MyTitleBar_right_text);
                btn_right.setText(text_right);
            }

            int leftDrawable = typedArray.getResourceId(R.styleable.MyTitleBar_left_drawable, -1);
            if (leftDrawable != -1) {
                btn_left.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0);//优先图片显示
            } else {
                String text_left = typedArray.getString(R.styleable.MyTitleBar_left_text);
                btn_left.setText(text_left);
            }

            int bg_color = typedArray.getColor(R.styleable.MyTitleBar_title_bg_color, Color.CYAN);//获得颜色，默认蓝绿色
            ll_title.setBackgroundColor(bg_color);

            typedArray.recycle();
        }

    }

    public void registerListener(final ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
        if (buttonClickListener != null) {
            btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.leftClick();
                    Log.d("tagxxx", "left");
                }
            });
            btn_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.rightClick();
                    Log.d("tagxxx", "right");
                }
            });
        }
    }

    private ButtonClickListener buttonClickListener;

    public interface ButtonClickListener {

        void leftClick();

        void rightClick();
    }
}