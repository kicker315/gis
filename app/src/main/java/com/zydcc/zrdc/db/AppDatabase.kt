package com.zydcc.zrdc.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zydcc.zrdc.entity.dic.Dltb
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.entity.dic.Project
import com.zydcc.zrdc.utils.DATABASE_NAME
import com.zydcc.zrdc.utils.DATABASE_PATH

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:8:31 AM
 * ========================================
 */
@Database(entities = [Dltb::class, Layer::class, Project::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dltbDao(): DltbDao
    abstract fun layerDao(): LayerDao
    abstract fun projectDao(): ProjectDao

    companion object {
        // For singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .createFromAsset(DATABASE_PATH)
                .addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // 做一些数据迁移的工作
//                        val request = OneTimeWorkRequestBuilder<ImportDatabaseWorker>().build()
//                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }

    }
}