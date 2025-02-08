package vip.xiaonuo.biz.modular.cmb.service;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.biz.modular.cmb.param.*;

/**
 * 招商银行API
 * @author pzh
 * @date 2024/02/06 10:30
 */
public interface CmbApiService {

    /**
     * @param param 请求参数
     */
    JSONObject sendRequest(CmbApiParam param);

    void callback(String receiveEncryptMsg);
}
