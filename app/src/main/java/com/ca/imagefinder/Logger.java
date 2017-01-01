package com.ca.imagefinder;

import android.text.TextUtils;
import android.util.Log;


public class Logger {
    public static final boolean ENABLE_LOG = BuildConfig.DEBUG;
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static String customTagPrefix = "";

    public static boolean allowD = true && DEBUG;
    public static boolean allowE = true;
    public static boolean allowI = true && DEBUG;
    public static boolean allowV = true && DEBUG;
    public static boolean allowW = true && DEBUG;

    /**
     * TAG.
     *
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        callerClazzName = TextUtils.isEmpty(customTagPrefix) ? callerClazzName : customTagPrefix + ":" + callerClazzName;
        return callerClazzName;
    }

    private static String generateClassNavigationSuffix(StackTraceElement caller) {
        String suffix = " (%s.java:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        suffix = String.format(suffix, callerClazzName, caller.getLineNumber());
        return suffix;
    }

    public static void d(String content) {
        if (!allowD) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content + generateClassNavigationSuffix(caller));
    }

    public static void d(String content, Throwable tr) {
        if (!allowD) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content + generateClassNavigationSuffix(caller), tr);
    }

    public static void e(String content) {
        if (!allowE) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content + generateClassNavigationSuffix(caller));
    }

    public static void e(String content, Throwable tr) {
        if (!allowE) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag,  content + generateClassNavigationSuffix(caller), tr);
    }

    public static void i(String content) {
        if (!allowI) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag,  content + generateClassNavigationSuffix(caller));
    }

    public static void i(String content, Throwable tr) {
        if (!allowI) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag,  content + generateClassNavigationSuffix(caller), tr);
    }

    public static void v(String content) {
        if (!allowV) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag,  content + generateClassNavigationSuffix(caller));
    }

    public static void v(String content, Throwable tr) {
        if (!allowV) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag,  content + generateClassNavigationSuffix(caller), tr);
    }

    public static void w(String content) {
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag,  content + generateClassNavigationSuffix(caller));
}

    public static void w(String content, Throwable tr) {
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag,  content + generateClassNavigationSuffix(caller), tr);
    }

    public static void w(Throwable tr) {
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, tr);
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


}
