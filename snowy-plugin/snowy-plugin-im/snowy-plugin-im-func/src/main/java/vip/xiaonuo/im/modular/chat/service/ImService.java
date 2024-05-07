package vip.xiaonuo.im.modular.chat.service;


import cn.hutool.json.JSONObject;
import vip.xiaonuo.im.modular.chat.pojo.LoginResponse;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/6 9:27
 */
public interface ImService {

    LoginResponse onLoginSuccess(JSONObject jsonObject);
}
