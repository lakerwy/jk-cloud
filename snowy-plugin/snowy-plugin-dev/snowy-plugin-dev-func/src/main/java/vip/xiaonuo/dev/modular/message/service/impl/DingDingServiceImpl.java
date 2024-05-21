package vip.xiaonuo.dev.modular.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserDeleteResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.AuthThirdApi;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.pojo.RabbitMsg;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DingDingServiceImpl implements IDingDingService {

    private final DingTalkProperties dingTalkProperties;

    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private CommonCacheOperator commonCacheOperator;


    public static Executor sendExecs = Executors.newFixedThreadPool(20);

    private final String BASE_TITLE_NAME = "【流程处理通知单】";

    @Override
    public OapiGettokenResponse getToken() {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(dingTalkProperties.getAppKey());
        request.setAppsecret(dingTalkProperties.getAppSecret());
        request.setHttpMethod("GET");
        try {
            return client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OapiV2UserGetbymobileResponse getByMobile(String phone) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(phone);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送工作通知
     * @return OapiMessageCorpconversationAsyncsendV2Response
     */
    @Override
    public OapiMessageCorpconversationAsyncsendV2Response asyncsendV2(String message ,String phone) {
        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(phone);
        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
            return null;
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(result.getUserid());
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText("流程消息");
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);
        oa.setPcMessageUrl("https://yun.jianke.co");
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        String mg = message;
        mg += "\n处理时间："+ DateUtil.formatDateTime(new Date()) +"\n详情请登录建科云2.0官网(https://yun.jianke.co)处理";
        body.setContent(mg);
        oa.setBody(body);


        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
//        msg.setOa(oa);
        msg.setMsgtype("oa");
        msg.setOa(oa);
//        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent(message);
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            return client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param subject 主题
     * @param receiverIdList 人员id集合
     */
    @Override
    public void batchSend(String subject, List<String> receiverIdList) {
        if(!dingTalkProperties.getIsSend()){
            return;
        }
        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(receiverIdList);
        //手机号集合
        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            try {
                collect.forEach(cl-> asyncsendV2(subject,cl));
            }catch (Exception e){
                log.info("> > > > >发送钉钉通知失败",e);
            }

        }
    }

    @Override
    public OapiMessageCorpconversationAsyncsendV2Response asyncsendV2(JSONObject jsonObject, String phone) {
        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(phone);
        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
            return null;
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(result.getUserid());
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText("工程校审处理单");
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);
        oa.setPcMessageUrl("https://yun.jianke.co");
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        String sn = String.valueOf(jsonObject.get("sn"));
        String title = "工程校审单号为s%的处理通知单";
        body.setTitle(String.format(title,sn));
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("项目名称");
        form.setValue(String.valueOf(jsonObject.get("projectName")));
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        forms.add(form);
        body.setForm(forms);
        oa.setBody(body);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");
        msg.setOa(oa);
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            return client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OapiMessageCorpconversationAsyncsendV2Response baseAsyncSend(JSONObject jsonObject, String phone, String title) {
        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(phone);
        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
            return null;
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(result.getUserid());
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        String mg = "处理时间："+ DateUtil.formatDateTime(new Date()) +"\n详情请登录建科云2.0官网(https://yun.jianke.co)处理";
        body.setContent(mg);


        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");
        msg.setOa(buildOa(jsonObject,title));
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            return client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OapiMessageCorpconversationAsyncsendV2Request.OA buildOa(JSONObject jsonObject,String title){
        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText("流程处理通知单");
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);

        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        body.setTitle(title);

        Map taskMap = jsonObject.get("taskMap", Map.class);
        Object processDefinitionId = taskMap.get("processDefinitionId");
        //摘要
        Object initiatorProcessAbstract = taskMap.get("initiatorProcessAbstract");
        Object initiatorProcessSn = taskMap.get("initiatorProcessSn");
        Object initiatorTime = taskMap.get("initiatorTime");
        Object initiatorName = taskMap.get("initiatorName");
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("流程名称： ");

        Object o = commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId);
        form.setValue(String.valueOf(o));
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("基本信息： ");
        form.setValue(String.valueOf(initiatorProcessAbstract));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("流水号：");
        form.setValue(String.valueOf(initiatorProcessSn));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("发起于：");
        form.setValue(initiatorTime+" | "+ initiatorName);
        forms.add(form);

        Object o1 = taskMap.get("noticeType");
        if(o1.toString().equals("COMPLETE")){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("流程完成情况：");
            form.setValue("已完成");
            forms.add(form);
        }

        body.setForm(forms);
        String mg = "处理时间："+ DateUtil.formatDateTime(new Date()) +"\n详情请登录建科云2.0官网(https://yun.jianke.co)处理";
        body.setContent(mg);
        oa.setBody(body);
        //小程序地址
//        Object currentActivityId = taskMap.get("currentActivityId");
//        Object processInstanceId = taskMap.get("processInstanceId");
//        Object taskId = taskMap.get("nextTaskId");
//        System.out.println(taskId);
        String url = String.format("%s/h5/#/pages/home/index",dingTalkProperties.getH5webUrl());
//        String url = String.format("%s/h5/#/pages/flw/task/todoTask/todo-process?id=%s&processInstanceId=%s&currentActivityId=%s",dingTalkProperties.getH5webUrl(),taskId,processInstanceId,currentActivityId);
        oa.setMessageUrl(url);
//        String pcUrl = String.format("%s/flw/todoTask", commonProperties.getFrontUrl());
//        oa.setPcMessageUrl(pcUrl);

        return oa;
    }

    public void baseAsyncSendForm(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms, String phone, Map<String,Object> values) {
        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(phone);
        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
            return;
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(result.getUserid());
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText((String) values.get("title"));
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);

        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        body.setTitle((String) values.get("title"));
        body.setForm(forms);
        String mg = "处理时间："+ DateUtil.formatDateTime(new Date()) +"\n详情请登录建科云2.0官网(https://yun.jianke.co)处理";
        body.setContent(mg);
        oa.setBody(body);
        String url = String.format("%s/h5/#/pages/flw/task/todoTask/todo-process?processInstanceId=%s",dingTalkProperties.getH5webUrl(),values.get("processInstanceId"));
        //String url = String.format("%s/h5/#/pages/flw/task/myApply/detail-process?id=%s&stateCode=%s",dingTalkProperties.getH5webUrl(),values.get("taskId"),values.get("stateCode"));
//        oa.setMessageUrl("https://webapp1.jianke.co/h5/#/pages/flw/task/todoTask/todo-process?id=e6b5287b-4246-11ee-865a-0242ac110009&processInstanceId=959a37ea-4246-11ee-865a-0242ac110009&currentActivityId=xn41ae86-a393-4fdc-8eea-68808a659fc7");

        oa.setMessageUrl(url);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");
        msg.setOa(oa);
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param entries 主题
     * @param receiverIdList 人员id集合
     */
    @Override
    public void batchSend(JSONObject entries, List<String> receiverIdList) {
        if(!dingTalkProperties.getIsSend()){
            return;
        }
        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(receiverIdList);
        //手机号集合
        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            try {
                collect.forEach(cl-> asyncsendV2(entries,cl));
            }catch (Exception e){
                log.info("> > > > >发送钉钉通知失败",e);
            }

        }
    }

    @Override
    public void baseBatchSend(JSONObject entries, List<String> receiverIdList) {
        if(!dingTalkProperties.getIsSend()){
            return;
        }
        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(receiverIdList);
        //手机号集合
        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            try {
                collect.forEach(cl-> baseAsyncSend(entries,cl,BASE_TITLE_NAME));
            }catch (Exception e){
                log.info("> > > > >发送钉钉通知失败",e);
            }
        }
    }

    /**
     *
     * @param forms
     * @param userIds  用户集合
     * @param values  拓展字段
     */
    @Override
    public void customBatchSend(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms, Set<String> userIds, Map<String,Object> values) {
        if(!dingTalkProperties.getIsSend()){
            return;
        }
        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(new ArrayList<>(userIds));
        //手机号集合
        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            try {
//                collect.forEach(cl-> baseAsyncSendForm(forms,cl,"【流程处理通知单】"));
                for (String cl : collect) {
                    sendExecs.execute(()->baseAsyncSendForm(forms,cl,values));
                }
            }catch (Exception e){
                log.info("> > > > >发送钉钉通知失败",e);
            }
        }

    }

    @Override
    public void commonSend(RabbitMsg rabbitMsg) {
        if(!dingTalkProperties.getIsSend()){
            return;
        }
        AuthThirdApi authThirdApi = SpringUtil.getBean(AuthThirdApi.class);
        Set<String> userSets = rabbitMsg.getUserSets();
        userSets.forEach(userId->{
            String thirdId = authThirdApi.getThirdIdByUserId(userId,"DingDing");
            if(StrUtil.isEmpty(thirdId)){
                thirdId = getUserId(userId);
            }
            if(StrUtil.isNotEmpty(thirdId)){
                String finalThirdId = thirdId;
                sendExecs.execute(()->commonAsyncSendForm(rabbitMsg, finalThirdId));
            }
        });

//        //手机号集合
//        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
//        if(CollUtil.isNotEmpty(collect)){
//            try {
//                for (String cl : collect) {
//                    sendExecs.execute(()->commonAsyncSendForm(rabbitMsg,cl));
//                }
//            }catch (Exception e){
//                log.info("> > > > >发送钉钉通知失败",e);
//            }
//        }
    }


    public void commonAsyncSendForm(RabbitMsg rabbitMsg,String userid) {
//        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(phone);
//        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
//            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
//            return;
//        }
//        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();
//        String userid = result.getUserid();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(userid);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText(rabbitMsg.getTitle());
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);

        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        body.setTitle(rabbitMsg.getTitle());
        body.setForm(rabbitMsg.getForms());
        String mg = "处理时间："+ DateUtil.formatDateTime(new Date());
        body.setContent(mg);
        oa.setBody(body);
        if(ObjectUtil.isNotEmpty(rabbitMsg.getUrl())){
            String url = String.format("%s/h5/#/pages/flw/task/todoTask/todo-process?processInstanceId=%s",dingTalkProperties.getH5webUrl(),rabbitMsg.getUrl());
            oa.setMessageUrl(url);
        }else {
            String url = String.format("%s/h5/#/pages/home/index",dingTalkProperties.getH5webUrl());
//       String url = String.format("%s/h5/#/pages/flw/task/todoTask/todo-process?id=%s&processInstanceId=%s&currentActivityId=%s",dingTalkProperties.getH5webUrl(),taskId,processInstanceId,currentActivityId);
            oa.setMessageUrl(url);
        }
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");
        msg.setOa(oa);
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = this.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public String getUserId(String userId){
        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(CollUtil.newArrayList(userId));
        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            String account = collect.get(0);
            OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = getByMobile(account);
            if (!oapiV2UserGetbymobileResponse.isSuccess()) {
                log.error(oapiV2UserGetbymobileResponse.getErrmsg());
                return null;
            }
            OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();
            return result.getUserid();
        }
        return null;
    }

    /**
     * 根据 钉钉应用 key 获取token 并缓存
     * @param appKey
     * @param appSecret
     * @return
     */
    @Override
    public String getDingToken(String appKey,String appSecret) {
        Object existDingDingToken = commonCacheOperator.get(appKey);
        if(ObjectUtil.isNotEmpty(existDingDingToken)){
            return existDingDingToken.toString();
        }
        String dingDingToken = CommonDingDingUtil.getToken(appKey,appSecret);
        commonCacheOperator.put(appKey,dingDingToken,7200);
        return dingDingToken;
    }

    @Override
    public void sendCarMsg(String agentId, String appKey, String appSecret, JSONObject jsonObject, String title, String linkUrl, String dingUserIds) {
        //消息体
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");

        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText(title);
        head.setBgcolor("FFFB9611");

        //构建form
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("用车人： ");
        form.setValue(String.valueOf(jsonObject.get("users")));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("手机号： ");
        form.setValue(String.valueOf(jsonObject.get("userMobile")));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("目的地： ");
        form.setValue(String.valueOf(jsonObject.get("destination")));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("用车时间： ");
        form.setValue(DateUtil.format(jsonObject.getDate("predictTransportTime"), DatePattern.NORM_DATETIME_PATTERN));
        forms.add(form);

        //构建body
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
//        body.setTitle(title);
        body.setAuthor(String.valueOf(jsonObject.get("users")));
        body.setContent(title);
        body.setForm(forms);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        oa.setHead(head);
        oa.setBody(body);
        oa.setMessageUrl(linkUrl);
        msg.setOa(oa);

//        OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
//        markdown.setText(title);
//        msg.setMarkdown(markdown);

        //发送钉钉通知
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.parseLong(agentId));
        request.setUseridList(dingUserIds);
        request.setToAllUser(false);
        request.setMsg(msg);

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        String accessToken = this.getDingToken(appKey,appSecret);
        try {
            client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除钉钉用户
     * @param userId - 建科云系统用户Id
     */
    @Override
    public void delDingUser(String userId) {
        //是否删除钉钉用户，配置
        if(dingTalkProperties.getIsDelDingUser()) {
            // 1. 根据建科云userId，获取钉钉userId
            AuthThirdApi authThirdApi = SpringUtil.getBean(AuthThirdApi.class);
            String thirdId = authThirdApi.getThirdIdByUserId(userId,"DingDing");
            log.info("【删除钉钉用户】三方用户钉钉用户Id，thirdId = "+ thirdId);
            // 2. 若三方用户表无记录
            if(StrUtil.isEmpty(thirdId)){
                thirdId = getUserId(userId);
                log.info("【删除钉钉用户】根据系统用户Id，获取账号Account（手机号），再获取钉钉用户Id，thirdId = "+ thirdId);
            }
            // 3. 钉钉用户id（钉钉平台-userId，删除钉钉用户）
            if(ObjectUtil.isNotEmpty(thirdId)){
                //获取token
                String access_token = getDingToken(dingTalkProperties.getAppKey(),dingTalkProperties.getAppSecret());
                OapiV2UserDeleteResponse oapiV2UserDeleteResponse = CommonDingDingUtil.delDingUser(access_token, thirdId);
                log.info("【删除钉钉用户】调用钉钉接口，返回结果："+ JSONUtil.parseObj(oapiV2UserDeleteResponse));
            }
        }

    }
}
