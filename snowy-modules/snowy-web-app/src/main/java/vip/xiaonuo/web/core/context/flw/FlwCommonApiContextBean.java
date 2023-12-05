package vip.xiaonuo.web.core.context.flw;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.flw.feign.FlwCommonFeign;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlwCommonApiContextBean implements FlwCommonApi {
    private final FlwCommonFeign flwCommonFeign;
    @Override
    public void save(String modelId, String tableName, String businessKey, String processInstanceId, String dbName) {
        flwCommonFeign.save(modelId,tableName,businessKey,processInstanceId,dbName);
    }

    @Override
    public JSONObject getInfoByProcessInstanceId(String processInstanceId) {
        return flwCommonFeign.getInfoByProcessInstanceId(processInstanceId);
    }

    @Override
    public void save2(String modelId, String tableName, String businessKey, String processInstanceId, String dbName, String bizTableName) {
        flwCommonFeign.save2(modelId,tableName,businessKey,processInstanceId,dbName,bizTableName);
    }

    @Override
    public String getSnByModelId(String modelId) {
        return flwCommonFeign.getSnByModelId(modelId);
    }

    @Override
    public String flwStart(JSONObject jsonObject) {
        return flwCommonFeign.flwStart(jsonObject);
    }

    @Override
    public void rollback(String processInstanceId) {
        flwCommonFeign.rollback(processInstanceId);
    }
}
