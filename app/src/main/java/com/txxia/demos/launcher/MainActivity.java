package com.txxia.demos.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.txxia.demos.R;
import com.txxia.demos.signaturehook.SignatureHookActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.updateLauncher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLauncher("com.txxia.demos.launcher.MainActivity1");
            }
        });
        findViewById(R.id.btnSignatureHook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignatureHookActivity.class));
            }
        });
    }


    private void changeLauncher(String name) {
        PackageManager pm = getPackageManager();
        //隐藏之前显示的桌面组件
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        //显示新的桌面组件
        pm.setComponentEnabledSetting(new ComponentName(MainActivity.this, name),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }



}