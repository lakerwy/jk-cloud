package vip.xiaonuo.im.modular.chat.service;


import cn.hutool.json.JSONObject;
import cn.wildfirechat.pojos.InputOutputUserInfo;
import cn.wildfirechat.pojos.OutputGetUserList;
import cn.wildfirechat.sdk.model.IMResult;
import vip.xiaonuo.im.modular.chat.pojo.LoginResponse;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/6 9:27
 */
public interface ImService {

    LoginResponse onLoginSuccess(JSONObject jsonObject);

    void syncUserToIm(List<JSONObject> jsonList);

    OutputGetUserList getAllUsers(int count, int offset);
}
