package com.okex.net;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.okex.LogUtil;
import com.okex.OkManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApiModel {

    private final static String PROTOCOL = "https";
    private final static String SEPARATOR = "/";
    private final static String HOST = "okapi.txxia.com";
//    private final static String HOST = "10.0.0.16:9901";


    private final static String CHECK_OPEN = "checkConfig";
    private final static String UPLOAD_LOG = "uploadlog";

    private static Map<String, String> baseGetParams = new HashMap<>();

    public static void init(){
        baseGetParams.put("model", Build.MODEL);
        baseGetParams.put("brand", Build.BRAND);
        baseGetParams.put("display", Build.DISPLAY);
        baseGetParams.put("manufacturer", Build.MANUFACTURER);
        baseGetParams.put("os_version", Build.VERSION.RELEASE);
        baseGetParams.put("v", OkManager.versionName);
        baseGetParams.put("pn", OkManager.packageName);
        baseGetParams.put("android_id", OkManager.android_id);
        baseGetParams.put("channel", OkManager.channel);
    }

    public static void checkH5Open(final NetRespListener<Boolean> listener){
        String url = buildAbsoluteUrl(CHECK_OPEN, baseGetParams);
        HttpClientUtils.get(url, new HttpClientUtils.OnRequestCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                LogUtil.d("checkH5Open result"+ jsonObject);
                if (listener != null) {
                    if (jsonObject != null) {
                        JSONObject jsonData = jsonObject.optJSONObject("data");
                        boolean isShowH5 = false;
                        if (jsonData != null) {
                            isShowH5 = jsonData.optInt("h5config") == 1;
                            OkManager.chennelApkUrl = jsonData.optString("apkurl");
                        }
                        SharedPreferences sp = OkManager.getSharedPreference();
                        if (sp != null) {
                            sp.edit().putBoolean("is_show_h5", isShowH5).apply();
                        }
                        listener.onMessageResponse(isShowH5);
                    } else {
                        listener.onMessageResponse(false);
                    }
                }
            }

            @Override
            public void onError(ErrorStatus errorStatus) {
                LogUtil.d("checkH5Open error "+ errorStatus.getErrorMsg());
                listener.onMessageError(errorStatus);
            }
        });
    }

    /**
     * 上传日志
     */
    public static void uploadLog(Map<String,String> data, final NetRespListener<Boolean> listener) {
        String url = buildAbsoluteUrl(UPLOAD_LOG, baseGetParams);
        HttpClientUtils.post(url, data, new HttpClientUtils.OnRequestCallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                LogUtil.d("uploadLog result"+ jsonObject);
                if (listener != null) {
                    if (jsonObject != null) {
                        listener.onMessageResponse(jsonObject.optInt("data") == 1);
                    } else {
                        listener.onMessageResponse(false);
                    }
                }
            }

            @Override
            public void onError(ErrorStatus errorStatus) {
                LogUtil.d("uploadLog error "+ errorStatus.getErrorMsg());
                if (listener != null) {
                    listener.onMessageError(errorStatus);
                }
            }
        });
    }


    private static String buildAbsoluteUrl(String method, Map<String, String> getParams) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL);
        builder.encodedAuthority(HOST);
        if (!TextUtils.isEmpty(method)) {
            builder.appendPath(method);
        } else {
            builder.path(SEPARATOR);
        }

        if (getParams != null && !getParams.isEmpty()) {
            Set<String> keys = getParams.keySet();
            for (String key : keys) {
                builder.appendQueryParameter(key, getParams.get(key));
            }
        }
        String url = builder.build().toString();
        LogUtil.d("buildAbsoluteUrl=" + url);
        return url;
    }

}
