package vip.xiaonuo.biz.modular.ezviz.service;

import cn.hutool.json.JSONObject;

public interface EzvizService {

    /**
     * 萤石token
     * @return
     */
    String getToken();

    /**
     * 萤石设备列表
     * @param pageStart
     * @param pageSize
     * @return
     */
    JSONObject deviceList(Integer pageStart,Integer pageSize);
}
