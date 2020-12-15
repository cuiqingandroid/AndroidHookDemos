package com.okex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.okex.util.IdUtil;
import com.okex.util.LocUtil;


public class OkCheSplashActivity extends Activity {

    private SharedPreferences sp;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            LocUtil.startLocation(OkCheSplashActivity.this, false);
            startMain();
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(IdUtil.getLayoutId(this, "activity_splash_ex"));
        OkManager.init(this);
        sp = getSharedPreferences("okmanager",Context.MODE_PRIVATE);
        if (sp.getBoolean("needShowPrivacy", true)) {
            showPrivacy();
        } else {
            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    }

    private void startMain(){
        boolean isShowH5 = sp.getBoolean("is_show_h5", false);
        if (isShowH5) {
            startActivity(new Intent(this, WebActivity.class));
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setComponent(new ComponentName("com.tong.car", "cn.com.drivedu.chexuetang.main.MainActivity"));
            intent.putExtra("isFromLogin", false);
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


    private void showPrivacy() {
        String title = "用户协议与隐私政策";
        String paivacy = "请你务必审慎阅读、充分理解“用户协议”和“隐私政策”各条款，包括但不限于：为了更好的向你提供服务，我们需要收集你的设备标识、操作日志等信息用于分析、优化应用性能。";
        SpannableString ss = new SpannableString(paivacy);
        int indexUser = paivacy.indexOf("用户协议");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(OkCheSplashActivity.this, UserProtocolActivity.class));
            }
        }, indexUser, indexUser+4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int indexPrivacy = paivacy.indexOf("隐私政策");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(OkCheSplashActivity.this, PrivacyActivity.class));
            }
        }, indexPrivacy, indexPrivacy+4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, IdUtil.getStyleId(this, "Theme.AppCompat.Light.Dialog"));
        builder.setTitle(title);
        builder.setMessage(ss);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(OkCheSplashActivity.this, "请查看并同意用户协议和隐私政策", Toast.LENGTH_SHORT).show();
                showPrivacy();
            }
        });

        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp.edit().putBoolean("needShowPrivacy", false).apply();
                if (LocUtil.startLocation(OkCheSplashActivity.this, true)){
                    startMain();
                }
            }
        });

        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OkCheSplashActivity.this, "请查看并同意用户协议和隐私政策", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alertDialog.show();
    }

}
