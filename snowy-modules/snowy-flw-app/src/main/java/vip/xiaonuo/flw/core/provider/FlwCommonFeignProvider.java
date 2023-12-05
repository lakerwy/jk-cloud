package vip.xiaonuo.flw.core.provider;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.flw.feign.FlwCommonFeign;
import vip.xiaonuo.flw.provider.FlwCommonApiProvider;

/**
 * 业务和模型关系 提供者
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FlwCommonFeignProvider implements FlwCommonFeign {

    private final FlwCommonApiProvider flwCommonApiProvider;
    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/save")
    public void save(@RequestParam String modelId, @RequestParam String tableName, @RequestParam String businessKey,
                     @RequestParam String processInstanceId, @RequestParam String dbName) {
        flwCommonApiProvider.save(modelId,tableName,businessKey,processInstanceId,dbName);
    }

    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/getInfo")
    public JSONObject getInfoByProcessInstanceId(@RequestParam String processInstanceId) {
        return flwCommonApiProvider.getInfoByProcessInstanceId(processInstanceId);
    }

    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/save2")
    public void save2(String modelId, String tableName, String businessKey, String processInstanceId, String dbName, String bizTableName) {
        flwCommonApiProvider.save2(modelId,tableName,businessKey,processInstanceId,dbName,bizTableName);
    }

    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/getSnByModelId")
    public String getSnByModelId(String modelId) {
        return flwCommonApiProvider.getSnByModelId(modelId);
    }

    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/flwStart")
    public String flwStart(@RequestBody JSONObject jsonObject) {
        return flwCommonApiProvider.flwStart(jsonObject);
    }

    @Override
    @RequestMapping("/feign/flw/flwCommonFeign/rollback")
    public void rollback(String processInstanceId) {
        flwCommonApiProvider.rollback(processInstanceId);
    }
}
