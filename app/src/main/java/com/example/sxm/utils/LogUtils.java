package com.example.sxm.utils;

import android.util.Log;

public class LogUtils {
    private static final boolean DEBUG = true;
    private static final String TAG = "Practice";
    public  static final boolean DEBUG_BLUR = true;

    public static void d(String tag, String msg) {
        if (DEBUG) Log.d(TAG, new StringBuilder("[")
                .append(tag)
                .append("]----")
                .append(msg)
                .toString());
    }

    public static void i(String tag, String msg) {
        if (DEBUG) Log.i(TAG, new StringBuilder("[")
                .append(tag)
                .append("]----")
                .append(msg)
                .toString());
    }
}
