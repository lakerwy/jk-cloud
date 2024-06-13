package vip.xiaonuo.im.modular.chat.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.*;
import cn.wildfirechat.sdk.model.IMResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonAvatarUtil;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dev.api.DevFileApi;
import vip.xiaonuo.im.core.ImProperties;
import vip.xiaonuo.im.modular.chat.pojo.LoginResponse;
import vip.xiaonuo.im.modular.chat.service.ImService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/6 9:53
 */
@Service
@Slf4j
public class ImServiceImpl implements ImService {

    @Resource
    ShortUUIDGenerator userNameGenerator;

    @Resource
    ImProperties imProperties;

    @Resource
    DevFileApi devFileApi;

    @PostConstruct
    private void init() {
        AdminConfig.initAdmin(imProperties.admin_url, imProperties.admin_secret);
        if(StrUtil.isEmpty(imProperties.admin_user_id)) {
            imProperties.admin_user_id = "admin";
        }
    }

    @Override
    public LoginResponse onLoginSuccess(JSONObject jsonObject) {
        if(!imProperties.isEnable()){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setEnable(false);
            return loginResponse;
        }
        try {
            String mobile = jsonObject.getStr("mobile");
            String clientId = jsonObject.getStr("clientId");
            int platform = jsonObject.getInt("platform");
            String displayName = jsonObject.getStr("displayName");
            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByMobile(mobile);

            //如果用户信息不存在，创建用户
            InputOutputUserInfo user;
            boolean isNewUser = false;
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                log.info("User not exist, try to create");
                //获取用户名。如果用的是shortUUID生成器，是有极小概率会重复的，所以需要去检查是否已经存在相同的userName。
                //ShortUUIDGenerator内的main函数有测试代码，可以观察一下碰撞的概率，这个重复是理论上的，作者测试了几千万次次都没有产生碰撞。
                //另外由于并发的问题，也有同时生成相同的id并同时去检查的并同时通过的情况，但这种情况概率极低，可以忽略不计。
                String userName;
                int tryCount = 0;
                do {
                    tryCount++;
                    userName = userNameGenerator.getUserName(mobile);
                    if (tryCount > 10) {
                        throw  new CommonException();
                    }
                } while (!isUsernameAvailable(userName));


                user = new InputOutputUserInfo();
                user.setName(userName);
                if (imProperties.isUse_random_name()) {
                    if(StrUtil.isEmpty(displayName)){
                        displayName = "用户" + (int) (Math.random() * 10000);
                    }
                    user.setDisplayName(displayName);
                } else {
                    user.setDisplayName(mobile);
                }
                user.setMobile(mobile);
                //图片编码
                String str = jsonObject.getStr("avatar");
                if(StrUtil.isNotEmpty(str)){
                    MultipartFile multipartFile = null;
                    try {
                        byte[] decode = Base64.decode(str);
                        File tempFile = FileUtil.createTempFile();
                        FileUtil.writeBytes(decode,tempFile);
                        multipartFile = new MockMultipartFile(tempFile.getName(), tempFile.getName(), "image/jpeg", decode);
                    }catch (Exception e){
                        log.error(e.getMessage());
                    }
                    String url = devFileApi.storageFileWithReturnUrlLocal(multipartFile);
                    user.setPortrait(url);
                }
                IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(user);
                if (userIdResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    user.setUserId(userIdResult.getResult().getUserId());
                    isNewUser = true;
                } else {
                    log.info("Create user failure {}", userIdResult.code);
                    throw new CommonException();
                }


            } else if (userResult.getCode() != 0) {
                log.error("Get user failure {}", userResult.code);
                throw new CommonException();
            } else {
                user = userResult.getResult();
            }

            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(user.getUserId(), clientId, platform);
            if (tokenResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                log.error("Get user token failure {}", tokenResult.code);
                throw new CommonException();
            }

            LoginResponse response = new LoginResponse();
            response.setUserId(user.getUserId());
            response.setImToken(tokenResult.getResult().getToken());
            response.setRegister(isNewUser);
            response.setPortrait(user.getPortrait());
            response.setUserName(user.getName());
            response.setEnable(true);

            if (isNewUser) {
                if (!StrUtil.isEmpty(imProperties.getWelcome_for_new_user())) {
                    sendTextMessage(imProperties.admin_user_id, user.getUserId(), imProperties.getWelcome_for_new_user());
                }

                if (imProperties.isNew_user_robot_friend() && !StrUtil.isEmpty(imProperties.getRobot_friend_id())) {
                    RelationAdmin.setUserFriend(user.getUserId(), imProperties.getRobot_friend_id(), true, null);
                }
                if (!StrUtil.isEmpty(imProperties.getRobot_welcome())) {
                    sendTextMessage(imProperties.getRobot_friend_id(), user.getUserId(), imProperties.getRobot_welcome());
                }

                if (!StrUtil.isEmpty(imProperties.getNew_user_subscribe_channel_id())) {
                    try {
                        GeneralAdmin.subscribeChannel(imProperties.getNew_user_subscribe_channel_id(), user.getUserId());
                    } catch (Exception ignored) {

                    }
                }
            } else {
                if (!StrUtil.isEmpty(imProperties.getWelcome_for_back_user())) {
                    sendTextMessage(imProperties.admin_user_id, user.getUserId(), imProperties.getWelcome_for_back_user());
                }
                if (!StrUtil.isEmpty(imProperties.getRobot_welcome())) {
                    sendTextMessage(imProperties.getRobot_friend_id(), user.getUserId(), imProperties.getRobot_welcome());
                }
                if (!StrUtil.isEmpty(imProperties.getBack_user_subscribe_channel_id())) {
                    try {
                        IMResult<OutputBooleanValue> booleanValueIMResult = GeneralAdmin.isUserSubscribedChannel(user.getUserId(), imProperties.getBack_user_subscribe_channel_id());
                        if (booleanValueIMResult != null && booleanValueIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS && !booleanValueIMResult.getResult().value) {
                            GeneralAdmin.subscribeChannel(imProperties.getBack_user_subscribe_channel_id(), user.getUserId());
                        }
                    } catch (Exception ignored) {

                    }
                }
            }

            if(!StrUtil.isEmpty(imProperties.getPrompt_text())) {
                sendTextMessage(imProperties.admin_user_id, user.getUserId(), imProperties.getPrompt_text());
            }

            if(!StrUtil.isEmpty(imProperties.getImage_msg_url()) && !StrUtil.isEmpty(imProperties.getImage_msg_base64_thumbnail())) {
                sendImageMessage(imProperties.admin_user_id, user.getUserId(), imProperties.getImage_msg_url(), imProperties.getImage_msg_base64_thumbnail());
            }

            log.info("login with session success, userId {}, clientId {}, platform {}, adminUrl {}", user.getUserId(), clientId, platform, imProperties.admin_url);
            return response;

        }catch (Exception e){
            e.printStackTrace();
            log.error("Exception happens", e);
            throw new CommonException();
        }
    }

    private boolean isUsernameAvailable(String username) {
        try {
            IMResult<InputOutputUserInfo> existUser = UserAdmin.getUserByName(username);
            if (existUser.code == ErrorCode.ERROR_CODE_NOT_EXIST.code) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendTextMessage(String fromUser, String toUser, String text) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(text);

        sendMessage(fromUser, conversation, payload);
    }

    private void sendMessage(String fromUser, Conversation conversation, MessagePayload payload) {
        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(fromUser, conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                log.info("send message success");
            } else {
                log.error("send message error {}", resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("send message error {}", e.getLocalizedMessage());
        }
    }

    private void sendImageMessage(String fromUser, String toUser, String url, String base64Thumbnail) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(3);
        payload.setRemoteMediaUrl(url);
        payload.setBase64edData(base64Thumbnail);
        payload.setMediaType(1);
        payload.setSearchableContent("[图片]");

        sendMessage(fromUser, conversation, payload);
    }

    /**
     * 暂时只新增用户，删除和修改用户没有处理
     * @param jsonList
     */
    @Override
    public void syncUserToIm(List<JSONObject> jsonList) {
        jsonList.forEach(jsonObject -> {
            try {
                String mobile = jsonObject.getStr("account");
                String displayName = jsonObject.getStr("name");
                IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByMobile(mobile);

                //如果用户信息不存在，创建用户
                InputOutputUserInfo user;
                if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                    log.info("User not exist, try to create");
                    //获取用户名。如果用的是shortUUID生成器，是有极小概率会重复的，所以需要去检查是否已经存在相同的userName。
                    //ShortUUIDGenerator内的main函数有测试代码，可以观察一下碰撞的概率，这个重复是理论上的，作者测试了几千万次次都没有产生碰撞。
                    //另外由于并发的问题，也有同时生成相同的id并同时去检查的并同时通过的情况，但这种情况概率极低，可以忽略不计。
                    String userName;
                    int tryCount = 0;
                    do {
                        tryCount++;
                        userName = userNameGenerator.getUserName(mobile);
                        if (tryCount > 10) {
                            throw new CommonException();
                        }
                    } while (!isUsernameAvailable(userName));


                    user = new InputOutputUserInfo();
                    user.setName(userName);
                    if (imProperties.isUse_random_name()) {
                        if (StrUtil.isEmpty(displayName)) {
                            displayName = "用户" + (int) (Math.random() * 10000);
                        }
                        user.setDisplayName(displayName);
                    } else {
                        user.setDisplayName(mobile);
                    }
                    user.setMobile(mobile);
                    //图片编码
                    String str = jsonObject.getStr("avatar");
                    if (StrUtil.isEmpty(str)) {
                        str = CommonAvatarUtil.generateImg(displayName);
                    }
                    MultipartFile multipartFile = null;
                    try {
                        String imageData = str.substring(str.indexOf(",") + 1);
                        byte[] decode = Base64.decode(imageData);
                        File tempFile = FileUtil.createTempFile("avatar",".jpeg",true);
                        FileUtil.writeBytes(decode, tempFile);
                        multipartFile = new MockMultipartFile(tempFile.getName(), tempFile.getName(), "image/jpeg", decode);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    String url = devFileApi.storageFileWithReturnUrlMinio(multipartFile);
//                    String backendUrl = commonProperties.getBackendUrl();
                    user.setPortrait(url);
                    IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(user);
                    if (userIdResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                        user.setUserId(userIdResult.getResult().getUserId());
                        handleSqlExecution(user.getUserId(),jsonObject.getStr("id"));
                    } else {
                        log.info("Create user failure {}", userIdResult.code);
                       // throw new CommonException();
                    }
                }else {
                    handleSqlExecution(userResult.getResult().getUserId(),jsonObject.getStr("id"));
                }
            }catch (Exception e){
                log.error("sync user fail----"+ e.getMessage());
            }

        });
    }

    @Override
    public OutputGetUserList getAllUsers(int count, int offset) {
        try {
            IMResult<OutputGetUserList> allUsers = UserAdmin.getAllUsers(offset, count);
            if(allUsers.code == ErrorCode.ERROR_CODE_SUCCESS.code){
                return allUsers.getResult();
            }
        }catch (Exception e){
            throw new CommonException("获取IM 用户失败");
        }

        return null;
    }


    public static void handleSqlExecution(String imUserId, String userId) {
        if (StrUtil.isBlank(imUserId) || StrUtil.isBlank(userId)) {
            return;
        }

        String sql = "UPDATE SYS_USER SET EXT_JSON = JSON_SET(IFNULL(EXT_JSON, '{}'), '$.imUserId', ?) WHERE ID = ?";

        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        try (Connection conn = dbsApi.getCurrentDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, imUserId);
            stmt.setString(2, userId);

            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new CommonException("Failed to execute SQL: " + e.getMessage());
        }
    }

    @Override
    public PojoGroupInfos getPojoGroupInfosByUserId(String userId){
        try {
            IMResult<PojoGroupInfos> userGroupInfosResult = GroupAdmin.getUserGroupInfos(userId);
            if(userGroupInfosResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS){
                return userGroupInfosResult.getResult();
            }
        }catch (Exception e){
            throw new CommonException("获取该用户的所有接口.");
        }
        return null;
    }

}
