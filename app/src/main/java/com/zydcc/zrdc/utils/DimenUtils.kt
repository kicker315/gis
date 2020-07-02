package com.zydcc.zrdc.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:11:08 AM
 * ========================================
 */
object DimenUtils {
    /**
     * Get the size of current screen.
     *
     * @param context the context
     * @return the size
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    fun getScreenSize(context: Context): Point {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point(display.width, display.height)
        } else {
            val point = Point()
            display.getSize(point)
            point
        }
    }

    /**
     * Get the width of current screen.
     *
     * @param contex the contex
     * @return the width
     */
    fun getScreenWidth(contex: Context): Int {
        val dm = contex.resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * Get the height of current screen.
     *
     * @param contex the mContext
     * @return the height
     */
    fun getScreenHeight(contex: Context): Int {
        val dm = contex.resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * Convert dp to pixel.
     *
     * @param context mContext to use
     * @param dp dp
     * @return pixel
     */
    fun dpToPx(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5).toInt()
    }

    /**
     * Convert pixel to dp.
     *
     * @param context the mContext to use
     * @param px pixel
     * @return dp
     */
    fun pxToDp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5).toInt()
    }

    fun getDimens(context: Context, resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }
}

