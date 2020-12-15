package com.okex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.okex.net.ApiModel;
import com.okex.net.ErrorStatus;
import com.okex.net.NetRespListener;

public class OkManager {

    public static boolean isShowH5 = false;
    public static String chennelApkUrl = "";

    public static String android_id = null;
    private static SharedPreferences sp = null;
    public static String channel = null;
    public static String packageName = "";
    /**
     * 应用版本号
     */
    public static String versionName ="";

    private static Context mContext;


    public static SharedPreferences getSharedPreference(){
        return  sp;
    }

    @SuppressLint("HardwareIds")
    public static void init(final Context context){
        mContext = context.getApplicationContext();
        sp = context.getSharedPreferences("okmanager", Context.MODE_PRIVATE);
        // 初始化全局数据
        isShowH5 = context.getSharedPreferences("okmanager", Context.MODE_PRIVATE).getBoolean("is_show_h5", false);
        android.util.Log.d("OkManager", "init isshowh5"+isShowH5+" channel:"+channel);
        channel = readChannel(context);
        packageName = context.getPackageName();
        versionName = getAppVersionName(context);

        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiModel.init();
        // 获取是否开启H5
        ApiModel.checkH5Open(new NetRespListener<Boolean>() {
            @Override
            public void onMessageResponse(Boolean data) {
                isShowH5 = data;
            }

            @Override
            public void onMessageError(ErrorStatus errorCode) {
                // 网络错误，不处理
            }
        });

        if (context instanceof Activity) {
            ((Activity) context).getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

                }

                @Override
                public void onActivityStarted(@NonNull Activity activity) {

                }

                @Override
                public void onActivityResumed(@NonNull Activity activity) {

                }

                @Override
                public void onActivityPaused(@NonNull Activity activity) {

                }

                @Override
                public void onActivityStopped(@NonNull Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {

                }
            });
        }

    }

    /**
     * 获取应用的version name
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            //no impl
        }
        return versionName;
    }

    private static String readChannel(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("MOBILE_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (channel == null){
            channel = "";
        }
        return channel;
    }


}
