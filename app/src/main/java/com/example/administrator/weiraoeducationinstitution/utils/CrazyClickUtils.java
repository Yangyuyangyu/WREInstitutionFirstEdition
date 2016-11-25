package com.example.administrator.weiraoeducationinstitution.utils;


/**
 * Created by Administrator on 2016/6/16.防止疯狂点击,间隔1.5秒，减小服务器压力和客户端流量输出
 */
public class CrazyClickUtils {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
