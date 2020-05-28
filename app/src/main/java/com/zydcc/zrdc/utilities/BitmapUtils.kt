package com.zydcc.zrdc.utilities

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import com.esri.arcgisruntime.mapping.view.MapView
import java.io.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/28:9:47 PM
 * ========================================
 */
/**
 * ===============================================
 * 图片压缩处理
 * decodeSampledBitmapFromResource   压缩图片资源文件里的图片
 * zoomImage                         缩放图像
 * bytes2Bimap                       二进制文件 转bitmap
 * bimap2Bytes                       bitmap 转 二进制文件
 * rotaingImageView                  旋转图片
 * fastblur                          模糊图像
 * compressByBitmapSize              根据图片大小按比例压缩 最大不超过100K
 * getBitmapByPath                   通过path 获取bitmap
 * saveFile                          保存图片到本地
 * watermarkBitmap                    水印
 * id2Bitmap                         id 转bitmap
 * getCacheBitmapFromView             获取一个 View 的缓存视图
 * myShot                             获取屏幕截图
 * Created by ningsk on 2019/9/11 13:21
 * ==============================================
 */
object BitmapUtils {
    fun fastblur(context: Context?, sentBitmap: Bitmap, radius: Int): Bitmap? {
        val bitmap = sentBitmap.copy(sentBitmap.config, true)
        if (radius < 1) {
            return null
        }
        val w = bitmap.width
        val h = bitmap.height
        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)
        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1
        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))
        var divsum = div + 1 shr 1
        divsum *= divsum
        val temp = 256 * divsum
        val dv = IntArray(temp)
        i = 0
        while (i < temp) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        yw = yi
        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int
        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius
            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x
                sir = stack[i + radius]
                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]
                rbs = r1 - Math.abs(i)
                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                pix[yi] = (-0x1000000 and pix[yi] or (dv[rsum] shl 16)
                        or (dv[gsum] shl 8) or dv[bsum])
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]
                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }

    /**
     * 压缩资源文件里图片
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun decodeSampledBitmapFromResource(
        res: Resources?,
        resId: Int, reqWidth: Int, reqHeight: Int
    ): Bitmap {

        // 给定的BitmapFactory设置解码的参数
        val options = BitmapFactory.Options()
        // 从解码器中获取原始图片的宽高，这样避免了直接申请内存空间
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(
            options, reqWidth,
            reqHeight
        )

        // 压缩完后便可以将inJustDecodeBounds设置为false了。
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    /**
     * 指定图片的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int, reqHeight: Int
    ): Int {
        // 原始图片的宽、高
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

//      if (height > reqHeight || width > reqWidth) {
//          //这里有两种压缩方式，可供选择。
//          /**
//           * 压缩方式二
//           */
//          // final int halfHeight = height / 2;
//          // final int halfWidth = width / 2;
//          // while ((halfHeight / inSampleSize) > reqHeight
//          // && (halfWidth / inSampleSize) > reqWidth) {
//          // inSampleSize *= 2;
//          // }
//
        /**
         * 压缩方式一
         */
        // 计算压缩的比例：分为宽高比例
        val heightRatio = Math.round(
            height.toFloat()
                    / reqHeight.toFloat()
        )
        val widthRatio =
            Math.round(width.toFloat() / reqWidth.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        //      }
        return inSampleSize
    }

    /**
     * 缩放图像
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    private fun zoomImage(
        bgimage: Bitmap, newWidth: Double,
        newHeight: Double
    ): Bitmap {
        // 获取这个图片的宽和高
        val width = bgimage.width.toFloat()
        val height = bgimage.height.toFloat()

        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        val bitmap = Bitmap.createBitmap(
            bgimage, 0, 0, width.toInt(),
            height.toInt(), matrix, true
        )
        val width2 = bitmap.width.toFloat()
        val height2 = bitmap.height.toFloat()
        return bitmap
    }

    fun bytes2Bimap(b: ByteArray): Bitmap? {
        return if (b.size != 0) {
            BitmapFactory.decodeByteArray(b, 0, b.size)
        } else {
            null
        }
    }

    fun bimap2Bytes(bitmap: Bitmap): ByteArray {
        val output = ByteArrayOutputStream() //初始化一个流对象
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output) //把bitmap100%高质量压缩 到 output对象里

//        bitmap.recycle();//自由选择是否进行回收
        val result = output.toByteArray() //转换成功了
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 旋转图像
     * @param angle
     * @param bitmap
     * @return
     */
    fun rotaingImageView(angle: Int, bitmap: Bitmap): Bitmap {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        //        System.out.println("angle2=" + angle);
        // 创建新的图片
        return Bitmap.createBitmap(
            bitmap, 0, 0,
            bitmap.width, bitmap.height, matrix, true
        )
    }

    /**
     * 根据图片大小按比例压缩 最大不超过100K
     *
     * @param bitmap
     * @return
     */
    fun compressByBitmapSize(bitmap: Bitmap): Bitmap? {
        val maxSize = 100.00
        var midbitmap: Bitmap? = null
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val temp: ByteArray
        temp = baos.toByteArray()
        val mid = temp.size / 1024.toDouble()
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            val i = mid / maxSize
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            midbitmap = zoomImage(
                bitmap, bitmap.width / Math.sqrt(i),
                bitmap.height / Math.sqrt(i)
            )
            bitmap.recycle()
        } else {
            return bitmap
        }
        return midbitmap
    }

    /**
     * 将本地图片转换成bitmap 并压缩
     * @param filePath
     * @param zoom
     * @return
     */
    fun getBitmapByPath(filePath: String?, zoom: Int): Bitmap {
        return BitmapFactory.decodeFile(
            filePath,
            getBitmapOption(zoom)
        )
    }

    /**
     * 如果图片过大，可能导致Bitmap对象装不下图片
     * 解决办法：
     * @param inSampleSize
     * @return
     */
    private fun getBitmapOption(inSampleSize: Int): BitmapFactory.Options {
        System.gc()
        val options = BitmapFactory.Options()
        options.inPurgeable = true
        options.inSampleSize = inSampleSize
        return options
    }



    /**
     * 水印
     * @param src 添加水印的图
     * @param watermark 水印图
     * @return
     */
    // 加水印 也可以加文字
    fun watermarkBitmap(
        src: Bitmap, watermark: Bitmap?,
        title: String?
    ): Bitmap? {
        val w = src.width
        val h = src.height
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        val newb =
            Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) // 创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        cv.drawBitmap(src, 0f, 0f, null) // 在 0，0坐标开始画入src
        val paint = Paint()
        //加入图片
        if (watermark != null) {
            val ww = watermark.width
            val wh = watermark.height
            paint.alpha = 50
            cv.drawBitmap(
                watermark,
                w - ww + 5.toFloat(),
                h - wh + 5.toFloat(),
                paint
            ) // 在src的右下角画入水印
        }
        //加入文字
        if (title != null) {
            val familyName = "宋体"
            val font = Typeface.create(familyName, Typeface.BOLD)
            val textPaint = TextPaint()
            textPaint.color = Color.RED
            textPaint.typeface = font
            textPaint.textSize = 22f
            //这里是自动换行的
            val layout = StaticLayout(
                title,
                textPaint,
                w,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                true
            )
            layout.draw(cv)
            //文字就加左上角算了
            //cv.drawText(title,0,40,paint);
        }
        cv.save() // 保存
        cv.restore() // 存储
        src.recycle()
        return newb
    }

    /**
     * id 转bitmap
     * @param res
     * @param id
     * @return
     */
    fun id2Bitmap(res: Resources?, id: Int): Bitmap {
        return BitmapFactory.decodeResource(res, id)
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    fun getCacheBitmapFromView(view: View): Bitmap? {
        val drawingCacheEnabled = true
        view.isDrawingCacheEnabled = drawingCacheEnabled
        view.buildDrawingCache(drawingCacheEnabled)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache)
            view.isDrawingCacheEnabled = false
        } else {
            bitmap = null
        }
        return bitmap
    }

    /**
     * 获取屏幕截图
     * @param activity
     * @param state  是否带状态栏
     * @return
     */
    fun myShot(activity: Activity, state: Boolean): Bitmap? {
        // 获取windows中最顶层的view
        val view = activity.window.decorView
        view.buildDrawingCache()

        // 获取状态栏高度
        val rect = Rect()
        view.getWindowVisibleDisplayFrame(rect)
        val statusBarHeights = rect.top
        val display = activity.windowManager.defaultDisplay

        // 获取屏幕宽和高
        val widths = display.width
        val heights = display.height

        // 允许当前窗口保存缓存信息
        view.isDrawingCacheEnabled = true
        var bmp: Bitmap? = null
        if (state) {
            val drawingCache = view.drawingCache
            if (drawingCache != null) {
                bmp = Bitmap.createBitmap(drawingCache)
            }
        } else {
            // 去掉状态栏
            bmp = Bitmap.createBitmap(
                view.drawingCache, 0,
                statusBarHeights, widths, heights - statusBarHeights
            )
        }


        // 允许当前窗口保存缓存信息
        view.isDrawingCacheEnabled = false
        return bmp
    }

    /**
     * 把一个MapView的对象转换成bitmap
     */
    fun getCacheBitmapFromView(v: MapView): Bitmap {
        v.clearFocus()
        v.setPressed(false)
        //能画缓存就返回false
        val willNotCache: Boolean = v.willNotCacheDrawing()
        v.setWillNotCacheDrawing(false)
        val color: Int = v.getDrawingCacheBackgroundColor()
        v.setDrawingCacheBackgroundColor(0)
        if (color != 0) {
            v.destroyDrawingCache()
        }
        v.buildDrawingCache()
        val cacheBitmap: Bitmap = v.drawingCache
        // Restore the view
        v.destroyDrawingCache()
        v.setWillNotCacheDrawing(willNotCache)
        v.setDrawingCacheBackgroundColor(color)
        return cacheBitmap
    }
}

