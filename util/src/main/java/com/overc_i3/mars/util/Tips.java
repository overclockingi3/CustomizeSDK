package com.overc_i3.mars.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

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

public class Tips {

    /**
     * 最简单的 Toast
     * @param context 调用方法的对象
     * @param message Toast内容
     * @param duration 显示时间长短 Toast.LENGTH_LONG(值为 1)或Toast.LENGTH_SHORT(值为 0)或自定义
     */
    public static void show(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义位置 Toast
     * @param context 调用方法的对象
     * @param message Toast内容
     * @param duration 显示时间长短 Toast.LENGTH_LONG(值为 1)或Toast.LENGTH_SHORT(值为 0)或自定义
     * @param gravity 显示位置如：Gravity.CENTER等
     * @param x 显示位置 X轴偏移量
     * @param y 显示位置 Y轴偏移量
     */
    public static void show(Context context, CharSequence message, int duration,
                            int gravity, int x, int y) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(gravity, x, y);
        toast.show();
    }

    /**
     * dialog提示框
     * @param context 调用方法的对象
     * @param Title 标题
     * @param message 内容
     * @param ok 按钮文字
     * @param cancelable 是否只能通过按钮dismiss对话框
     */
    public static void alert(Context context, CharSequence Title, CharSequence message,
                             String ok, boolean cancelable) {
        new AlertDialog.Builder(context).setTitle(Title)
                .setMessage(message).setCancelable(cancelable)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        dialog.dismiss();
                    }
                }).show();
    }
}
