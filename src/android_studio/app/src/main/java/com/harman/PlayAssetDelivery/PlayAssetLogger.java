package com.harman.PlayAssetDelivery;

import android.util.Log;

public class PlayAssetLogger {
    static boolean g_enableReleaseLogging = false; // Logging Enable or Disable

    public static void d(String tag, String msg) {
         if(g_enableReleaseLogging)
             Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if(g_enableReleaseLogging)
             Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if(g_enableReleaseLogging)
             Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if(g_enableReleaseLogging)
        Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if(g_enableReleaseLogging)
             Log.w(tag, msg);
    }
}
