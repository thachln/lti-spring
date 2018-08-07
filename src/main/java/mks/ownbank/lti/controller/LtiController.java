package mks.ownbank.lti.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.imsglobal.aspect.Lti;
import org.imsglobal.lti.launch.LtiLaunch;
import org.imsglobal.lti.launch.LtiSigner;
import org.imsglobal.lti.launch.LtiUser;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.imsglobal.lti2.objects.consumer.ToolConsumer;
import org.imsglobal.lti2.objects.provider.SecurityContract;
import org.imsglobal.lti2.objects.provider.ToolProfile;
import org.imsglobal.lti2.objects.provider.ToolProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mks.ownbank.db.dao.LuckyNumDao;
import mks.ownbank.db.entiy.LogLtiLauch;
import mks.ownbank.lti.JsonReader;
import mks.ownbank.lti.config.LtiProviderConfig;

/**
 *
 * @author Thach
 */
@SessionAttributes({"userid", "username", "fullname", "givenname", "familyname", "roles", "logLtiLaunch", "custom_program"})
@Controller
public class LtiController {

    private Map<String, ToolConsumerInfo> tool_consumer_profile_map = new HashMap<>();
    private SecureRandom random = new SecureRandom();
    private LtiProviderConfig ltiConfig = new LtiProviderConfig();
    private final static Logger LOG = Logger.getLogger(LtiController.class.getName());

    @Autowired
    LuckyNumDao luckyNumDao;
    
    private class ToolConsumerInfo {
        public String profileUrl;
        public String reg_key;
        public String reg_secret;

        private ToolConsumerInfo(String profileUrl, String reg_key, String reg_secret) {
            this.profileUrl = profileUrl;
            this.reg_key = reg_key;
            this.reg_secret = reg_secret;
        }
    }

    @Autowired
    LtiSigner signer;
    
    @Lti
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public String ltiVote(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        if (!result.getSuccess()) {
            LOG.info("Lti verification failed! error was: " + result.getError());
            LOG.info("   message: " + result.getMessage());
            map.put("ltiError", result.getError().toString());
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return "error";
        } else {
            String username =  result.getLtiLaunchResult().getUser().getId();
            
            LOG.info("logonId=" + username);
            LOG.info("Role=" + result.getLtiLaunchResult().getUser().getRoles());
            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> params = new HashMap<>();
            for (String param: Collections.list(request.getParameterNames())) {
                params.put(param, request.getParameter(param));
            }
            map.put("params", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params));

            map.put("launch", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getLtiLaunchResult()));
            storeSession(request, result, map);

            // Debug logon user
            String logonUser = request.getParameter("lis_person_sourcedid");
            String userId = request.getParameter("user_id");
            String contextLable = request.getParameter("context_label");
            String contextTitle = request.getParameter("context_title");
            String userRole = request.getParameter("ext_sakai_role");
            
            LOG.info("logonUser=" + logonUser + ";userRole=" + userRole);
            
            // Prepare data for form
            map.put("litLaunchData", result);
            
            return "vote";
        }
    }

    @Lti
    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public String goVote(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "vote";
    }

    @Lti
    @RequestMapping(value = "/vote-actions", params = "clickedBtn=vote", method = RequestMethod.POST)
    public String homeVote(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "vote";
    }

    @Lti
    @RequestMapping(value = "/vote-actions", params = "clickedBtn=voteHistory", method = RequestMethod.POST)
    public String voteHistory(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {

        return "vote-history";
    }

    @Lti
    @RequestMapping(value = "/vote-actions", params = "clickedBtn=voteReport", method = RequestMethod.POST)
    public String voteReport(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "vote-report";
    }
    
    @Lti
    @RequestMapping(value = "/lucky-num", method = RequestMethod.GET)
    public String getLuckyNum(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "lucky-num";
    }

    @Lti
    @RequestMapping(value = "/lucky-num-actions", params = "clickedBtn=LuckyNum", method = RequestMethod.POST)
    public String homeLuckyNum(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "lucky-num";
    }

    @Lti
    @RequestMapping(value = "/lucky-num-actions", params = "clickedBtn=LuckyNumHistory", method = RequestMethod.POST)
    public String goLuckyNumHistory(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {

        return "lucky-num-history";
    }

    @Lti
    @RequestMapping(value = "/lucky-num-actions", params = "clickedBtn=LuckyNumReport", method = RequestMethod.POST)
    public String goLuckyNumReport(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable {
        return "lucky-num-report";
    }


    /**
     * Save some information from request into the session.
     * These values are re-used in AjaxController.
     * @param request
     * @param result
     * @param map
     */
    private void storeSession(HttpServletRequest request, LtiVerificationResult result, ModelMap map) {
        LtiLaunch ltiLaunch = result.getLtiLaunchResult();
        LtiUser user = ltiLaunch.getUser();
        LogLtiLauch logLtiLaunch = new LogLtiLauch(ltiLaunch, request.getRemoteAddr(), request.getRemoteHost());
        
        // Refer the value of @SessionAttributes at begin of the controller declaration
        // userid, roles, logLitLaunch: are always existed.
        map.put("userid", user.getId());
        map.put("roles", user.getRoles());
        map.put("logLtiLaunch", logLtiLaunch);
        
        // Additional information depending on client's settings
        if (request.getParameter("lis_person_sourcedid") != null) {
            map.put("username", request.getParameter("lis_person_sourcedid"));
        } else {
            map.put("username", request.getParameter("ext_sakai_eid"));
        }
        
        if (request.getParameter("lis_person_name_full") != null) {
            map.put("fullname", request.getParameter("lis_person_name_full"));
        }
        
        if (request.getParameter("lis_person_name_given") != null) {
            map.put("givenname", request.getParameter("lis_person_name_given"));
        }
        
        if (map.containsAttribute("lis_person_name_family")) {
            map.put("familyname", request.getParameter("lis_person_name_family"));
        }
        
        if (map.containsAttribute("custom_program")) {
            map.put("custom_program", request.getParameter("custom_program"));
        }
    }

    @Lti
    @RequestMapping(value = "/lti", method = RequestMethod.POST)
    public String ltiEntry(HttpServletRequest request, LtiVerificationResult result, HttpServletResponse resp, ModelMap map) throws Throwable{
        if(!result.getSuccess()){
            LOG.info("Lti verification failed! error was: " + result.getError());
            LOG.info("   message: " + result.getMessage());
            map.put("ltiError", result.getError().toString());
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return "error";
        } else {
            map.put("name", result.getLtiLaunchResult().getUser().getId());
            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> params = new HashMap<>();
            for (String param: Collections.list(request.getParameterNames())) {
                params.put(param, request.getParameter(param));
            }
            map.put("params", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params));

            map.put("launch", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getLtiLaunchResult()));
            return "lti";
        }
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String ltiTest(@RequestParam Map params, ModelMap map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String profileUrl = (String) params.get("tc_profile_url");
        String reg_key = (String) params.get("reg_key");
        String reg_password = (String) params.get("reg_password");
        //get the tcProfile url,

        //add it to a map with a randomly generated id in order for users to retrieve it after they're returned to the view
        String randomToken = nextRandomToken();
        tool_consumer_profile_map.put(randomToken, new ToolConsumerInfo(profileUrl, reg_key, reg_password));
        map.put("tool_consumer_retrieval_token", randomToken);
        map.put("tool_proxy_registration_request", mapper.writeValueAsString(params));
        map.put("params", params);
        return "register";
    }

    @RequestMapping(value = {"/profile_retrieval"}, method = RequestMethod.GET)
    public ResponseEntity retrieveProfile(@RequestParam String token) throws IOException {
        String tc_profile_url = tool_consumer_profile_map.get(token).profileUrl;
        LOG.info("******Got ");
        if(tc_profile_url == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        //get the profile and return it.
        ToolConsumer tc = JsonReader.readJsonFromUrl(tc_profile_url, ToolConsumer.class);
        return new ResponseEntity(tc, HttpStatus.OK);
    }

    @RequestMapping(value = {"/toolRegistration"}, method = RequestMethod.POST)
    public ResponseEntity toolRegistration(@RequestBody JsonNode toolRegistrationDetails, @RequestParam String token) throws Exception {
        ToolProxy tp = new ToolProxy();
        tp.setContext(ToolProxy.CONTEXT_URL);
        tp.setTool_proxy_guid("guid");
        tp.setId("id");
        tp.setType("ToolProxy");
        tp.setTool_profile(getToolProfile());
        tp.setSecurity_contract(getSecurityContract());

        ObjectMapper mapper = new ObjectMapper();
        HttpPost request = new HttpPost(toolRegistrationDetails.get("endpoint").asText());
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(mapper.writeValueAsString(tp)));

        ToolConsumerInfo info = tool_consumer_profile_map.get(token);
        signer.sign(request, info.reg_key, info.reg_secret);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() >= 400){
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer, "utf-8");
            String theString = writer.toString();
            throw new Exception("Got error from tool consumer: " + response.getStatusLine().getStatusCode() + " - " + theString);
        }

        return new ResponseEntity("Created tool proxy", HttpStatus.OK);
    }

    public String nextRandomToken() {
        return new BigInteger(130, random).toString(32);
    }

    private ToolProfile getToolProfile(){
        ToolProfile tp = new ToolProfile();
        tp.setBase_url_choice(getBaseUrlChoices());
        tp.setResource_handler(getResourceHandler());
        return tp;
    }

    private ArrayNode getBaseUrlChoices(){
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode choices = mapper.createArrayNode();

        ObjectNode baseUrlChoice = mapper.createObjectNode();
        baseUrlChoice.put("default_base_url", ltiConfig.default_base_url);
        baseUrlChoice.put("secure_base_url", ltiConfig.secure_base_url);

        baseUrlChoice.put("selector", "DefaultSelector");

        choices.add(baseUrlChoice);
        return choices;
    }

    private ArrayNode getResourceHandler() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode resourceHandler = mapper.createArrayNode();

        ObjectNode resource = mapper.createObjectNode();

        ArrayNode messageList = mapper.createArrayNode();
        ObjectNode message = mapper.createObjectNode();
        message.put("message_type", ToolConsumer.LtiCapability.BASICLTI_LAUNCH);
        message.put("path", ltiConfig.basic_lti_launch_path);
        ArrayNode parameterList = mapper.createArrayNode();
        ObjectNode parameter = mapper.createObjectNode();
        parameter.put("name", "my_custom_course_offering_title_parameter");
        parameter.put("variable", ToolConsumer.LtiCapability.CO_TITLE);
        parameterList.add(parameter);
        message.put("parameter", parameterList);
        messageList.add(message);

        resource.put("message", messageList);
        resource.put("name", getValue("Lti Example App"));

        resourceHandler.add(resource);
        return resourceHandler;
    }

    private ObjectNode getValue(String value){
        ObjectNode valueNode = new ObjectMapper().createObjectNode();
        valueNode.put("default_value", value);
        return valueNode;
    }

    private SecurityContract getSecurityContract(){
        SecurityContract contract = new SecurityContract();
        contract.setShared_secret("secret");
        return contract;
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseEntity getConfig() {
        return new ResponseEntity(ltiConfig, HttpStatus.OK);
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResponseEntity editConfig(@RequestBody LtiProviderConfig config) {
        ltiConfig = config;
        return new ResponseEntity(ltiConfig, HttpStatus.OK);
    }


}
