package com.fengmap.kotlindemo.bean;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.fengmap.kotlindemo.bean.NewsInfo;

import com.fengmap.kotlindemo.bean.NewsInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newsInfoDaoConfig;

    private final NewsInfoDao newsInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newsInfoDaoConfig = daoConfigMap.get(NewsInfoDao.class).clone();
        newsInfoDaoConfig.initIdentityScope(type);

        newsInfoDao = new NewsInfoDao(newsInfoDaoConfig, this);

        registerDao(NewsInfo.class, newsInfoDao);
    }
    
    public void clear() {
        newsInfoDaoConfig.getIdentityScope().clear();
    }

    public NewsInfoDao getNewsInfoDao() {
        return newsInfoDao;
    }

}
