package vip.xiaonuo.flw.feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author pzh
 * @date 2024/12/30 15:30
 * 流程监控Feign提供者
 */
@FeignClient(name= FeignConstant.FLW_APP, contextId = "FlwProcessMonitorFeign")
public interface FlwProcessMonitorFeign {

    /**
     * 流程详情
     *
     * @author pzh
     * @date 2024/12/30 15:30
     **/
    @RequestMapping("/feign/flw/process/monitor/detail")
    JSONObject monitorDetail(@RequestParam(value = "processInstanceId") String processInstanceId);
}
