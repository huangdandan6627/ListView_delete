package com.huang.listview_delete.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/8.简单的log
 */

public class Logutil {
    public static boolean isDEBUG = true;

    public static void e(Object message) {
        if (isDEBUG) {
            Log.e("ME", message == null ? "空" : message.toString());
        }
    }

    public static void e(String tag, Object message) {
        if (isDEBUG) {
            Log.e("" + tag, message == null ? "空" : message.toString());
        }
    }

}
