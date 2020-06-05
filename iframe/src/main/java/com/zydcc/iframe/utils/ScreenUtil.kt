package com.zydcc.iframe.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

/**
 * =======================================
 * 屏幕工具
 * Create by ningsikai 2020/6/3:8:48 AM
 * ========================================
 */
object ScreenUtil {
    /**
     * 获取屏幕宽度(较小的宽度，横屏时为屏幕高度)
     */
    fun getSmallScreenWidth(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels
        val height = outMetrics.heightPixels
        return if (height > width) width else height
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获取当前窗口的高度， 该高度是不包含导航栏和状态栏的
     */
    fun getWindowHeight(activity: Activity): Int {
        return activity.window.windowManager.defaultDisplay.height
    }

    /**
     * 获取当前窗口的宽度
     */
    fun getWindowWidth(activity: Activity): Int {
        return getScreenWidth(activity)
    }

    /**
     * 精确获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）
     *
     * @return 设备尺寸
     */
    fun getScreenPhysicalSize(context: Context): Double {
        val dm = context.resources.displayMetrics
        val diagonalPixels = Math.sqrt(
            Math.pow(
                dm.widthPixels.toDouble(),
                2.0
            ) + Math.pow(dm.heightPixels.toDouble(), 2.0)
        )
        return diagonalPixels / dm.densityDpi
    }

    /**
     * 判断是否是平板（官方用法）
     */
    fun isTablet(context: Context): Boolean {
        val size = getScreenPhysicalSize(context)
        return size >= 6.0 && context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * 获取导航栏高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    fun getStatusBarHeight(context: Activity): Int {
        var statusBarHeight = 0
        try {
            val res = context.resources
            val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = res.getDimensionPixelSize(resourceId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (statusBarHeight == 0) {
            val frame = Rect()
            context.window.decorView.getWindowVisibleDisplayFrame(frame)
            statusBarHeight = frame.top
        }
        return statusBarHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 设置灰度
     *
     * @param greyScale true:灰度
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun setGreyScale(v: View, greyScale: Boolean) {
        if (greyScale) {
            // Create a paint object with 0 saturation (black and white)
            val cm = ColorMatrix()
            cm.setSaturation(0f)
            val greyScalePaint = Paint()
            greyScalePaint.colorFilter = ColorMatrixColorFilter(cm)
            // Create a hardware layer with the greyScale paint
            v.setLayerType(View.LAYER_TYPE_HARDWARE, greyScalePaint)
        } else {
            // Remove the hardware layer
            v.setLayerType(View.LAYER_TYPE_NONE, null)
        }
    }

    /**
     * 判断是否开启了自动亮度调节
     */
    fun isAutoBrightness(aContentResolver: ContentResolver?): Boolean {
        var automicBrightness = false
        try {
            automicBrightness = Settings.System.getInt(
                aContentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return automicBrightness
    }

    /**
     * 获取屏幕的亮度
     */
    fun getScreenBrightness(activity: Activity): Float {
        var nowBrightnessValue = 0
        val resolver = activity.contentResolver
        try {
            nowBrightnessValue = Settings.System.getInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return nowBrightnessValue.toFloat()
    }

    /**
     * 设置亮度(手动设置亮度，需要关闭自动设置亮度的开关)
     *
     * @param brightness 0-1
     */
    fun setBrightness(activity: Activity, brightness: Float) {
        stopAutoBrightness(activity)
        val lp = activity.window.attributes
        lp.screenBrightness = brightness * (1f / 255f)
        activity.window.attributes = lp
    }

    /**
     * 停止自动亮度调节
     */
    fun stopAutoBrightness(activity: Activity) {
        Settings.System.putInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
    }

    /**
     * 开启亮度自动调节
     */
    fun startAutoBrightness(activity: Activity) {
        Settings.System.putInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        )
    }

    /**
     * 保存亮度设置状态
     */
    fun saveBrightness(resolver: ContentResolver, brightness: Int) {
        val uri = Settings.System.getUriFor("screen_brightness")
        Settings.System.putInt(resolver, "screen_brightness", brightness)
        // resolver.registerContentObserver(uri, true, myContentObserver);
        resolver.notifyChange(uri, null)
    }
}