package com.txxia.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.okex.OkManager;
import com.okex.WebActivity;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        startActivity(new Intent(this, UserProtocolActivity.class));
//        startActivity(new Intent(this, SplashActivity.class));
        startActivity(new Intent(this, WebActivity.class));

        test();
    }



    private void test(){
        OkManager.init(this);

    }


}