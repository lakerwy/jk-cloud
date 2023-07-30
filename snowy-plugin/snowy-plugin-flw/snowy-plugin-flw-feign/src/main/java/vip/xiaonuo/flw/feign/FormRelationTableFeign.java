package vip.xiaonuo.flw.feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author mayike
 * @date 2023/07/30 3:57
 * 业务表和流程关系Feign提供者
 */
@FeignClient(name= FeignConstant.FLW_APP, contextId = "FormRelationTableFeign")
public interface FormRelationTableFeign {

    @RequestMapping("/feign/flw/formRelationTable/save")
    void save(@RequestParam String modelId, @RequestParam String tableName, @RequestParam String businessKey, @RequestParam String processInstanceId, @RequestParam String dbName);

    @RequestMapping("/feign/flw/formRelationTable/getInfo")
    JSONObject getInfoByProcessInstanceId(@RequestParam String processInstanceId);
}
