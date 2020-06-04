package com.zydcc.arcgis.utils;

import android.os.Environment;

import com.zydcc.zrdc.base.App;

import java.util.UUID;

/**
 * =======================================
 * 路径常量
 * Create by ningsikai 2020/6/4:4:38 PM
 * ========================================
 */
public class ConstPath {
    private String PATH_SDCARD;//SD卡路径
    private String PATH_SDCARD_APP;//程序的SD路径，访问需要用户确认
    private String PATH_IMG_CACHE;//缓存图片路径
    private String PATH_TEMP;//暂存路径
    private String PATH_FILE;//文件路径
    private String PATH_LOG; //日志路径

    private static class InstancesClass {
        private static final ConstPath instance = new ConstPath();
    }

    public static ConstPath getInstance() {
        return InstancesClass.instance;
    }

    public ConstPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            PATH_SDCARD = Environment.getExternalStorageDirectory().getPath();//获取根目录
        } else {
            PATH_SDCARD = Environment.getRootDirectory().getAbsolutePath();//获取根目录
        }

        PATH_SDCARD_APP = PATH_SDCARD + "/frame/";
        PATH_IMG_CACHE = App.Companion.getAPP_CONTEXT().getExternalCacheDir() + "/img_cache/";
        PATH_TEMP = App.Companion.getAPP_CONTEXT().getExternalCacheDir() + "/temp/";
        PATH_LOG = App.Companion.getAPP_CONTEXT().getExternalFilesDir(null) + "/log/";
        PATH_FILE = App.Companion.getAPP_CONTEXT().getExternalFilesDir(null) + "/file/";
    }

    public String getPathSdcard() {
        return PATH_SDCARD;
    }

    public String getPathSdcardApp() {
        return PATH_SDCARD_APP;
    }

    public String getPathImgCache() {
        return PATH_IMG_CACHE;
    }

    public String getPathTemp() {
        return PATH_TEMP;
    }

    public String getPathFile() {
        return PATH_FILE;
    }

    public String getPathLog() {
        return PATH_LOG;
    }

    public String getTempFilePath(String suffix) {
        return getPathTemp() + UUID.randomUUID().toString() + "." + suffix;
    }
}
