package com.overc_i3.mars.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mars on 2017/8/25.
 *    ┏┓   ┏┓
 *   ┏┛┻━━━┛┻━━┓
 *   ┃         ┃
 *   ┃         ┃
 *   ┃  ┳┛ ┗┳  ┃
 *   ┃         ┃
 *   ┃    ┻    ┃
 *   ┃         ┃
 *   ┗━┓     ┏━┛
 *     ┃     ┃神兽保佑
 *     ┃     ┃代码无BUG！
 *     ┃     ┗━━━━━━━━┓
 *     ┃                   ┣┓
 *     ┃                   ┏┛
 *     ┗┓┓┏━┳┓┓┏━━━┓┓┏┛
 *      ┃┫┫  ┃┫┫   ┃┫┫
 *      ┗┻┛  ┗┻┛   ┗┻┛
 */

public class NetInfo {
    /**
     * 判断网络是否连接
     * @param context 调用方法的对象
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否wifi连接
     * @param context 调用方法的对象
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     * @param activity 调用方法的对象
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
