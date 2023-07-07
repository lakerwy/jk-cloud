package vip.xiaonuo.dev.modular.message.service;

import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;

import java.util.List;

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

}
