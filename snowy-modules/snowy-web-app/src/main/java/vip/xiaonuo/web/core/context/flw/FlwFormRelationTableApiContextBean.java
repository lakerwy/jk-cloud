package vip.xiaonuo.web.core.context.flw;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.flw.feign.FormRelationTableFeign;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlwFormRelationTableApiContextBean implements FormRelationTableApi {
    private final FormRelationTableFeign formRelationTableFeign;
    @Override
    public void save(String modelId, String tableName, String businessKey, String processInstanceId, String dbName) {
        formRelationTableFeign.save(modelId,tableName,businessKey,processInstanceId,dbName);
    }

    @Override
    public JSONObject getInfoByProcessInstanceId(String processInstanceId) {
        return formRelationTableFeign.getInfoByProcessInstanceId(processInstanceId);
    }

    @Override
    public void save2(String modelId, String tableName, String businessKey, String processInstanceId, String dbName, String bizTableName) {
        formRelationTableFeign.save2(modelId,tableName,businessKey,processInstanceId,dbName,bizTableName);
    }

    @Override
    public String getSnByModelId(String modelId) {
        return formRelationTableFeign.getSnByModelId(modelId);
    }
}
