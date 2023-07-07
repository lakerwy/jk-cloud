package vip.xiaonuo.common.util;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;

/**
 * 钉钉通用工具类
 *
 * @author yong.ye
 * @date 2023/6/14 8:28
 */
public class CommonDingDingUtil {

    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    /**
     * 新版SDK
     * 根据钉钉应用配置，获取企业内部应用的Token
     * @param appKey 钉钉应用的唯一标识key
     * @param appSecret 应用的密钥。AppKey和AppSecret可在钉钉开发者后台的应用详情页面获取。
     * @return
     */
    public static String getAccessToken(String appKey,String appSecret) {
        try {
            //创建连接
            com.aliyun.dingtalkoauth2_1_0.Client client = createClient();
            com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest()
                    .setAppKey(appKey)
                    .setAppSecret(appSecret);
            //获取返回结果
            GetAccessTokenResponse response = client.getAccessToken(getAccessTokenRequest);
            return response.body.accessToken;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 旧版SDK
     * 根据钉钉应用配置，获取企业内部应用的Token
     * @param appKey 钉钉应用的唯一标识key
     * @param appSecret 应用的密钥。AppKey和AppSecret可在钉钉开发者后台的应用详情页面获取。
     * @return
     */
    public static String getToken(String appKey,String appSecret){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(appKey);
            req.setAppsecret(appSecret);
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            return rsp.getAccessToken();
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 根据钉钉Token，免登码code，获取钉钉用户信息
     * @param access_token 钉钉内部应用 token
     * @param code 免登码 code
     */
    public static OapiV2UserGetuserinfoResponse.UserGetByCodeResponse getUserInfo(String access_token, String code){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getuserinfo");
            OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
            req.setCode(code);
            OapiV2UserGetuserinfoResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据 钉钉userid，获取钉钉用户详情
     * @param access_token 钉钉内部应用 token
     * @param userId 钉钉用户userid
     * @return
     */
    public static OapiV2UserGetResponse.UserGetResponse getUserById(String access_token, String userId){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }


}
