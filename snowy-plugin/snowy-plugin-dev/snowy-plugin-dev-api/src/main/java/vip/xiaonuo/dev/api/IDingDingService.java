package vip.xiaonuo.dev.api;

import cn.hutool.json.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import vip.xiaonuo.common.pojo.RabbitMsg;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDingDingService {
    /**
     * 钉钉获取企业内部access_token
     * @return OapiGettokenResponse
     */
    OapiGettokenResponse getToken();

    /**
     * 发送工作通知
     * @return OapiMessageCorpconversationAsyncsendV2Response
     */
    OapiMessageCorpconversationAsyncsendV2Response asyncsendV2(String message ,String phone);

    /**
     * 根据手机号获取userid
     * @param phone 手机号
     * @return OapiV2UserGetbymobileResponse
     */
    OapiV2UserGetbymobileResponse getByMobile(String phone);

    void batchSend(String subject, List<String> receiverIdList);

    /**
     * 发送工作通知
     * @return OapiMessageCorpconversationAsyncsendV2Response
     */
    OapiMessageCorpconversationAsyncsendV2Response asyncsendV2(JSONObject message , String phone);

    OapiMessageCorpconversationAsyncsendV2Response baseAsyncSend(JSONObject jsonObject, String phone, String title);

    void batchSend(JSONObject entries, List<String> receiverIdList);

    void baseBatchSend(JSONObject entries,List<String> receiverIdList);

    void customBatchSend(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms, Set<String> userIds, Map<String,Object> values);

    void commonSend(RabbitMsg rabbitMsg);

    /**
     * 根据 钉钉应用 key 获取token 并缓存
     * @param appKey
     * @param appSecret
     * @return
     */
    String getDingToken(String appKey,String appSecret);

    /**
     * 发送 科科用车 、 科科司机 钉钉OA通知
     * @param agentId 钉钉应用的AgentId
     * @param appKey 钉钉应用的唯一标识key
     * @param appSecret 应用的密钥
     * @param jsonObject 信息体参数对象
     * @param title 消息主题
     * @param linkUrl 跳转链接
     * @param dingUserIds 钉钉用户id
     */
    void sendCarMsg(String agentId, String appKey, String appSecret,JSONObject jsonObject, String title, String linkUrl, String dingUserIds );


    /**
     * 根据 建科云 userId，删除钉钉用户
     * @param userId
     */
    void delDingUser(String userId);

}
