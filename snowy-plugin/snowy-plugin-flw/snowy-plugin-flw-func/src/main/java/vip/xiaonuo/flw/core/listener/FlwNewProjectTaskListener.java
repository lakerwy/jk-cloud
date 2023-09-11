package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.project.service.BizProjectService;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.entity.BizProjectRegister;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.BizProjectRegisterService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class FlwNewProjectTaskListener implements ExecutionListener {

    @Resource
    FormRelationTableService formRelationTableService;

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        //获取流程实例关联的业务信息
        FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
        if(ObjectUtil.isNotEmpty(info)){
            //根据businessKey 从project中获取APPROVAL_NO 单号 更新立项登记的立项情况 BIZ_PROJECT_REGISTER 的 is_Approval
            String sql = "SELECT INFO_NO FROM "+ info.getTableName() + " WHERE ID = " + info.getId();
            Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
            if(ObjectUtil.isNotNull(map)){
                String infoNo = String.valueOf(map.get("INFO_NO"));
                if(StrUtil.isNotBlank(infoNo)){
                    String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
                   // sql = "update BIZ_PROJECT_REGISTER set is_Approval = \"已立项\" where SN = '"+bizProject.getInfoNo()+"'";
                    String flName = "`"+dbName+"`.`BIZ_PROJECT_REGISTER`";
                    sql = "update " + flName + " set `is_Approval` = \"已立项\"  where SN = '" + infoNo +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                    log.info("立项回填操作-----sn"+infoNo);
                }
            }
        }
    }
}
