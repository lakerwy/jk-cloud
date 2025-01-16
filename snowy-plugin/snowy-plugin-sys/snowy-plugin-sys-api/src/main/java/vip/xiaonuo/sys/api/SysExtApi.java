package vip.xiaonuo.sys.api;

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
    Boolean saveRegSubsidyResult(Map<String,Object> paramMap);
}
