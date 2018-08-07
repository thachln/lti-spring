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

import org.imsglobal.lti.launch.LtiLaunch;

/**
 * @author ThachLN
 *
 */
public class LogLtiLauch implements Serializable {
    /** Auto increment key. */
    private Integer id;

    private String version;
    private String messageType;
    private String resourceLinkId;

    private String contextId;
    private String launchPresentationReturnUrl;
    private String toolConsumerInstanceGuid;
    
    private String remoteAddr;
    private String remoteHost;
    
    private Integer voteId;
    private Integer luckyNumId;

    public LogLtiLauch() {
        
    }

    public LogLtiLauch(LtiLaunch ltiLaunch, String remoteAddr, String remoteHost) {
        this.version = ltiLaunch.getVersion();
        this.messageType = ltiLaunch.getMessageType();
        this.resourceLinkId = ltiLaunch.getResourceLinkId();

        this.contextId = ltiLaunch.getContextId();
        this.launchPresentationReturnUrl = ltiLaunch.getLaunchPresentationReturnUrl();
        this.toolConsumerInstanceGuid = ltiLaunch.getToolConsumerInstanceGuid();
        
        this.remoteAddr = remoteAddr;
        this.remoteHost = remoteHost;
    }

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
    * Get value of version.
    * @return the version
    */
    public String getVersion() {
        return version;
    }

    /**
     * Set the value for version.
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
    * Get value of messageType.
    * @return the messageType
    */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Set the value for messageType.
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
    * Get value of resourceLinkId.
    * @return the resourceLinkId
    */
    public String getResourceLinkId() {
        return resourceLinkId;
    }

    /**
     * Set the value for resourceLinkId.
     * @param resourceLinkId the resourceLinkId to set
     */
    public void setResourceLinkId(String resourceLinkId) {
        this.resourceLinkId = resourceLinkId;
    }

    /**
    * Get value of contextId.
    * @return the contextId
    */
    public String getContextId() {
        return contextId;
    }

    /**
     * Set the value for contextId.
     * @param contextId the contextId to set
     */
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    /**
    * Get value of launchPresentationReturnUrl.
    * @return the launchPresentationReturnUrl
    */
    public String getLaunchPresentationReturnUrl() {
        return launchPresentationReturnUrl;
    }

    /**
     * Set the value for launchPresentationReturnUrl.
     * @param launchPresentationReturnUrl the launchPresentationReturnUrl to set
     */
    public void setLaunchPresentationReturnUrl(String launchPresentationReturnUrl) {
        this.launchPresentationReturnUrl = launchPresentationReturnUrl;
    }

    /**
    * Get value of toolConsumerInstanceGuid.
    * @return the toolConsumerInstanceGuid
    */
    public String getToolConsumerInstanceGuid() {
        return toolConsumerInstanceGuid;
    }

    /**
     * Set the value for toolConsumerInstanceGuid.
     * @param toolConsumerInstanceGuid the toolConsumerInstanceGuid to set
     */
    public void setToolConsumerInstanceGuid(String toolConsumerInstanceGuid) {
        this.toolConsumerInstanceGuid = toolConsumerInstanceGuid;
    }

    /**
    * Get value of remoteAddr.
    * @return the remoteAddr
    */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * Set the value for remoteAddr.
     * @param remoteAddr the remoteAddr to set
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
    * Get value of remoteHost.
    * @return the remoteHost
    */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * Set the value for remoteHost.
     * @param remoteHost the remoteHost to set
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    /**
    * Get value of voteId.
    * @return the voteId
    */
    public Integer getVoteId() {
        return voteId;
    }

    /**
     * Set the value for voteId.
     * @param voteId the voteId to set
     */
    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    /**
    * Get value of luckyNumId.
    * @return the luckyNumId
    */
    public Integer getLuckyNumId() {
        return luckyNumId;
    }

    /**
     * Set the value for luckyNumId.
     * @param luckyNumId the luckyNumId to set
     */
    public void setLuckyNumId(Integer luckyNumId) {
        this.luckyNumId = luckyNumId;
    }

}
