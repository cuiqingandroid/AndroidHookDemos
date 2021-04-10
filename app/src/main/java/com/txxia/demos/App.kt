package com.txxia.demos

import android.app.Application
import android.content.Context
import com.txxia.demos.signaturehook.PmsHookWrapper

open class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        PmsHookWrapper.hookPMS(base)
    }

    override fun onCreate() {
        super.onCreate()
    }
}