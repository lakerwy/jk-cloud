package vip.xiaonuo.sys.modular.im.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import com.tencentyun.TLSSigAPIv2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.im.config.TencentImProperties;
import vip.xiaonuo.sys.modular.im.constant.ResultConstant;
import vip.xiaonuo.sys.modular.im.constant.TencentCloudImApiConstant;
import vip.xiaonuo.sys.modular.im.constant.TencentCloudImConstant;
import vip.xiaonuo.sys.modular.im.param.AccountParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.pojo.CommonResult.CODE_ERROR;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/13 20:02
 */
@Slf4j
@Component
public class TencentCloudImService {
    private static final String REDIS_IM_USER_SIG = "silence:im_user_sig:";

    @Resource
    TencentImProperties tencentImProperties;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    /**
     * 获取腾讯云用户签名
     */
    public String getTxCloudUserSig() {
        return createUserSignByUserId(tencentImProperties.getAppManager());
    }

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
     * 获取腾讯im请求路径
     */
    private String getHttpsUrl(String imServiceApi) {
        return String.format("%s%s?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json",
                tencentImProperties.getPrefixUrl(), imServiceApi, tencentImProperties.getSdkAppId(), tencentImProperties.getAppManager(), this.getTxCloudUserSig(), RandomUtils.nextInt(0, 999999999));
    }

    /**
     * 导入单个账号
     *
     * @param accountParam 用户
     */
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
    public CommonResult<Object> multiAccountImport(List<AccountParam> accountParams) {
        String httpsUrl = getHttpsUrl(TencentCloudImApiConstant.AccountManage.MULTI_ACCOUNT_IMPORT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("identifier", tencentImProperties.getAppManager());
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
