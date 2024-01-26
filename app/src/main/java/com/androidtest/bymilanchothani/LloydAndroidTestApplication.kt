package com.androidtest.bymilanchothani

import android.app.Application
import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LloydAndroidTestApplication : MultiDexApplication()
{

    companion object {
        lateinit var instance: LloydAndroidTestApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}