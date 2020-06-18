package com.zydcc.zrdc.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;



import org.greenrobot.greendao.database.Database;

/**
 * =======================================
 * <p>
 * Create by ningsikai 2020/6/18:2:27 PM
 * ========================================
 */
public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db,
                new MigrationHelper.ReCreateAllTableListener() {

                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                }, LayerDao.class);
    }
}
