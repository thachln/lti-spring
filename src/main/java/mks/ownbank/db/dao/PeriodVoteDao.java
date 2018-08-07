package mks.ownbank.db.dao;

import java.util.List;

import mks.ownbank.db.entiy.PeriodVote;

public interface PeriodVoteDao {

    void save(PeriodVote periodVote);

    List<PeriodVote> list();

    List<PeriodVote> findVoteByteUserId(String userId);

    void saveOrUpdate(PeriodVote periodVote);

}