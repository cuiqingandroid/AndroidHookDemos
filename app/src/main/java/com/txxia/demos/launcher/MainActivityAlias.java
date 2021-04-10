package com.txxia.demos.launcher;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.txxia.demos.R;


public class MainActivityAlias extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hooed);

        findViewById(R.id.updateLauncher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLauncher("com.txxia.demos.launcher.MainActivity");
            }
        });
    }


    private void changeLauncher(String name) {
        PackageManager pm = getPackageManager();
        //隐藏之前显示的桌面组件
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        //显示新的桌面组件
        pm.setComponentEnabledSetting(new ComponentName(MainActivityAlias.this, name),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }



}