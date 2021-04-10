package com.txxia.demos.util;


import com.txxia.demos.BuildConfig;

public final class Log {

    private static final String TAG = "cuiqing";
    private static boolean sEnabled = BuildConfig.DEBUG;

    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static boolean isLoggingEnabled() {
        return sEnabled;
    }

    public static void v(String msg) {
        if (sEnabled) {
            android.util.Log.v(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (sEnabled) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.v(TAG, msg, tr);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void d(String msg) {
        if (sEnabled) {
            android.util.Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnabled) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.d(TAG, msg, tr);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        if (sEnabled) {
            android.util.Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (sEnabled) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.i(TAG, msg, tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        if (sEnabled) {
            android.util.Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (sEnabled) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.w(TAG, msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.w(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        if (sEnabled) {
            android.util.Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnabled) {
            android.util.Log.e(tag, msg);
        }

    }

    public static void e(String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.e(TAG, msg, tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void t(String msg, Object... args) {
        if (sEnabled) {
            android.util.Log.v("test", String.format(msg, args));
        }
    }
}