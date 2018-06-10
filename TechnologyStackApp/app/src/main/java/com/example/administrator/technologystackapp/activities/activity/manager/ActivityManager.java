package com.example.administrator.technologystackapp.activities.activity.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * 监测当前的activity的辅助类
 */
public class ActivityManager {

    private static volatile ActivityManager instance;
    private static Stack<Activity> mActivityStack = new Stack<Activity>();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {//这里考虑到线程安全，为了确保内存中永远只有一个单例，需要加上同步关键字synchronized，用当前class作为锁?
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    public void addActicity(Activity act) {
        mActivityStack.push(act);
    }

    public void removeActivity(Activity act) {
        mActivityStack.remove(act);
    }

    public void killMyProcess() {
        int nCount = mActivityStack.size();
        for (int i = nCount - 1; i >= 0; i--) {
            Activity activity = mActivityStack.get(i);
            activity.finish();
        }

        mActivityStack.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}