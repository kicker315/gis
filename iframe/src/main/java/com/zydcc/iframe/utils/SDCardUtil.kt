package com.zydcc.iframe.utils

import android.os.Environment
import android.os.StatFs
import com.zydcc.iframe.app.BaseApp
import java.io.File

/**
 * =======================================
 * SD卡相关的辅助类
 * Create by ningsikai 2020/6/3:8:46 AM
 * ========================================
 */
object SDCardUtil  {
    /**
     * 判断SDCard是否可用
     */
    val isSDCardEnable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取SD卡路径
     */
    val sDCardPath: String
        get() = Environment.getExternalStorageDirectory()
            .path + File.separator//应用目录//SD卡目录

    /**
     * 获取可用Path，默认返回sd卡路径，如果sd卡不可用，返回程序存储沙盒路径
     */
    val enablePath: String
        get() = if (isSDCardEnable) {
            sDCardPath //SD卡目录
        } else {
            BaseApp.context.getExternalFilesDir(null)
                .toString() + File.separator //应用目录
        }// 获取空闲的数据块的数量
    // 获取单个数据块的大小（byte）

    /**
     * 获取SD卡的剩余容量 单位byte
     */
    val sDCardAllSize: Long
        get() {
            if (isSDCardEnable) {
                val stat = StatFs(sDCardPath)
                // 获取空闲的数据块的数量
                val availableBlocks = stat.availableBlocks.toLong() - 4
                // 获取单个数据块的大小（byte）
                val freeBlocks = stat.availableBlocks.toLong()
                return freeBlocks * availableBlocks
            }
            return 0
        }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    fun getFreeBytes(path: String): Long {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        var filePath = path
        filePath = if (filePath.startsWith(sDCardPath)) {
            sDCardPath
        } else { // 如果是内部存储的路径，则获取内存存储的可用容量
            Environment.getDataDirectory().absolutePath
        }
        val stat = StatFs(filePath)
        val availableBlocks = stat.availableBlocks.toLong() - 4
        return stat.blockSize * availableBlocks
    }

    /**
     * 获取系统存储路径
     */
    val rootDirectoryPath: String
        get() = Environment.getRootDirectory()
            .absolutePath + File.separator
}