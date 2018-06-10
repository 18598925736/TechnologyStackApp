package com.example.administrator.technologystackapp.activities.activity.function;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.danmu.DanMu;
import com.example.administrator.technologystackapp.activities.custom.danmu.DanMuColor;
import com.example.administrator.technologystackapp.activities.custom.danmu.MyDanMuLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 弹幕 属性动画
 */
public class ActivityCustomDanMuLayout extends BaseActivity {

    private MyDanMuLayout dl_my_danMu;
    private Button btn_send, btn_send_plane, btn_hide, btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danmu);
        init();
    }

    private void init() {
        dl_my_danMu = findViewById(R.id.dl_my_danmu);
        btn_send = findViewById(R.id.btn_send);
        btn_hide = findViewById(R.id.btn_hide);
        btn_show = findViewById(R.id.btn_show);
        btn_send_plane = findViewById(R.id.btn_send_plane);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_my_danMu.sendDanMu(DanMu.getRandomDanMuContentInPresets(), DanMuColor.getRandomColorInPresets(), Color.TRANSPARENT, true);
            }
        });
        btn_send_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_my_danMu.sendPlane(true);
            }
        });
        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_my_danMu.hideDanMu();
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_my_danMu.showDanMu();
            }
        });

        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dl_my_danMu.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseTimer();
    }

    Timer timer;// 用来模拟从server获取到的弹幕

    private void startTimer() {
        if (timer != null)
            releaseTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dl_my_danMu.sendDanMu(DanMu.getRandomDanMuContentInPresets(), DanMuColor.getRandomColorInPresets(), Color.TRANSPARENT, false);
            }
        }, 0, 300);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dl_my_danMu.sendPlane(false);
            }
        }, 500, 3000);
    }

    private void releaseTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

}
