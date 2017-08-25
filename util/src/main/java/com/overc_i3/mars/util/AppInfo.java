package com.overc_i3.mars.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
 *     ┃              ┣┓
 *     ┃              ┏┛
 *     ┗┓┓┏━┳┓┓┏━━━┓┓┏┛
 *      ┃┫┫  ┃┫┫   ┃┫┫
 *      ┗┻┛  ┗┻┛   ┗┻┛
 */

public class AppInfo {

    /**
     * 收集设备参数信息
     */
    public Map<String, String> collectDeviceInfo(Context context) {
        // 用来存储设备信息
        Map<String, String> deviceInfo = new HashMap<>();
        try {
            PackageManager pm = context.getPackageManager();// 获得包管理器
            // 得到该应用的信息，即主Activity
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                deviceInfo.put("versionName", versionName);
                deviceInfo.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();// 反射机制
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                deviceInfo.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return deviceInfo;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本码信息
     */
    public static String getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return String.valueOf(packageInfo.versionCode);

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本号信息
     */
    public static String getVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return "V " + String.valueOf(packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
