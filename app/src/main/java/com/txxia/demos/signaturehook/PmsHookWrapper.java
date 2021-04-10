package com.txxia.demos.signaturehook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.txxia.demos.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PmsHookWrapper {

    /**
     * 原apk获取到的签名信息
     */
    public static final String ORIGIN_SIGN = "028766324ab65c63";

    @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
    public static void hookPMS(Context context) {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);
            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            final Object sPackageManager = sPackageManagerField.get(currentActivityThread);
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(), new Class[]{iPackageManagerInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("getPackageInfo".equals(method.getName())) {
                        Log.d("hookPMS getPackageInfo invoke");
                        Integer flag = (Integer)args[1];
                        if (flag == 64) {
                            Signature sign = new Signature(ORIGIN_SIGN);
                            PackageInfo info = (PackageInfo)method.invoke(sPackageManager, args);
                            info.signatures[0] = sign;
                            return info;
                        }
                    }

                    return method.invoke(sPackageManager, args);
                }
            });
            sPackageManagerField.set(currentActivityThread, proxy);
            PackageManager pm = context.getPackageManager();
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm, proxy);
            Log.d("hookPMS success ");
        } catch (Exception e) {
            Log.d("hookPMS exception " + e.getMessage());
        }
    }
}
