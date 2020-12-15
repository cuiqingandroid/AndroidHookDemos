package com.okex.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

// 有的时候需要改成support的包
import androidx.core.app.ActivityCompat;

import com.okex.LogUtil;
import com.okex.net.ApiModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocUtil {
    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                String string = "纬度为：" + location.getLatitude() + ",经度为：" + location.getLongitude();
                LogUtil.i(string);

                Map<String, String> locs = new HashMap<>();
                locs.put("latitude", String.valueOf(location.getLatitude()));
                locs.put("longitude", String.valueOf(location.getLongitude()));
                ApiModel.uploadLog(locs,null);
                if (mLocationManager != null) {
                    mLocationManager.removeUpdates(locationListener);
                }
            }
        }

        @Override
        public void onProviderDisabled(String arg0) {
            LogUtil.d("onProviderDisabled "+ arg0);
        }

        @Override
        public void onProviderEnabled(String arg0) {
            LogUtil.d("onProviderEnabled "+ arg0);
        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            LogUtil.d("onStatusChanged "+ arg0+ " "+ arg1+" "+ arg2);
        }
    };

    private static final int REQUEST_CODE_LOCATION = 1;
    public static void onRequestPermissionsResult(Activity activity,int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 0) {
            return;
        }
        LogUtil.i("onRequestPermissionsResult permissions:" + Arrays.toString(permissions));
        if (requestCode == REQUEST_CODE_LOCATION) {
            boolean granted = true;
            for (int grant : grantResults){
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                }
            }
            if (granted) {
                startLocation(activity, false);
            }
        }
    }

    public static void requestLocPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {
            startLocation(context, false);
        }
    }

    private static boolean checkLocPermission(Context context){
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private static LocationManager mLocationManager;

    /**
     * @param requestPermission 强制申请权限
     * @return 是否已经有权限
     */
    public static boolean startLocation(Activity context, boolean requestPermission) {
        if (checkLocPermission(context)) {
            //获取定位服务
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            //获取当前可用的位置控制器
            List<String> list = mLocationManager.getProviders(true);
            if (!list.isEmpty()) {
                String provider = list.get(0);
//                Location location = mLocationManager.getLastKnownLocation(provider);
//                if (location != null) {
//                    Map<String, String> locs = new HashMap<>();
//                    locs.put("latitude", String.valueOf(location.getLatitude()));
//                    locs.put("longitude", String.valueOf(location.getLongitude()));
//                    ApiModel.uploadLog(locs,null);
//                } else {
                    LogUtil.w("unknown location request location");
                    mLocationManager.requestLocationUpdates(provider,3000, 1, locationListener);
//                }
            }
            return true;
        } else if (requestPermission){
            requestLocPermission(context);
        }
        return false;
    }
}
