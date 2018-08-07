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
import mks.ownbank.db.dao.LuckyNumDao;
import mks.ownbank.db.entiy.LuckyNum;

/**
 * @author ThachLN
 */
@Repository
public class LuckyNumDaoImpl extends BaseDao implements LuckyNumDao {
    private final static Logger LOG = Logger.getLogger(LuckyNumDaoImpl.class.getName());

    public LuckyNumDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * [Explain the description for this method here].
     * @param vote
     * @see mks.ownbank.db.dao.LuckyNumDao#save(mks.ownbank.db.entiy.Vote)
     */
    @Override
    @Transactional
    public void save(LuckyNum vote) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            
            // Save the vote information
            session.save(vote);
            
            // Save the client information
            vote.getLtiLaunch().setVoteId(vote.getId());
            session.save(vote.getLtiLaunch());

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, String.format("Could not save the lucky number [%s]", vote), ex);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void saveOrUpdate(LuckyNum vote) {
        Session session = getCurrentSession();

        try {
            // Save the vote information
            session.save(vote);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, String.format("Could not save the lucky number [%s]", vote), ex);
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
    public List<LuckyNum> list() {
        Session session = getCurrentSession();
        List<LuckyNum> listUser = (List<LuckyNum>) session.createCriteria(LuckyNum.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        session.close();

        return listUser;
    }

    @Override
    public List<LuckyNum> findVoteByteUserId(String userId) {
        Session session = getCurrentSession();
        String hsql = "from LuckyNum u where u.userid = :userId";
        Query query = session.createQuery(hsql);
        
        query.setString("userId", userId);
        // session.close();
        
        return query.list();
    }
}
