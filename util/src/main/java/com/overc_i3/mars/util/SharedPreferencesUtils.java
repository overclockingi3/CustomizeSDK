package com.overc_i3.mars.util;

import android.content.Context;
import android.content.SharedPreferences;

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

public class SharedPreferencesUtils {
    /**
     * 保存数据的方法，需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context 调用方法的对象
     * @param fileName 保存文件名
     * @param key 要保存的值名
     * @param object 要保存的值
     */
    public static boolean setParam(Context context, String fileName, String key, Object object){

        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE).edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }
        return editor.commit();
    }

    /**
     * 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context 调用方法的对象
     * @param fileName 保存文件名
     * @param key 要保存的值名
     * @param defaultObject 取值失败时赋予的默认值
     * @return 返回取出的值
     */
    public static Object getParam(Context context, String fileName, String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sharedPreferences.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sharedPreferences.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sharedPreferences.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sharedPreferences.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sharedPreferences.getLong(key, (Long)defaultObject);
        }
        return defaultObject;
    }
}
