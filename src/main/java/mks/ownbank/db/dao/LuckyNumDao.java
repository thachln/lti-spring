package mks.ownbank.db.dao;

import java.util.List;

import mks.ownbank.db.entiy.LuckyNum;

public interface LuckyNumDao {

    void save(LuckyNum vote);

    List<LuckyNum> list();

    List<LuckyNum> findVoteByteUserId(String userId);

    void saveOrUpdate(LuckyNum vote);

}