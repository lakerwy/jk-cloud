package vip.xiaonuo.flw.api;

import cn.hutool.json.JSONObject;

/**
 * 业务表和流程模型关系Api
 *
 * @author mayike
 * @date 2023/7/30 3:33
 **/
public interface FormRelationTableApi {
    /**
     * 新增
     * @param modelId
     * @param tableName
     * @param businessKey
     * @param processInstanceId
     * @param dbName
     */
    void save(String modelId,String tableName,String businessKey,String processInstanceId,String dbName);

    /**
     * 根据流程实例获取
     * @param processInstanceId
     * @return
     */
    JSONObject getInfoByProcessInstanceId(String processInstanceId);
}
