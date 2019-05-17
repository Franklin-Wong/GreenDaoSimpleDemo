package com.galaxyf.greendaosimpledemo.db;

import android.database.Cursor;
import android.text.TextUtils;

import com.galaxyf.greendaosimpledemo.dao.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wongerfeng on 2018/9/13.
 * 数据库升级脚本
 */

public class MigrationHelper {
    private static final String TAG = "MigrationHelper";

    public static final String CONVERSATION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";

    public static MigrationHelper getInstance(){
        return InstanceHolder.sMigrationHelper;
    }

    static class InstanceHolder{
        static MigrationHelper sMigrationHelper = new MigrationHelper();
    }

    /**
     *
     * @param db
     * @param daoClasses
     */
    public void migrate(Database db, Class<? extends AbstractDao<?,?>> ...daoClasses){
        generateTempTables(db, daoClasses);
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, false);
        restoreData(db, daoClasses);

    }

    /**
     *
     * @param db
     * @param daoClasses
     */
    private void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>[] daoClasses) {

        for (int i=0;i < daoClasses.length;i++){
            //retrieve the required information from the DAO classes
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String divider = "";
            //获取 表名
            String tableName = daoConfig.tablename;
            //获取临时表名
            String tempTableName = daoConfig.tablename.concat("_TEMP ");
            //创建属性 集合
            ArrayList<String> properties = new ArrayList<>();
            //创建 SQL创建表格字符串
            StringBuilder createTableStringBuilder = new StringBuilder();
            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName);
            int length = daoConfig.properties.length;

            for (int j=0;j < length; j++){
                //获取 列名称
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tableName).contains(columnName)){
                    //添加 列名
                    properties.add(columnName);

                    String type = null;

                    try {
                        //获取 元素 数据类型
                        type = getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);

                    if (daoConfig.properties[j].primaryKey){
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }
                    divider = ",";

                }
            }
            createTableStringBuilder.append(");");

            db.execSQL(createTableStringBuilder.toString());

            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("INSERT INTO").append(tableName).append("(");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" ) SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",",properties));
            insertTableStringBuilder.append("FROM").append(tableName).append(";");

            db.execSQL(insertTableStringBuilder.toString());
        }
    }
    /**
     * 存储新的数据库表格
     * @param db
     * @param daoClasses
     */
    private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>[] daoClasses) {

        for (int i=0;i < daoClasses.length; i++){
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
//            String divider = "";

            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");

            ArrayList<String> properties = new ArrayList<>();

            for (int j=0;j < daoConfig.properties.length; j++){
                String columnName = daoConfig.properties[i].columnName;

                if (getColumns(db, tempTableName).contains(columnName)){
                    properties.add(columnName);
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("INSERT INTO").append(tableName).append("(");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT");
            insertTableStringBuilder.append(TextUtils.join(",",properties));
            insertTableStringBuilder.append("FROM").append(tempTableName).append(";");

            //创建删除表格语句
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE").append(tempTableName);

            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());


        }

    }
    /**
     *
     * @param type
     * @return
     * @throws Exception
     */
    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)){
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(int.class) || type.equals(Integer.class) || type.equals(long.class)){
            return "INTEGER";
        }
        if (type.equals(Boolean.class)){
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSATION_CLASS_NOT_FOUND_EXCEPTION.concat(" - CLASS:").concat(type.toString()));
        exception.printStackTrace();

        throw exception;
    }

    /**
     *
     * @param db
     * @param tableName
     * @return
     */
    private List<String> getColumns(Database db, String tableName) {
        ArrayList<String> columns = new ArrayList<>();

        Cursor cursor = null;

//        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);

            if (cursor != null){
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));

            }
//        }catch (Exception e){
//            Log.i(TAG, "getColumns:tableName= "+ tablename);
//            e.printStackTrace();
//        }
// finally {
            if (cursor != null){
                cursor.close();
            }
//        }


        return columns;
    }



}
