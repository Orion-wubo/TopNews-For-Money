package com.fengmap.kotlindemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fengmap.kotlindemo.bean.DaoMaster;
import com.fengmap.kotlindemo.bean.DaoSession;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.bean.NewsInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by bai on 2018/12/19.
 */

public class DBManager {
    private final static String dbName = "test_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(NewsInfo user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao userDao = daoSession.getNewsInfoDao();
        userDao.insertOrReplace(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<NewsInfo> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao NewsInfoDao = daoSession.getNewsInfoDao();
        NewsInfoDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param NewsInfo
     */
    public void deleteUser(NewsInfo NewsInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao NewsInfoDao = daoSession.getNewsInfoDao();
        NewsInfoDao.delete(NewsInfo);
    }

    /**
     * 更新一条记录
     *
     * @param NewsInfo
     */
    public void updateUser(NewsInfo NewsInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao NewsInfoDao = daoSession.getNewsInfoDao();
        NewsInfoDao.update(NewsInfo);
    }

    /**
     * 查询用户列表
     */
    public List<NewsInfo> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao NewsInfoDao = daoSession.getNewsInfoDao();
        QueryBuilder<NewsInfo> qb = NewsInfoDao.queryBuilder();
        List<NewsInfo> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public boolean queryUserById(Long id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        NewsInfoDao newsInfoDao = daoSession.getNewsInfoDao();
        QueryBuilder<NewsInfo> qb = newsInfoDao.queryBuilder();
        qb.where(NewsInfoDao.Properties.Id.gt(id)).orderAsc(NewsInfoDao.Properties.Id);
        List<NewsInfo> list = qb.list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
