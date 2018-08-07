/**
 * Copyright 2018, MKS GROUP.
 */
package mks.ownbank.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import mks.ownbank.db.dao.BaseDao;
import mks.ownbank.db.dao.SettingDao;
import mks.ownbank.db.entiy.Setting;

/**
 * @author ThachLN
 *
 */
public class SettingDaoImpl extends BaseDao implements SettingDao {

    public SettingDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * [Explain the description for this method here].
     * @param Setting
     * @see mks.ownbank.db.dao.SettingDao#save(mks.ownbank.db.entiy.Setting)
     */
    @Override
    public void save(Setting Setting) {
        // TODO Auto-generated method stub

    }

    /**
     * [Explain the description for this method here].
     * @return
     * @see mks.ownbank.db.dao.SettingDao#list()
     */
    @Override
    public List<Setting> list() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * [Explain the description for this method here].
     * @param userId
     * @return
     * @see mks.ownbank.db.dao.SettingDao#findByUserId(java.lang.String)
     */
    @Override
    public List<Setting> findByUserId(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * [Explain the description for this method here].
     * @param Setting
     * @see mks.ownbank.db.dao.SettingDao#saveOrUpdate(mks.ownbank.db.entiy.Setting)
     */
    @Override
    public void saveOrUpdate(Setting Setting) {
        // TODO Auto-generated method stub

    }

}
