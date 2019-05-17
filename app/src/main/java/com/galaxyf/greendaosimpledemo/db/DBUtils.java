package com.galaxyf.greendaosimpledemo.db;

import android.content.Context;

import com.galaxyf.greendaosimpledemo.dao.UserDao;
import com.galaxyf.greendaosimpledemo.entity.Student;
import com.galaxyf.greendaosimpledemo.entity.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Wongerfeng on 2018/9/12.
 */

public class DBUtils {
    public static final String PASSWORD = "USER";
    /**
     *
     * @param user
     */
    public static void insert(Context context, User user){
        DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao()
                .insert(user);
    }

    /**
     *
     * @return List<User>
     */
    public static List<User> queryAll(Context context){
        QueryBuilder<User> queryBuilder = DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao()
                .queryBuilder();
        return queryBuilder.build().list();
    }

    /**
     *
     * @param context
     * @param object
     * @return
     */
    public static List<User> queryById(Context context, Object object){
        List<User> users = DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao()
                .queryBuilder()
                .where(UserDao.Properties.UserId.eq(object))
                .build()
                .list();
        return users;

    }

    /**
     *
     * @param context
     * @param id
     */
    public static void deleteByKey(Context context, long id){
        DBManager.getSingleInstance().init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao()
                .deleteByKey(id);
    }

    /**
     *
     * @param context
     * @param user
     */
    public static void delete(Context context, User user){
        UserDao userDao = DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao();

        User unique = userDao
                .queryBuilder()
                .where(UserDao.Properties.Nickname.eq(user.getNickname()), UserDao.Properties.Username.eq(user.getUsername()))
                .build().unique();
        if (unique != null){
            userDao.delete(unique);
        }

    }

    /**
     *
     * @param context
     * @param user
     * @param newUser
     */
    public static User updateData(Context context, User user, User newUser){
        UserDao userDao = DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao();
        User unique = userDao.queryBuilder()
                .where(UserDao.Properties.UserId.eq(user.getUserId()))
                .build().unique();
        if (unique != null){
            unique = newUser;
            userDao.update(unique);
            return unique;
        }

        return null;
    }

    /**
     * 分页加载数据
     * @param context
     * @param pageSize
     * @param num
     * @return
     */
    public static List<User> queryByPage(Context context, int pageSize, int num){

        return DBManager.getSingleInstance()
                .init(context, PASSWORD)
                .getDaoSession(context, PASSWORD)
                .getUserDao()
                .queryBuilder()
                .offset(pageSize - 1)
                .limit(num)
                //ID降序
//                .orderDesc(UserDao.Properties.UserId)
                //ID升序查询
                .orderAsc(UserDao.Properties.UserId)
                .build()
                .list();

    }


    public static List<Student> queryStudentByUser(Context context, User user){

        return user.getStudentList();
    }

    public static Student insertStudent(Context context, User user, Student student){
        student.setUserId(user.getUserId());
//        student.setUser(user);
        DBManager.getSingleInstance().getDaoSession(context,PASSWORD)
                .getStudentDao().insertOrReplace(student);
        return student;
    }

}
