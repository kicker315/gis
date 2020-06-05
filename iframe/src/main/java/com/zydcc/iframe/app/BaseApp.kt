package com.zydcc.iframe.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication

/**
 * =======================================
 * 程序入口的基类，具体到应用的程序入口需要继承此类
 * Create by ningsikai 2020/6/5:8:29 AM
 * ========================================
 */
open class BaseApp : MultiDexApplication() {


    override fun onCreate() {
        context = this
        super.onCreate()
        if (isSetDefaultUncaughtExceptionHandler()) {
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler.instance)
        }
    }

    protected fun isSetDefaultUncaughtExceptionHandler(): Boolean {
        return true
    }

    companion object {
        lateinit var context: Context
    }

}