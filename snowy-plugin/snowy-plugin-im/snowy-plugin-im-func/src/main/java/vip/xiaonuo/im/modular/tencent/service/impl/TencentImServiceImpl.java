package vip.xiaonuo.im.modular.tencent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tencentyun.TLSSigAPIv2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.im.modular.tencent.client.WebServerClient;
import vip.xiaonuo.im.modular.tencent.config.TencentImProperties;
import vip.xiaonuo.im.modular.tencent.constant.ResultConstant;
import vip.xiaonuo.im.modular.tencent.constant.TencentCloudImApiConstant;
import vip.xiaonuo.im.modular.tencent.constant.TencentCloudImConstant;
import vip.xiaonuo.im.modular.tencent.param.AccountParam;
import vip.xiaonuo.im.modular.tencent.param.FriendAddParam;
import vip.xiaonuo.im.modular.tencent.param.GroupCreateParam;
import vip.xiaonuo.im.modular.tencent.service.TencentImService;
import vip.xiaonuo.util.modular.initData.entity.InitData;
import vip.xiaonuo.util.modular.initData.service.InitDataService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.pojo.CommonResult.CODE_ERROR;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/13 20:02
 */
@Slf4j
@Component
public class TencentImServiceImpl implements TencentImService {
    private static final String REDIS_IM_USER_SIG = "silence:im_user_sig:";

    @Resource
    TencentImProperties tencentImProperties;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private WebServerClient webServerClient;

    @Resource
    private InitDataService initDataService;

    /**
     * 获取腾讯云用户签名
     */
    public String getTxCloudUserSig() {
        return createUserSignByUserId(tencentImProperties.getAppManager());
    }

    @Override
    public String createUserSignByUserId(String userId) {
        Object userSig = commonCacheOperator.get(REDIS_IM_USER_SIG + userId);
        if (ObjectUtil.isEmpty(userSig)) {
            TLSSigAPIv2 tlsSigApi = new TLSSigAPIv2(tencentImProperties.getSdkAppId(), tencentImProperties.getSecretKey());
            userSig = tlsSigApi.genUserSig(userId, tencentImProperties.getExpireTime());
            commonCacheOperator.put(REDIS_IM_USER_SIG + userId, userSig, 86400L);
        }
        return String.valueOf(userSig);
    }

    /**
     * 初始化所有用户的userSig
     */
    @Override
    public void initUserSign() {
        try {
            boolean tencnetImUser = initDataService.getInitStatusByType("TENCNET_IM_USER");
            if(tencnetImUser){
                return;
            }
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("parentId","1666651352106315777");
            String org = webServerClient.getOrgByMap(mapParam);
            JSONObject jsonObject = JSONUtil.parseObj(org);
            String data = jsonObject.getStr("data");
            JSONObject sysOrg = JSONUtil.parseObj(data);
            String childrenUser = webServerClient.getChildrenUser(sysOrg.getStr("id"));
            JSONObject userJson = JSONUtil.parseObj(childrenUser);
            JSONArray jsonArray = userJson.getJSONArray("data");
            List<AccountParam> accountParams = new ArrayList<>();
            AccountParam accountParam ;
            for (Object object : jsonArray) {
                JSONObject user = (JSONObject) object;
                String userId = user.getStr("account");
                //初始化文件
                accountParam = new AccountParam();
                accountParam.setUserID(userId);
                String name = user.getStr("name");
                accountParam.setNick(name);
                Object o = user.get("extJson");
                if(ObjectUtil.isNotEmpty(o)){
                    JSONObject extJson = JSONUtil.parseObj(o);
                    String avatarUrl = extJson.getStr("avatarUrl");
                    if(StrUtil.isNotEmpty(avatarUrl)){
                        accountParam.setFaceUrl(avatarUrl);
                    }
                }else {
                    accountParam.setFaceUrl("");
                }

                accountParams.add(accountParam);
            }
            if (CollUtil.isNotEmpty(accountParams)) {
                int totalSize = accountParams.size();
                for (int i = 0; i < totalSize; i += 90) {
                    int end = Math.min(totalSize, i + 90);
                    List<AccountParam> batch = accountParams.subList(i, end);
                    multiAccountImport(batch);
                }
            }
            InitData initData = new InitData();
            initData.setInitStatus(true);
            initData.setType("TENCNET_IM_USER");
            initData.setRemark("腾讯IM的用户初始化");
            initDataService.saveOrUpdate(initData,new LambdaQueryWrapper<InitData>().eq(InitData::getType,initData.getType()));
        }catch (Exception e){
            log.error("初始化IM 用户失败{}", e);
        }
    }

    /**
     * 获取腾讯im请求路径
     */
    private String getHttpsUrl(String imServiceApi) {
        return String.format(TencentCloudImApiConstant.PARAMS_TEM,
                TencentCloudImApiConstant.BASE_URL, imServiceApi, tencentImProperties.getSdkAppId(), tencentImProperties.getAppManager(), this.getTxCloudUserSig(), RandomUtils.nextInt(0, 999999999));
    }

    /**
     * 导入单个账号
     *
     * @param accountParam 用户
     */
    @Override
    public CommonResult<String> accountImport(AccountParam accountParam) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.AccountManage.ACCOUNT_IMPORT);
        JSONObject jsonObject = BeanUtil.toBean(accountParam, JSONObject.class);
        jsonObject.set("identifier", tencentImProperties.getAppManager());
//        jsonObject.set("UserID", accountParam.getUserID());
//        if (StrUtil.isNotEmpty(accountParam.getNick())) {
//            jsonObject.set("Nick", accountParam.getNick());
//        }
//        if (StrUtil.isNotEmpty(accountParam.getFaceUrl())) {
//            jsonObject.set("FaceUrl", accountParam.getFaceUrl());
//        }
        log.info("腾讯云im导入单个账号，请求参数：{}", jsonObject);
        log.info("请求的url：{}", httpsUrl);
        String result = HttpUtil.post(httpsUrl, jsonObject.toString());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.ok();
        }
        log.info("腾讯云im导入单个账号，返回结果：{}", result);
        return CommonResult.error(String.valueOf(resultJson.get(ResultConstant.ErrorInfo)));

    }

    /**
     * 导入多个账号
     *
     * @param accountParams 用户id集合
     */
    @Override
    public CommonResult<Object> multiAccountImport(List<AccountParam> accountParams) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.AccountManage.MULTI_ACCOUNT_IMPORT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("AccountList", JSONUtil.parseArray(accountParams));
        log.info("腾讯云im导入多个账号，请求参数：{}", jsonObject);
        String result = HttpUtil.post(httpsUrl, jsonObject.toStringPretty());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.ok();
        }
        log.info("腾讯云im导入单个账号，返回结果：{}", result);
        JSONArray failAccounts = resultJson.getJSONArray("FailAccounts");
        return new CommonResult<>(CODE_ERROR, "导入失败", failAccounts);

    }

    /**
     * 删除账号
     *
     * @param accountParams 用户id集合
     */
    @Override
    public CommonResult<Object> accountDelete(List<AccountParam> accountParams) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.AccountManage.ACCOUNT_DELETE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("DeleteItem", JSONUtil.parseArray(accountParams));
        log.info("腾讯云im删除账号，请求参数：{}", jsonObject);
        String result = HttpUtil.post(httpsUrl, jsonObject.toString());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.ok();
        }
        log.info("腾讯云im删除账户，返回结果：{}", result);
        JSONArray failAccounts = resultJson.getJSONArray("ResultItem");
        return new CommonResult<>(CODE_ERROR, "删除失败", failAccounts);
    }

    /**
     * 查询账号是否已经导入im
     *
     * @param accountParams 用户id集合
     */
    @Override
    public CommonResult<Object> accountCheck(List<AccountParam> accountParams) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.AccountManage.ACCOUNT_CHECK);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("CheckItem", JSONUtil.parseArray(accountParams));
        log.info("腾讯云im查询账号，请求参数：{}", jsonObject);
        String result = HttpUtil.post(httpsUrl, jsonObject.toString());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.data(resultJson.getJSONArray("ResultItem"));
        }
        log.info("腾讯云im查询账号，返回结果：{}", result);
        return new CommonResult<>(CODE_ERROR, "删除失败", resultJson.get(ResultConstant.ErrorInfo));
    }

    @Override
    public CommonResult<String> friendAdd(FriendAddParam friendAddParam) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.RelationManage.FRIEND_ADD);
        JSONObject jsonObject = JSONUtil.parseObj(friendAddParam);
        log.info("腾讯云im添加好友，请求参数：{}", jsonObject);
        String result = HttpUtil.post(httpsUrl, jsonObject.toString());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.ok();
        }
        log.info("腾讯云im查询账号，返回结果：{}", result);
        return new CommonResult<>(CODE_ERROR, "删除失败", resultJson.get(ResultConstant.ErrorInfo).toString());
    }

    @Override
    public CommonResult<String> tencentUpdateImUser(JSONObject userInfo) {
        String userId = userInfo.getStr("account");
        //初始化文件
        AccountParam accountParam = new AccountParam();
        accountParam.setUserID(userId);
        String name = userInfo.getStr("name");
        accountParam.setNick(name);
        Object o = userInfo.get("extJson");
        if(ObjectUtil.isNotEmpty(o)){
            JSONObject extJson = JSONUtil.parseObj(o);
            String avatarUrl = extJson.getStr("avatarUrl");
            if(StrUtil.isNotEmpty(avatarUrl)){
                accountParam.setFaceUrl(avatarUrl);
            }
        }else {
            accountParam.setFaceUrl("");
        }
        return accountImport(accountParam);
    }

    @Override
    public CommonResult<String> tencentCreateRoot(AccountParam accountParam) {
//        accountParam.setUserID("@RBT#001");
//        accountParam.setNick("科科");
//        accountParam.setFaceUrl("https://minio.hbjkjt.com/comm/jk.png");
        return accountImport(accountParam);
    }

    @Override
    public CommonResult<Object> createGroup(GroupCreateParam groupCreateParam) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.GroupManage.CREATE_GROUP);
        JSONObject jsonObject = JSONUtil.parseObj(groupCreateParam);
        log.info("腾讯云im创建群组，请求参数：{}", jsonObject);
        String result = HttpUtil.post(httpsUrl, jsonObject.toString());
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer errorCode = resultJson.getInt(ResultConstant.ErrorCode);
        log.info("腾讯云im创建群组，返回结果：{}", result);
        if (ResultConstant.IS_OK.equals(errorCode)) {
            return CommonResult.ok();
        }
        return new CommonResult<>(CODE_ERROR, "创建群组失败", resultJson.get(ResultConstant.ErrorInfo).toString());
    }

    private List<JSONObject> getUserIdJsonList(List<String> userIds) {
        return userIds.stream().map(v -> {
            JSONObject userIdJson = new JSONObject();
            userIdJson.set("UserID", v);
            return userIdJson;
        }).collect(Collectors.toList());
    }

    /**
     * 单发单聊消息
     *
     * @param syncOtherMachine 是否同步消息到发送方（1-同步，2-不同步）
     * @param fromUserId       发送方用户id
     * @param toUserId         接收方用户id
     * @param msgType          消息对象类型
     * @param msgContent       消息内容
     */
    public String sendMsg(Integer syncOtherMachine, String fromUserId, String toUserId, String msgType, String msgContent) {
        Integer random = RandomUtils.nextInt(0, 999999999);
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.SingleChatManage.SEND_MSG);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("SyncOtherMachine", syncOtherMachine);
        if (StrUtil.isNotEmpty(fromUserId)) {
            // 发送方不为空表示指定发送用户，为空表示为管理员发送消息
            jsonObject.set("From_Account", fromUserId);
        }
        jsonObject.set("To_Account", toUserId);
        jsonObject.set("MsgRandom", random);
        List<JSONObject> msgBody = getMsgBody(msgType, msgContent);
        jsonObject.set("MsgBody", msgBody);
        log.info("腾讯云im单发单聊消息，请求参数：{}", jsonObject.toString());
        String result = HttpUtil.post(httpsUrl, jsonObject);
        log.info("腾讯云im单发单聊消息，返回结果：{}", result);
        return result;
    }

    /**
     * 批量发单聊消息
     *
     * @param syncOtherMachine 是否同步消息到发送方（1-同步，2-不同步）
     * @param fromUserId       发送方用户id
     * @param toUserIds        接收方用户id集合
     * @param msgType          消息对象类型
     * @param msgContent       消息内容
     */
    public String batchSendMsg(Integer syncOtherMachine, String fromUserId, List<String> toUserIds, String msgType, String msgContent) {
        Integer random = RandomUtils.nextInt(0, 999999999);
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.SingleChatManage.BATCH_SEND_MSG);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("SyncOtherMachine", syncOtherMachine);
        if (StrUtil.isNotEmpty(fromUserId)) {
            // 发送方不为空表示指定发送用户，为空表示为管理员发送消息
            jsonObject.set("From_Account", fromUserId);
        }
        jsonObject.set("To_Account", toUserIds);
        jsonObject.set("MsgRandom", random);
        List<JSONObject> msgBody = getMsgBody(msgType, msgContent);
        jsonObject.set("MsgBody", msgBody);
        log.info("腾讯云im批量发单聊消息，请求参数：{}", jsonObject.toString());
        String result = HttpUtil.post(httpsUrl, jsonObject);
        log.info("腾讯云im批量发单聊消息，返回结果：{}", result);
        return result;
    }


    /**
     * 拼接发送消息内容
     *
     * @param msgType    消息类型
     * @param msgContent 发送消息内容
     * @return 消息内容
     */
    private List<JSONObject> getMsgBody(String msgType, String msgContent) {
        List<JSONObject> msgBody = new ArrayList<>();
        if (msgType.equals(TencentCloudImConstant.TIM_TEXT_ELEM)) {
            // 文本类型
            JSONObject msgBodyJson = new JSONObject();
            msgBodyJson.set("MsgType", msgType);
            JSONObject msgContentObj = new JSONObject();
            msgContentObj.set("Text", msgContent);
            msgBodyJson.set("MsgContent", msgContentObj);
            msgBody.add(msgBodyJson);
        }
        return msgBody;
    }

    /**
     * 查询单聊消息
     *
     * @param fromUserId 发送方用户id
     * @param toUserId   接收方用户id
     * @param maxCnt     查询条数
     * @param startTime  起始时间（单位：秒）
     * @param endTime    结束时间（单位：秒）
     * @param lastMsgKey 最后一条消息的 MsgKey
     * @return 单聊消息列表
     */
    public String adminGetRoamMsg(String fromUserId, String toUserId, Integer maxCnt, Long startTime, Long endTime, String lastMsgKey) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.SingleChatManage.ADMIN_GET_ROAM_MSG);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("From_Account", fromUserId);
        jsonObject.set("To_Account", toUserId);
        jsonObject.set("MaxCnt", maxCnt);
        jsonObject.set("MinTime", startTime);
        jsonObject.set("MaxTime", endTime);
        if (StrUtil.isNotEmpty(lastMsgKey)) {
            jsonObject.set("LastMsgKey", lastMsgKey);
        }
        log.info("腾讯云im查询单聊消息，请求参数：{}", jsonObject.toString());
        String result = HttpUtil.post(httpsUrl, jsonObject);
        log.info("腾讯云im查询单聊消息，返回结果：{}", result);
        return result;
    }

    /**
     * 撤回单聊消息
     *
     * @param fromUserId 发送方用户id
     * @param toUserId   接收方用户id
     * @param msgKey     MsgKey
     */
    public void adminMsgWithDraw(String fromUserId, String toUserId, String msgKey) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.SingleChatManage.ADMIN_MSG_WITH_DRAW);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("From_Account", fromUserId);
        jsonObject.set("To_Account", toUserId);
        jsonObject.set("MsgKey", msgKey);
        log.info("腾讯云im撤回单聊消息，请求参数：{}", jsonObject.toString());
        String result = HttpUtil.post(httpsUrl, jsonObject);
        log.info("腾讯云im撤回单聊消息，返回结果：{}", result);
    }

    /**
     * 设置单聊消息已读
     *
     * @param reportUserId 读取消息的用户
     * @param peerUserId   发送消息的用户
     */
    public void adminSetMsgRead(String reportUserId, String peerUserId) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.SingleChatManage.ADMIN_SET_MSG_READ);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
        jsonObject.set("Report_Account", reportUserId);
        jsonObject.set("Peer_Account", peerUserId);
        log.info("腾讯云im设置单聊消息已读，请求参数：{}", jsonObject.toString());
        String result = HttpUtil.post(httpsUrl, jsonObject);
        log.info("腾讯云im设置单聊消息已读，返回结果：{}", result);
    }
}
