package com.example.administrator.technologystackapp.activities.custom.danmu;

import android.graphics.Color;

public class DanMuColor {
    public static final int RED = Color.RED;
    public static final int BLUE = Color.BLUE;
    public static final int YELLOW = Color.YELLOW;
    public static final int GREEN = Color.GREEN;
    public static final int ORANGE = Color.parseColor("#FFAA00");

    /**
     * 返回上面预设中的任意一个颜色
     *
     * @return
     */
    public static int getRandomColorInPresets() {
        int result;

        int n = (int) (10 * Math.random());
        switch (n) {
            case 1:
            case 2:
                result = RED;
                break;
            case 3:
            case 4:
                result = BLUE;
                break;
            case 5:
            case 6:
                result = YELLOW;
                break;
            case 7:
            case 8:
                result = GREEN;
                break;
            case 9:
                result = ORANGE;
                break;
            default:
                result = BLUE;
        }
        return result;
    }

}