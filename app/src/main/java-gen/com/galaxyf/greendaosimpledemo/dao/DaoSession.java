package com.galaxyf.greendaosimpledemo.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.galaxyf.greendaosimpledemo.entity.Student;
import com.galaxyf.greendaosimpledemo.entity.User;

import com.galaxyf.greendaosimpledemo.dao.StudentDao;
import com.galaxyf.greendaosimpledemo.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig studentDaoConfig;
    private final DaoConfig userDaoConfig;

    private final StudentDao studentDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        studentDao = new StudentDao(studentDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Student.class, studentDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        studentDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
