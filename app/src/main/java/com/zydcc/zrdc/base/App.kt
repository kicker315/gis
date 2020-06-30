package com.zydcc.zrdc.base

import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.Utils
<<<<<<< HEAD
import com.zydcc.zrdc.greendao.helper.DbOpenHelper
import com.zydcc.zrdc.greendao.DaoMaster
import com.zydcc.zrdc.greendao.DaoSession
import com.zydcc.zrdc.utilities.DATABASE_NAME
import com.zydcc.zrdc.utilities.DATABASE_PATH
import java.io.IOException
=======
import com.zydcc.zrdc.location.LocationService
>>>>>>> parent of 35bc049... 数据库框架变更

/**
 * =======================================
 * App
 * Create by ningsikai 2020/5/19:1:17 PM
 * ========================================
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        CrashUtils.init()
<<<<<<< HEAD
        setDatabase()
        try {
            copyDatabase()
            return
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun copyDatabase() {
        val dbFile = getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            dbFile.parentFile.mkdir()
            ResourceUtils.copyFileFromAssets(DATABASE_PATH, dbFile.path)
        }

        dicDaoSession = DaoMaster(DaoMaster.DevOpenHelper(this, DATABASE_NAME, null).writableDb).newSession()
    }

    private fun setDatabase() {
        mHelper =
            DbOpenHelper(this, "zydcc-db", null)
        db = mHelper.writableDatabase
        mDaoMaster = DaoMaster(db)
        mDaoSession = mDaoMaster.newSession()
    }

    companion object {
        var db: SQLiteDatabase ?= null
        lateinit var dicDaoSession: DaoSession
        lateinit var mDaoSession: DaoSession
=======
        locationService = LocationService(applicationContext)
    }

    companion object {
        // 百度定位监听
        var locationService: LocationService? = null
>>>>>>> parent of 35bc049... 数据库框架变更
    }

}