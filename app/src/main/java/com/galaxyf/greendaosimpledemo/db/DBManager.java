package com.galaxyf.greendaosimpledemo.db;

import android.content.Context;

import com.galaxyf.greendaosimpledemo.dao.DaoMaster;
import com.galaxyf.greendaosimpledemo.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Wongerfeng on 2018/9/12.
 */

public class DBManager {
    // 是否加密
    public static final boolean ENCRYPTED = false;
    public static final String dbName = "user_db";
    private DaoMaster.DevOpenHelper mOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private MyDBHelper myDBHelper;
//    private volatile static DBManager instance;

    public static DBManager getSingleInstance(){
        return InstanceHolder.INSTANCE;
    }


    private static class InstanceHolder{
        private static DBManager INSTANCE = new DBManager();
    }

    public DBManager init(Context context,String password) {
        //创建openHelper
//        mOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        myDBHelper = new MyDBHelper(context);

        //DaoMaster对象处置db
        getDaoMaster(context,password);
        //DaoMaster新建session
        getDaoSession(context,password);
        return this;
    }
    /**获取可写db*/
    private Database getWritableDatabase(Context context, String password){
        if (myDBHelper == null){
            init(context,password);
        }
        if (ENCRYPTED){
            return myDBHelper.getEncryptedWritableDb(password);
        }
        return myDBHelper.getWritableDb();
    }

    private DaoMaster getDaoMaster(Context context,String password) {
        if (mDaoMaster == null){
            synchronized (DBManager.class){
                if (mDaoMaster == null){
                    mDaoMaster = new DaoMaster(getWritableDatabase(context,password));
                }
            }
        }
        return mDaoMaster;
    }


    public DaoSession getDaoSession(Context context,String password){
        if (mDaoSession == null){
            synchronized (DBManager.class){
                if (mDaoSession == null){
                    mDaoSession = getDaoMaster(context,password).newSession();
                }
            }
        }
        return mDaoSession;
    }




}
