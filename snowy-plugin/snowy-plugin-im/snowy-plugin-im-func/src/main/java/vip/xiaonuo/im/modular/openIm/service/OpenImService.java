package vip.xiaonuo.im.modular.openIm.service;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.im.modular.openIm.param.LoginParam;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/22 14:36
 */
public interface OpenImService {
    void initUser();

    JSONObject autoLogin(JSONObject userJson);

    void test();
}
