package vip.xiaonuo.sys.api;

import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * SYS 对外 API
 **/
public interface SysExtApi {

    /**
     * 注册补贴 审批 通过 处理结果反馈
     * @param paramMap
     * @return
     */
    Boolean saveRegSubsidyResult(Map<String,String> paramMap);

    /**
     * 工商信息 新增|变更|注销 审批 通过 处理
     * @param object
     * @return
     */
    Boolean dealIcManage(JSONObject object);

}
