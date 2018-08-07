package mks.ownbank.db.dao;

import java.util.List;

import mks.ownbank.db.entiy.Setting;

public interface SettingDao {

    void save(Setting Setting);

    List<Setting> list();

    List<Setting> findByUserId(String userId);

    void saveOrUpdate(Setting Setting);
}