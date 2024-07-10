package vip.xiaonuo.im.modular.chat.provider;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.im.api.ImAppApi;
import vip.xiaonuo.im.modular.chat.pojo.LoginResponse;
import vip.xiaonuo.im.modular.chat.service.ImService;
import vip.xiaonuo.im.modular.tencent.service.TencentImService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/6 11:30
 */
@Service
public class ImAppApiProvider implements ImAppApi {

    @Resource
    ImService imService;

    @Resource
    TencentImService tencentImService;

    @Override
    public JSONObject imLogin(JSONObject jsonObject) {
        LoginResponse loginResponse = imService.onLoginSuccess(jsonObject);
        return JSONUtil.parseObj(loginResponse);
    }

    @Override
    public String tencentImLogin(String userId) {
        return tencentImService.createUserSignByUserId(userId);
    }
}
