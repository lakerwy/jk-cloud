package vip.xiaonuo.common.util;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;

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
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkcalendar_1_0.Client createScheduleClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcalendar_1_0.Client(config);
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

    /**
     * 根据 钉钉userid，获取钉钉用户详情
     * @param access_token 钉钉内部应用 token
     * @param userId 钉钉用户userid
     * @return
     */
    public static OapiV2UserGetResponse getUnionId(String access_token, String userId) throws Exception{
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * -获取部门列表 【调用服务端API-获取部门列表接口，入参部门字段传1，获取企业通讯录根部门下的一级子部门信息。】
     *
     * 说明：本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门
     *
     * @param access_token
     * @param deptId
     * @return
     */
    public static OapiV2DepartmentListsubResponse getListSub(String access_token, Long deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        req.setLanguage("zh_CN");
        OapiV2DepartmentListsubResponse rsp = null;
        try {
            rsp = client.execute(req, access_token);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return rsp;
    }


    /**
     * 获取子部门ID列表接口，逐级遍历获取该企业下所有部门的ID 【调用本接口，获取下一级部门基础信息。】
     *
     * 说明
     *     本接口不受授权范围限制。
     *     本接口只支持获取下一级所有部门ID列表。
     *
     * @param access_token
     * @param deptId
     * @return
     */
    public OapiV2DepartmentListsubidResponse getListSubId(String access_token, Long deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
        OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();
        req.setDeptId(deptId);
        OapiV2DepartmentListsubidResponse rsp = null;
        try {
            rsp = client.execute(req, access_token);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return rsp;
    }


    /**
     * 根据各个部门ID，分别调用服务端API-获取部门用户详情接口，分别获取每个部门下的员工详情信息
     * @param access_token token
     * @param deptId 部门Id
     * @param cursor 分页查询游标
     * @param size   分页大小
     * @return
     */
    public OapiV2UserListResponse getList(String access_token, Long deptId,Long cursor,Long size){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
        OapiV2UserListRequest req = new OapiV2UserListRequest();
        req.setDeptId(deptId);
        req.setCursor(cursor);
        req.setSize(size);
        req.setContainAccessLimit(false);
        req.setLanguage("zh_CN");
        OapiV2UserListResponse rsp = null;
        try {
            rsp = client.execute(req, access_token);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return rsp;
    }
}
