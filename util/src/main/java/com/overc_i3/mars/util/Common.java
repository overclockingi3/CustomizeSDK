package com.overc_i3.mars.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Mars on 2017/8/25.
 *    ┏┓   ┏┓
 *   ┏┛┻━━━┛┻━━┓
 *   ┃             ┃
 *   ┃            ┃
 *   ┃  ┳┛ ┗┳ ┃
 *   ┃          ┃
 *   ┃    ┻    ┃
 *   ┃         ┃
 *   ┗━┓     ┏━┛
 *     ┃     ┃神兽保佑
 *     ┃     ┃代码无BUG！
 *     ┃     ┗━━━━━━━━┓
 *     ┃                    ┣┓
 *     ┃                    ┏┛
 *     ┗┓┓┏━┳┓┓┏━━━┓┓┏┛
 *      ┃┫┫  ┃┫┫   ┃┫┫
 *      ┗┻┛  ┗┻┛   ┗┻┛
 */

public class Common {

    private static long lastClickTime = 0;

    /**
     * 判断是否1秒内多次点击
     * @param ms 判断时间（毫秒）
     */
    public static boolean isFastDoubleClick(int ms) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < ms) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 选择日期对话框
     * @param context 调用方法的对象
     * @param textView 显示日期的 TextView
     * @param cancel 取消按钮的文字
     * @param separator 日期显示的分隔符
     * @param y 默认年份
     * @param m 默认月份 0-11
     * @param d 默认天数
     */
    public static void datePicker(Context context, final TextView textView, String cancel,
                                  final String separator, int y, int m, int d) {
        DatePickerDialog datePicker = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        String month = String.valueOf(monthOfYear + 1);
                        String day = String.valueOf(dayOfMonth);
                        if (month.length() != 2) {
                            month = "0" + month;
                        }
                        if (day.length() !=2) {
                            day = "0" + day;
                        }
                        String date = String.valueOf(year) + separator + month
                                + separator + day;
                        textView.setText(date);
                    }
                }, y, m, d);
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将文本内容置为空
                        textView.setText("");
                    }
                });
        datePicker.show();
    }

    /**
     * 线程休眠，不让当前线程霸占该进程所有资源
     * @param ms 休眠时间（毫秒）
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开软键盘
     * @param editText 输入框
     * @param context 上下文
     */
    public static void openKeybord(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * @param editText 输入框
     * @param context 调用方法的对象
     */
    public static void closeKeybord(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 控制软键盘的打开与关闭
     * @param context 调用方法的对象
     */
    public static void controlKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //震动milliseconds毫秒
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(milliseconds);
        }
    }

    /**
     * 以pattern[]方式震动
     * 举个例子：vibrate(new int[]{100,200,300,400},2)是指：先等待100ms，震动200ms，再等待300ms，震动400ms，
     * 接着就从pattern[2]的位置开始重复，就是继续的等待300ms，震动400ms，一直重复下去。
     * 传入0就是从开头一直重复下去，传入-1就是不重复震动。
     * @param activity 活动界面
     * @param pattern 震动方式
     * @param repeat 是否重复震动
     */
    public static void vibrate(final Activity activity, long[] pattern,int repeat){
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(pattern,repeat);
        }
    }

    /**
     * 取消震动
     */
    public static void virateCancle(final Activity activity){
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.cancel();
        }
    }

    /**
     * 播放提示音
     * @param context 调用对象
     * @param uri 提示音文件路径，值为null时播放系统提示音
     */
    public static void Ringtone(Context context, Uri uri) {
        if (uri == null) {
            RingtoneManager.getRingtone(context,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();
        } else {
            RingtoneManager.getRingtone(context, uri).play();
        }
    }

    /**
     * 调用该方法无论手机是否有菜单按键都显示actionbar的菜单按钮
     * @param context 调用方法的对象
     */
    public static void getOverflowMenu(Context context) {

        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
