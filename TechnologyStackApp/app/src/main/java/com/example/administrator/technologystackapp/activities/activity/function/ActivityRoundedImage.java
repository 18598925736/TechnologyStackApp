package com.example.administrator.technologystackapp.activities.activity.function;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;

/**
 * 圆形头像
 */
public class ActivityRoundedImage extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rounded_image);
        initImage();
    }

    private void initImage() {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.me);//1、取得图片资源的bitmap对象
        Bitmap dst;//2、将长方形图片裁剪成正方形图片
        if (src.getWidth() >= src.getHeight()) {// 所谓的裁剪，就是用Bitmap的create方法，指定宽高和源bitmap
            dst = Bitmap.createBitmap(src, src.getWidth() / 2 - src.getHeight() / 2, 0, src.getHeight(), src.getHeight());
        } else {
            dst = Bitmap.createBitmap(src, 0, src.getHeight() / 2 - src.getWidth() / 2, src.getWidth(), src.getWidth());
        }
        //这样，就得到了一个方形的图
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), dst);// 3、再启用RoundedBitmapDrawable设置圆角和抗锯齿
        roundedBitmapDrawable.setCornerRadius(dst.getWidth() / 2); //设置圆角半径为正方形边长的一半
        roundedBitmapDrawable.setAntiAlias(true);//图片的裁剪通常会造成锯齿，这里要设置抗锯齿
        ImageView image3 = (ImageView) findViewById(R.id.iv_my);//4、将处理之后的drawable对象设置到imageView中
        image3.setImageDrawable(roundedBitmapDrawable);
    }
}
