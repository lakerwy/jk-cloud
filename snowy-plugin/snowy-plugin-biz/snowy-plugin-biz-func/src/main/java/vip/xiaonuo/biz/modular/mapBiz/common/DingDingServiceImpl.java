package vip.xiaonuo.biz.modular.mapBiz.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dbs.core.util.CommonUtils;
import vip.xiaonuo.biz.modular.mapBiz.common.config.DingTalkProperties;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.CacheConstant;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
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
//        oa.setPcMessageUrl("https://yun.jianke.co");
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        //String sn = String.valueOf(jsonObject.get("sn"));
        body.setTitle("【工程设计校审】");
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("基本信息： ");
        String professionValue = jsonObject.getStr("profession");
        String reader = jsonObject.getStr("reader");
        form.setValue(jsonObject.getStr("projectName")+" | " + jsonObject.getStr("designStage") + " | " +jsonObject.getStr("companyOrDepartment") + " | "+ commonCacheOperator.get(CommonUtils.MAJOR_TYPE + professionValue) + " | " +commonCacheOperator.get(CommonUtils.MAJOR_TYPE_USER+reader));
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("流水号：");
        form.setValue(jsonObject.getStr("sn"));
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("发起于：");
        form.setValue(DateUtil.format(jsonObject.getDate("applyTime"), DatePattern.NORM_DATETIME_PATTERN) +","+ jsonObject.getStr("applyUser"));
        forms.add(form);

        body.setForm(forms);
        String mg = "处理时间："+ DateUtil.formatDateTime(new Date()) +"\n详情请登录建科云2.0官网(https://yun.jianke.co)处理";
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

    @Override
    public OapiMessageCorpconversationAsyncsendV2Response baseAsyncSend(JSONObject jsonObject, String phone,String title) {
        return null;
    }

    /**
     *
     * @param entries 主题
     * @param receiverIdList 人员id集合
     */
    @Override
    public void batchSend(JSONObject entries, List<String> receiverIdList) {
//        if(!dingTalkProperties.isSend()){
//            return;
//        }
//        List<JSONObject> users = sysUserApi.getUserListByIdListWithoutException(receiverIdList);
        Set<String> collect = new HashSet<>();
        for (String s : receiverIdList) {
            String account = String.valueOf(commonCacheOperator.get(CacheConstant.USER_INFO + s));
            collect.add(account);
        }

        //手机号集合
//        List<String> collect = users.stream().map(u -> u.getStr("account")).collect(Collectors.toList());
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

    }

    @Override
    public void customBatchSend(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms, Set<String> userIds, Map<String,Object> values) {

    }

    @Override
    public String getDingToken(String appKey, String appSecret) {
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
        form.setKey("手机号： ");
        form.setValue(String.valueOf(jsonObject.get("userMobile")));
        forms.add(form);
        form.setKey("目的地： ");
        form.setValue(String.valueOf(jsonObject.get("destination")));
        forms.add(form);
        form.setKey("用车时间： ");
        form.setValue(String.valueOf(jsonObject.get("predictTransportTime")));
        forms.add(form);

        //构建body
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        body.setTitle(title);
        body.setAuthor(String.valueOf(jsonObject.get("users")));
        body.setContent(title);
        body.setForm(forms);

        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        oa.setHead(head);
        oa.setBody(body);
        oa.setMessageUrl(linkUrl);
        msg.setOa(oa);

        OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
        markdown.setText(title);
        msg.setMarkdown(markdown);

        //发送钉钉通知
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.getLong(agentId));
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
}
