package vip.xiaonuo.flw.core.provider;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.flw.feign.FormRelationTableFeign;
import vip.xiaonuo.flw.provider.FormRelationTableApiProvider;

/**
 * 业务和模型关系 提供者
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FormRelationTableFeignProvider implements FormRelationTableFeign {

    private final FormRelationTableApiProvider formRelationTableApiProvider;
    @Override
    @RequestMapping("/feign/flw/formRelationTable/save")
    public void save(@RequestParam String modelId, @RequestParam String tableName, @RequestParam String businessKey,
                     @RequestParam String processInstanceId, @RequestParam String dbName) {
        formRelationTableApiProvider.save(modelId,tableName,businessKey,processInstanceId,dbName);
    }

    @Override
    @RequestMapping("/feign/flw/formRelationTable/getInfo")
    public JSONObject getInfoByProcessInstanceId(@RequestParam String processInstanceId) {
        return formRelationTableApiProvider.getInfoByProcessInstanceId(processInstanceId);
    }

    @Override
    @RequestMapping("/feign/flw/formRelationTable/save2")
    public void save2(String modelId, String tableName, String businessKey, String processInstanceId, String dbName, String bizTableName) {
        formRelationTableApiProvider.save2(modelId,tableName,businessKey,processInstanceId,dbName,bizTableName);
    }
}
