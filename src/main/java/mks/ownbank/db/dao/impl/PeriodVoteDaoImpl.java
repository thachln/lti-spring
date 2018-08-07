/**
 * Licensed to FA Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * FA licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package mks.ownbank.db.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import mks.ownbank.db.dao.BaseDao;
import mks.ownbank.db.dao.PeriodVoteDao;
import mks.ownbank.db.entiy.PeriodVote;

/**
 * @author ThachLN
 */
@Repository
public class PeriodVoteDaoImpl extends BaseDao implements PeriodVoteDao {
    private final static Logger LOG = Logger.getLogger(PeriodVoteDaoImpl.class.getName());

    public PeriodVoteDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * [Explain the description for this method here].
     * @param periodVote
     * @see mks.ownbank.db.dao.LuckyNumDao#save(mks.ownbank.db.entiy.Vote)
     */
    @Override
    @Transactional
    public void save(PeriodVote periodVote) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            
            // Save the vote information
            session.save(periodVote);
            
            // Save the client information
            periodVote.getLtiLaunch().setVoteId(periodVote.getId());
            session.save(periodVote.getLtiLaunch());

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, String.format("Could not save the vote [%s]", periodVote), ex);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void saveOrUpdate(PeriodVote periodVote) {
        Session session = getCurrentSession();

        try {
            // Save the vote information
            session.save(periodVote);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, String.format("Could not save the vote [%s]", periodVote), ex);
        } finally {
             session.close();
        }
    }
    
    /**
     * [Explain the description for this method here].
     * @return
     * @see mks.ownbank.db.dao.LuckyNumDao#list()
     */
    @Override
    public List<PeriodVote> list() {
        Session session = getCurrentSession();
        List<PeriodVote> listPeriodVote = (List<PeriodVote>) session.createCriteria(PeriodVote.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        session.close();
        return listPeriodVote;
    }

    @Override
    public List<PeriodVote> findVoteByteUserId(String userId) {
        Session session = getCurrentSession();
        String hsql = "from PeriodVote u where u.userid = :userId";
        Query query = session.createQuery(hsql);
        
        query.setString("userId", userId);
        
        // session.close();
        return query.list();
    }
}
