package com.txxia.demos.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.txxia.demos.R;
import com.txxia.demos.signaturehook.PmsHookWrapper;

public class JniActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        TextView tvSignature = findViewById(R.id.tvJniStr);
        tvSignature.setText("jni获取字符串:"+NativeInterface.nativeString());
    }


}
