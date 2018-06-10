package com.example.administrator.technologystackapp.activities.custom.danmu;


import android.graphics.drawable.Drawable;

/**
 *
 */
public class DanMu {

    public boolean ifMine = false;
    public String content;//弹幕的文字内容
    public float distanceToTop;//距离顶端的比例（0-1）
    public int duration;//显示时长，单位是秒
    public int textColor;//文字颜色
    public int bgColor;//背景颜色
    public Drawable drawable;

    public static String getRandomDanMuContentInPresets() {
        String result;

        int n = (int) (10 * Math.random());
        switch (n) {
            case 1:
            case 2:
                result = "6666666";
                break;
            case 3:
            case 4:
                result = "不要放弃，加油，你是最胖的！";
                break;
            case 5:
            case 6:
                result = "一条大河啊啊啊啊向西流";
                break;
            case 7:
            case 8:
                result = "冷静冷静";
                break;
            case 9:
                result = "卧槽什么鬼，吓我一跳";
                break;
            default:
                result = "2333333";
        }
        return result;
    }
}