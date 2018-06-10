package com.example.administrator.technologystackapp.activities.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.administrator.technologystackapp.R;


/**
 * 感谢网上的大神，提供的思路。
 * 这是一个带删除按钮的EditText，它能够在输入框中有内容时，显示最右边的删除按钮，点击该按钮可以直接清空内容
 */
@SuppressLint("AppCompatCustomView")
public class DelEditText extends EditText {

    private Drawable imgClear;
    private Context mContext;

    public DelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setDrawable();
        init();
    }

    private void init() {
        imgClear = mContext.getResources().getDrawable(R.drawable.delete);
        //添加watcher监听器，监听 文本被改变之后的事件
        addTextChangedListener(new TextWatcher() {
            //内容变化前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //内容正在改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //在内容改变完之后
            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("mytagX", "" + editable.toString());
                setDrawable();
            }
        });
    }

    //绘制删除图片
    //这里的setCompoundDrawablesWithIntrinsicBounds方法解释一下：
    //按照原注释的意思，这个方法会在组件的上下左右,如果只需要在右侧显示，那就把其他3个参数设置为null，显示一个Drawable
    private void setDrawable() {
        if (length() < 1)//
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgClear, null);
    }


    //当触摸范围在右侧时，触发删除方法，隐藏叉叉

    /**
     * 继承父组件的触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgClear != null && event.getAction() == MotionEvent.ACTION_UP) {//如果触发的是 按下并释放的动作，也就是平时的点一下
            int eventX = (int) event.getRawX();//就拿到当前点击的位置X,Y坐标
            int eventY = (int) event.getRawY();
            Log.d("mytagX", "" + eventX + " - " + eventY);
            Rect rect = new Rect();//新建一个矩形
            getGlobalVisibleRect(rect);//将当前View的绘制范围大小，设置到这个属性中. 比如说，这个View的绘制范围是 从 (0,0)到(100,200), 那么Rect的4个属性值就是0,0,100,200sa

            Log.d("onTouchEvent", "" + rect.left + " - " + rect.top + " - " + rect.right + " - " + rect.bottom);

            rect.left = rect.right - 100;//将rect的左 ，设置为它 右的值-100. 这是在控制触发事件的范围大小
            if (rect.contains(eventX, eventY))//如果点击的位置，在Rect范围之内，那就触发清空事件
            {
                setText("");
                Log.d("onTouchEvent", "点击了EditText并且触发了清空事件");
            } else
                Log.d("onTouchEvent", "点击了EditText但是并没有点击到删除按钮的范围之内");

        }
        return super.onTouchEvent(event);
    }

}
