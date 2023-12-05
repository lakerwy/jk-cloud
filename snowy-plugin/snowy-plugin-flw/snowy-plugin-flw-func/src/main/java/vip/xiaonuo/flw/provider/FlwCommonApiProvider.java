package vip.xiaonuo.flw.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.flw.modular.model.service.FlwModelService;
import vip.xiaonuo.flw.modular.process.param.FlwProcessStartParam;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: vip.xiaonuo.flw.provider
 * @NAME: FormRelationTableApiProvider
 * @USER: jack
 * @DATE: 7/30/2023
 * @PROJECT_NAME: jk-cloud
 **/
@Service
public class FlwCommonApiProvider implements FlwCommonApi {
    @Resource
    FormRelationTableService formRelationTableService;

    @Resource
    FlwProcessService flwProcessService;

    @Resource
    FlwModelService flwModelService;

    @Override
    public void save(String modelId, String tableName, String businessKey, String processInstanceId, String dbName) {
        formRelationTableService.save(modelId,tableName,businessKey,processInstanceId,dbName);
    }

    @Override
    public JSONObject getInfoByProcessInstanceId(String processInstanceId) {
        return JSONUtil.parseObj(formRelationTableService.getInfoByProcessInstanceId(processInstanceId));
    }

    @Override
    public void save2(String modelId, String tableName, String businessKey, String processInstanceId, String dbName, String bizTableName) {
        formRelationTableService.save2(modelId,tableName,businessKey,processInstanceId,dbName,bizTableName);
    }

    @Override
    public String getSnByModelId(String modelId) {
        return flwProcessService.getSn(modelId);
    }

    @Override
    public String flwStart(JSONObject jsonObject) throws CommonException {
        FlwProcessStartParam flwProcessStartParam = BeanUtil.toBean(jsonObject, FlwProcessStartParam.class);
        return flwProcessService.start(flwProcessStartParam);
    }

    @Override
    public void rollback(String processInstanceId) {
        flwModelService.rollback(processInstanceId);
    }
}
