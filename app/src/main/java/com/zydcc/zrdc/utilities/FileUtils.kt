package com.zydcc.zrdc.utilities

import java.io.File
import java.io.FilenameFilter
import java.util.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:12:12 PM
 * ========================================
 */
object FileUtils {


    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isDir(file: File): Boolean {
        return isFileExists(file) && file.isDirectory
    }


    private fun isSpace(s: String?): Boolean {
        if (s == null) {
            return true
        }
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    /**
     * 创建未存在的文件夹
     */
    fun makeDirs(file: File): File? {
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * 大小写忽略
     *
     * @param dirPath 目录路径
     * @param suffix 后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dirPath: String, suffix: String,
        isRecursive: Boolean
    ): List<File> {
        val file = getFileByPath(dirPath) ?: return emptyList()
        return listFilesInDirWithFilter(file, suffix, isRecursive)
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * 大小写忽略
     *
     * @param dir 目录
     * @param suffix 后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dir: File,
        suffix: String,
        isRecursive: Boolean
    ): List<File> {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, suffix)
        }
        if (!isDir(dir)) {
            return emptyList()
        }
        val list: MutableList<File> =
            ArrayList()
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.name.toUpperCase(Locale.ROOT).endsWith(suffix.toUpperCase(Locale.ROOT))) {
                    list.add(file)
                }
            }
        }
        return list
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dirPath 目录路径
     * @param suffix 后缀名
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dirPath: String,
        suffix: String
    ): List<File?>? {
        val file = getFileByPath(dirPath) ?: return emptyList()
        return listFilesInDirWithFilter(file, suffix)
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dir 目录
     * @param suffix 后缀名
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dir: File,
        suffix: String
    ): List<File> {
        if (!isDir(dir)) {
            return emptyList()
        }
        val list: MutableList<File> =
            ArrayList()
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.name.toUpperCase(Locale.ROOT).endsWith(suffix.toUpperCase(Locale.ROOT))) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(listFilesInDirWithFilter(file, suffix)!!)
                }
            }
        }
        return list
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dirPath 目录路径
     * @param filter 过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dirPath: String, filter: FilenameFilter,
        isRecursive: Boolean
    ): List<File?>? {
        val file = getFileByPath(dirPath) ?: return emptyList()
        return listFilesInDirWithFilter(file, filter, isRecursive)
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dir 目录
     * @param filter 过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dir: File?, filter: FilenameFilter,
        isRecursive: Boolean
    ): List<File?>? {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, filter)
        }
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list: MutableList<File?> =
            ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
            }
        }
        return list
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dirPath 目录路径
     * @param filter 过滤器
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dirPath: String,
        filter: FilenameFilter
    ): List<File?>? {
        return listFilesInDirWithFilter(FileUtils.getFileByPath(dirPath), filter)
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dir 目录
     * @param filter 过滤器
     * @return 文件链表
     */
    fun listFilesInDirWithFilter(
        dir: File?,
        filter: FilenameFilter
    ): List<File?>? {
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list: MutableList<File?> =
            ArrayList()
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(listFilesInDirWithFilter(file, filter)!!)
                }
            }
        }
        return list
    }
}