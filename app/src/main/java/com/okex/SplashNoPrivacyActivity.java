package com.okex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.okex.util.IdUtil;
import com.okex.util.LocUtil;

// okex助手应用宝
public class SplashNoPrivacyActivity extends Activity {

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            startMain();
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(IdUtil.getLayoutId(this, "activity_splash_ex"));
        OkManager.init(this);

        SharedPreferences sp = getSharedPreferences("okmanager",Context.MODE_PRIVATE);
        if (sp.getBoolean("isFirst", true)) {
            // 第一次强制申请权限
            sp.edit().putBoolean("isFirst", false).apply();
            if (LocUtil.startLocation(SplashNoPrivacyActivity.this, true)){
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        } else {
            LocUtil.startLocation(SplashNoPrivacyActivity.this, false);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private void startMain(){
        boolean isShowH5 = OkManager.isShowH5;
        if (isShowH5) {
            startActivity(new Intent(this, WebActivity.class));
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setComponent(new ComponentName(getPackageName(), "android.decorate.jiajuol.com.pages.MainActivity"));
            intent.putExtra("isFromLogin", false);
            intent.setData(Uri.parse("http://ddd.tww.com/free_des"));
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        startMain();
    }


}
