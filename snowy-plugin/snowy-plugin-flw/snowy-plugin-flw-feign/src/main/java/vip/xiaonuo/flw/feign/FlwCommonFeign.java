package vip.xiaonuo.flw.feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author mayike
 * @date 2023/07/30 3:57
 * 业务表和流程关系Feign提供者
 */
@FeignClient(name= FeignConstant.FLW_APP, contextId = "FlwCommonFeign")
public interface FlwCommonFeign {

    @RequestMapping("/feign/flw/flwCommonFeign/save")
    void save(@RequestParam String modelId, @RequestParam String tableName, @RequestParam String businessKey, @RequestParam String processInstanceId, @RequestParam String dbName);

    @RequestMapping("/feign/flw/flwCommonFeign/getInfo")
    JSONObject getInfoByProcessInstanceId(@RequestParam String processInstanceId);

    @RequestMapping("/feign/flw/flwCommonFeign/save2")
    void save2(@RequestParam String modelId, @RequestParam String tableName, @RequestParam String businessKey, @RequestParam String processInstanceId, @RequestParam String dbName, @RequestParam String bizTableName);

    @RequestMapping("/feign/flw/flwCommonFeign/getSnByModelId")
    String getSnByModelId(@RequestParam String modelId);

    @RequestMapping("/feign/flw/flwCommonFeign/flwStart")
    String flwStart(@RequestBody JSONObject jsonObject);

    @RequestMapping("/feign/flw/flwCommonFeign/rollback")
    void rollback(@RequestParam String processInstanceId);
}
