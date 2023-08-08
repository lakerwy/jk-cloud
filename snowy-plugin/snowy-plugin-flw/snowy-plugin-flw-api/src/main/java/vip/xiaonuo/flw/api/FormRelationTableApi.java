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

    /**
     * 新增(针对流程中，1个流程多条业务记录情况，有一张流程申请单表，一张业务数据表（外键是申请单Id）)
     * @param modelId
     * @param tableName  流程申请单表-用于流程
     * @param businessKey
     * @param processInstanceId
     * @param dbName
     * @param bizTableName  业务数据表-实际业务数据
     */
    void save2(String modelId,String tableName,String businessKey,String processInstanceId,String dbName,String bizTableName);
}
