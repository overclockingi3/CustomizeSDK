package com.overc_i3.mars.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

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
