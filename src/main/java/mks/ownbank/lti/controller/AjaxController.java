package mks.ownbank.lti.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imsglobal.aspect.Lti;
import org.imsglobal.lti.launch.LtiSigner;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;

import mks.ownbank.db.dao.LuckyNumDao;
import mks.ownbank.db.dao.PeriodVoteDao;
import mks.ownbank.db.entiy.LogLtiLauch;
import mks.ownbank.db.entiy.LuckyNum;
import mks.ownbank.db.entiy.PeriodVote;
import mks.ownbank.lti.controller.model.VoteDataModel;

/**
 *
 * @author Thach
 */
@SessionAttributes({"userid", "username", "fullname", "givenname", "familyname", "roles", "logLtiLaunch", "custom_program"})
@RestController
public class AjaxController {

    private final static Logger LOG = Logger.getLogger(AjaxController.class.getName());

    @Autowired
    LtiSigner signer;
    
    @Autowired
    LuckyNumDao luckyNumDao;
    
    @Autowired
    PeriodVoteDao periodVoteDao;
    
    /**
     * Lưu kết quả bỏ HỤI.
     * @param voteValue
     * @param request
     * @param result
     * @param resp
     * @param map
     * @return
     * @throws Throwable
     */
    @Lti
    @RequestMapping(value = "/savePeriodVote", method = RequestMethod.POST)
    public String savePeriodVote(@ModelAttribute("model") PeriodVote periodVote, HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        LOG.info("PeriodVote saving...");
        // Get user and client information from session
        String logonUser = (String) map.get("userid");
        LogLtiLauch logLtiLaunch = (LogLtiLauch) map.get("logLtiLaunch");

        LOG.info("logonUser=" + logonUser + ";voteNum=" + periodVote.getVoteValue() + ";logLtiLaunch=" + logLtiLaunch);

        periodVote.setId(null); // For save newly
        
        String customProgram = map.containsKey("custom_program") ? (String) map.get("custom_program") : "OwnBank_MKS";
        periodVote.setEventId(customProgram);
        periodVote.setVoted(new Date());
        periodVote.setUserid(logonUser);
        periodVote.setLtiLaunch(logLtiLaunch);
        
        // Get data from session
        periodVote.setUsername((String) map.get("username"));
        periodVote.setGivenname((String) map.get("givenname"));
        periodVote.setFullname((String) map.get("fullname"));
        periodVote.setFamilyname((String) map.get("familyname"));
        
        periodVoteDao.save(periodVote);
        
        Map<String, String> resultStatus = new HashMap<>();
        resultStatus.put("status", "OK");
        
        return new Gson().toJson(resultStatus);
    }

    
    /**
     * [Give the description for method].
     * @param luckyNum
     * @param request
     * @param result
     * @param resp
     * @param map
     * @return
     * @throws Throwable
     */
    @Lti
    @RequestMapping(value = "/save-luckynum", method = RequestMethod.POST)
    public String draftSaveAnswer(@RequestParam("luckyNum") Integer luckyNum, HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        LOG.info("Draft saving...");
        // Get user and client information from session
        String logonUser = (String) map.get("userid");
        LogLtiLauch logLtiLaunch = (LogLtiLauch) map.get("logLtiLaunch");

        LOG.info("logonUser=" + logonUser + ";luckyNum=" + luckyNum + ";logLtiLaunch=" + logLtiLaunch);
        
        LuckyNum vote = new LuckyNum();
        vote.setId(null); // For save newly
        
        String customProgram = map.containsKey("custom_program") ? (String) map.get("custom_program") : "OwnBank_MKS";
        vote.setEventId(customProgram);
        vote.setGot(new Date());
        vote.setLuckyNum(luckyNum);
        vote.setUserid(logonUser);
        vote.setLtiLaunch(logLtiLaunch);
        
        // Get data from session
        vote.setUsername((String) map.get("username"));
        vote.setGivenname((String) map.get("givenname"));
        vote.setFullname((String) map.get("fullname"));
        vote.setFamilyname((String) map.get("familyname"));
        
        luckyNumDao.save(vote);
        
        Map<String, String> resultStatus = new HashMap<>();
        resultStatus.put("status", "OK");
        
        return new Gson().toJson(resultStatus);
    }

    @Lti
    @RequestMapping(value = "/load-history-votes", method = RequestMethod.GET)
    public String loadHistoryVotes(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        String userId = (String) map.get("userid");
        
        LOG.info("loadHistoryVotes...userId=" + userId);
        List<PeriodVote> listVotes = periodVoteDao.findVoteByteUserId(userId);
        
        VoteDataModel dataModel = new VoteDataModel(listVotes);

        return new Gson().toJson(dataModel);
    }

    @Lti
    @RequestMapping(value = "/load-history-lucky-num", method = RequestMethod.GET)
    public String loadHistoryLuckyNum(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        String userId = (String) map.get("userid");
        
        LOG.info("loadHistoryVotes...userId=" + userId);
        List<LuckyNum> listVotes = luckyNumDao.findVoteByteUserId(userId);
        
        VoteDataModel dataModel = new VoteDataModel(listVotes);

        return new Gson().toJson(dataModel);
    }

    @Lti
    @RequestMapping(value = "/load-all-history-lucky-num", method = RequestMethod.GET)
    public String loadAllHistoryLuckyNum(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        String userId = (String) map.get("userid");
        
        LOG.info("loadHistoryVotes...userId=" + userId);
        List<LuckyNum> listVotes = luckyNumDao.list();
        
        VoteDataModel dataModel = new VoteDataModel(listVotes);

        return new Gson().toJson(dataModel);
    }

    @Lti
    @RequestMapping(value = "/load-all-history-votes", method = RequestMethod.GET)
    public String loadAllHistoryVotes(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        String userId = (String) map.get("userid");
        
        LOG.info("loadHistoryVotes...userId=" + userId);
        List<PeriodVote> listVotes = periodVoteDao.list();
        
        VoteDataModel dataModel = new VoteDataModel(listVotes);

        return new Gson().toJson(dataModel);
    }
}
