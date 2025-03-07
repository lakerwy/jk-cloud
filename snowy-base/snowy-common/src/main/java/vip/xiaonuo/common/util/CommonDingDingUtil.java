package vip.xiaonuo.common.util;

import cn.hutool.core.collection.CollUtil;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * 钉钉通用工具类
 *
 * @author yong.ye
 * @date 2023/6/14 8:28
 */
@Slf4j
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
    public static List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getListSub(String access_token, Long deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        req.setLanguage("zh_CN");
        OapiV2DepartmentListsubResponse rsp = null;
        try {
            rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (ApiException e) {
            log.error("【https://oapi.dingtalk.com/topapi/v2/department/listsub】"+ e.getErrMsg());
            return null;
        }
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
    public static OapiV2DepartmentListsubidResponse getListSubId(String access_token, Long deptId){
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
    public static OapiV2UserListResponse.PageResult getListUser(String access_token, Long deptId,Long cursor,Long size){
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
            return rsp.getResult();
        } catch (ApiException e) {
            log.error("【https://oapi.dingtalk.com/topapi/v2/user/list】"+e.getErrMsg());
            return null;
        }
    }


    /**
     * 删除钉钉用户【慎用-做好开关】
     * @param access_token
     * @param dingUserId
     * @return
     */
    public static OapiV2UserDeleteResponse delDingUser(String access_token,String dingUserId){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/delete");
            OapiV2UserDeleteRequest req = new OapiV2UserDeleteRequest();
            req.setUserid(dingUserId);
            OapiV2UserDeleteResponse rsp = client.execute(req, access_token);
            return rsp;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 递归获取 钉钉组织架构 部门集合
     * @return
     */
    public static void execRecursionDept(String access_token, Long deptId,List<OapiV2DepartmentListsubResponse.DeptBaseResponse> result){
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> tempSubDept = getListSub(access_token, deptId);
        if(CollUtil.isNotEmpty(tempSubDept)){
            result.addAll(tempSubDept);
            tempSubDept.forEach(dept->{
                execRecursionDept(access_token,dept.getDeptId(),result);
            });
        }
    }


    /**
     * 递归获取，钉钉部门用户信息 集合
     * @return
     */
    public static void execRecursionDeptUser(String access_token, Long deptId, Long cursor, Long size, List<OapiV2UserListResponse.ListUserResponse> result){
        OapiV2UserListResponse.PageResult tempDeptUser = getListUser(access_token, deptId, cursor, size);
        if(CollUtil.isNotEmpty(tempDeptUser.getList())){
            result.addAll(tempDeptUser.getList());
        }
        if(tempDeptUser.getHasMore()){
            execRecursionDeptUser(access_token,deptId,tempDeptUser.getNextCursor(),size,result);
        }
    }


    /**
     * 根据 钉钉deptId，获取钉钉部门详情
     * @param access_token 钉钉内部应用 token
     * @param deptId 钉钉部门Id
     * @return
     */
    public static OapiV2DepartmentGetResponse.DeptGetResponse getDeptById(String access_token, Long deptId){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(deptId);
            req.setLanguage("zh_CN");
            OapiV2DepartmentGetResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 根据手机号，获取钉钉用户信息（userId）
     * @param mobile
     * @return
     */
    public static OapiV2UserGetbymobileResponse.UserGetByMobileResponse getByMobile(String access_token, String mobile) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
            OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
            req.setMobile(mobile);
            OapiV2UserGetbymobileResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        }catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取考勤报表列定义
     * @param access_token 钉钉内部应用 token
     * @return
     */
    public static OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo getAttcolumns(String access_token){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getattcolumns");
            OapiAttendanceGetattcolumnsRequest req = new OapiAttendanceGetattcolumnsRequest();
            OapiAttendanceGetattcolumnsResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取考勤报表列值
     * @param access_token 钉钉内部应用 token
     * @return
     */
    public static OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo getColumnval(String access_token, String userid,
                                                                                        String column_id_list, String from_date, String to_date){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getcolumnval");
            OapiAttendanceGetcolumnvalRequest req = new OapiAttendanceGetcolumnvalRequest();
            req.setUserid(userid);
            req.setColumnIdList(column_id_list);
            req.setFromDate(StringUtils.parseDateTime(from_date));
            req.setToDate(StringUtils.parseDateTime(to_date));
            OapiAttendanceGetcolumnvalResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取报表假期数据
     * @param access_token 钉钉内部应用 token
     * @return
     */
    public static OapiAttendanceGetleavetimebynamesResponse.ColumnValListForTopVo getLeavetimebynames(String access_token, String userid,
                                                                                        String leave_names, String from_date, String to_date){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getleavetimebynames");
            OapiAttendanceGetleavetimebynamesRequest req = new OapiAttendanceGetleavetimebynamesRequest();
            req.setUserid(userid);
            req.setLeaveNames(leave_names);
            req.setFromDate(StringUtils.parseDateTime(from_date));
            req.setToDate(StringUtils.parseDateTime(to_date));
            OapiAttendanceGetleavetimebynamesResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户签到记录
     * @param access_token 钉钉内部应用 token
     * @return
     */
    public static OapiCheckinRecordGetResponse.PageResult getCheckinRecord(String access_token, String userids, Long startTime, Long endTime,
                                                                           Long cursor, Long size){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/checkin/record/get");
            OapiCheckinRecordGetRequest req = new OapiCheckinRecordGetRequest();
            req.setUseridList(userids);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setCursor(cursor);
            req.setSize(size);
            OapiCheckinRecordGetResponse rsp = client.execute(req, access_token);
            return rsp.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取打卡结果
     * @param access_token 钉钉内部应用 token
     * @return
     */
    public static List<OapiAttendanceListResponse.Recordresult> getAttendanceList(String access_token, String userid, String workDateFrom, String workDateTo,
                                                                           Long offset, Long limit){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
            OapiAttendanceListRequest req = new OapiAttendanceListRequest();
            req.setWorkDateFrom(workDateFrom);// "2020-11-11 08:00:00"
            req.setWorkDateTo(workDateTo);
            req.setUserIdList(Arrays.asList(userid));
            req.setOffset(offset);// 起始0
            req.setLimit(limit);// 最大值50
            OapiAttendanceListResponse rsp = client.execute(req, access_token);
            return rsp.getRecordresult();
        } catch (Exception e) {
            return null;
        }
    }

}
