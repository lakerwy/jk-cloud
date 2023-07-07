package vip.xiaonuo.dev.modular.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageMassSendRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.dev.modular.message.service.IDingDingService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DingDingServiceImpl implements IDingDingService {

    private final DingTalkProperties dingTalkProperties;

    @Resource
    private SysUserApi sysUserApi;

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
        mg += "\n请登录建科云2.0官网处理";
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
        if(!dingTalkProperties.isSend()){
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
}
