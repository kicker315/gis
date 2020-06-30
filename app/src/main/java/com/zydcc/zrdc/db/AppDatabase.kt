package com.zydcc.zrdc.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zydcc.zrdc.db.dao.CodeBrushDao
import com.zydcc.zrdc.db.dao.DLTBDao
import com.zydcc.zrdc.db.dao.LayerDao
import com.zydcc.zrdc.db.table.CodeBrush
import com.zydcc.zrdc.db.table.DLTB
import com.zydcc.zrdc.db.table.Layer
import com.zydcc.zrdc.utilities.DATABASE_NAME
import com.zydcc.zrdc.utilities.DATABASE_PATH

/**
 * =======================================
 * The Room database for this app
 * Create by ningsikai 2020/5/18:2:56 PM
 * ========================================
 */
@Database(entities = [DLTB::class, CodeBrush::class, Layer::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dltbDao(): DLTBDao
    abstract fun codeBrushDao(): CodeBrushDao
    abstract fun layerDao(): LayerDao

    companion object {
        // For singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance?: synchronized(this) {
                instance?: buildDatabase(context).also { instance = it }
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