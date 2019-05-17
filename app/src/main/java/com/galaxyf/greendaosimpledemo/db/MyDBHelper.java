package com.galaxyf.greendaosimpledemo.db;

import android.content.Context;

import com.galaxyf.greendaosimpledemo.dao.DaoMaster;
import com.galaxyf.greendaosimpledemo.dao.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Wongerfeng on 2018/9/13.
 * 数据库迁移脚本
 */

public class MyDBHelper extends DaoMaster.OpenHelper {

    public static final String dbName = "user_db";

    public MyDBHelper(Context context) {
        super(context, dbName, null);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //不能直接升级数据库，因为会删除旧表
        if (newVersion > oldVersion){
            MigrationHelper.getInstance().migrate(db, UserDao.class);

        }
    }
}
