package com.zydcc.iframe.app

/**
 * =======================================
 * 异常捕捉
 * Create by ningsikai 2020/6/5:8:34 AM
 * ========================================
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    private val mExceptionFileName = "log.txt"
    private val TAG = CrashHandler::class.java.simpleName

    private var mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread?, e: Throwable?) {

    }

    companion object {
        val instance: CrashHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandler()
        }
    }

}