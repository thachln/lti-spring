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
package mks.ownbank.db.entiy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author ThachLN
 *
 */
public class PeriodVote implements Serializable {
    /** Auto increment key. */
    private Integer id;

    /** Code of the event*/
    private String eventId;
    
    /** user id */
    private String userid;
    
    /** Account of voter. This value depends on the setting from the client.*/
    private String username;
    private String fullname;
    private String givenname;
    private String familyname;
    
    /** Date of vote */
    private Date voted;
    
    /** Randomized number */
    private Double voteValue;

    private LogLtiLauch ltiLaunch;

    /**
    * Get value of id.
    * @return the id
    */
    public Integer getId() {
        return id;
    }

    /**
     * Set the value for id.
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * Get value of eventId.
    * @return the eventId
    */
    public String getEventId() {
        return eventId;
    }

    /**
     * Set the value for eventId.
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
    * Get value of userid.
    * @return the userid
    */
    public String getUserid() {
        return userid;
    }

    /**
     * Set the value for userid.
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
    * Get value of username.
    * @return the username
    */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value for username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Get value of fullname.
    * @return the fullname
    */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the value for fullname.
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
    * Get value of givenname.
    * @return the givenname
    */
    public String getGivenname() {
        return givenname;
    }

    /**
     * Set the value for givenname.
     * @param givenname the givenname to set
     */
    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    /**
    * Get value of familyname.
    * @return the familyname
    */
    public String getFamilyname() {
        return familyname;
    }

    /**
     * Set the value for familyname.
     * @param familyname the familyname to set
     */
    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    /**
    * Get value of voted.
    * @return the voted
    */
    public Date getVoted() {
        return voted;
    }

    /**
     * Set the value for voted.
     * @param voted the voted to set
     */
    public void setVoted(Date voted) {
        this.voted = voted;
    }

    /**
    * Get value of voteValue.
    * @return the voteValue
    */
    public Double getVoteValue() {
        return voteValue;
    }

    /**
     * Set the value for voteValue.
     * @param voteValue the voteValue to set
     */
    public void setVoteValue(Double voteValue) {
        this.voteValue = voteValue;
    }

    /**
    * Get value of ltiLaunch.
    * @return the ltiLaunch
    */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "log_ltilaunch_id")
    public LogLtiLauch getLtiLaunch() {
        return ltiLaunch;
    }

    /**
     * Set the value for ltiLaunch.
     * @param ltiLaunch the ltiLaunch to set
     */
    public void setLtiLaunch(LogLtiLauch ltiLaunch) {
        this.ltiLaunch = ltiLaunch;
    }

}
