package vip.xiaonuo.sys.api;

import cn.hutool.json.JSONObject;

import java.util.List;

/**
 * 组织API
 *
 * @author xuyuxiang
 * @date 2022/6/6 11:35
 **/
public interface PartnerInfoApi {

    /**
     * 根据分公司区域获取合作伙伴
     * @param region
     * @return
     */
    List<JSONObject> getByRegion(String region);
}
