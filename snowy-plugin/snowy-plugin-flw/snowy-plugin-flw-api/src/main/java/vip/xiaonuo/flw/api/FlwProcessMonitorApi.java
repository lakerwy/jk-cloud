package vip.xiaonuo.flw.api;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.List;

/**
 * 业务表和流程模型关系Api
 *
 * @author mayike
 * @date 2023/7/30 3:33
 **/
public interface FlwProcessMonitorApi {
    JSONObject monitorDetail(String processInstanceId);
}
