package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 监听事件处理 [总部人员离职]
 */
@Slf4j
@Component
public class FlwTurnPositiveTaskListener implements ExecutionListener {

    @Resource
    FormRelationTableService formRelationTableService;
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try{
            String processInstanceId = execution.getProcessInstanceId();
            FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
            //异动类型
            String moveType = String.valueOf(execution.getVariable("moveType"));
            if(ObjectUtil.isNotEmpty(moveType)) {
                String sql = "SELECT FILE_ID FROM " + info.getTableName() + " WHERE ID = '" + info.getId() + "'";
                Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
                if (ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("FILE_ID"))) {
                    String fileId = String.valueOf(map.get("FILE_ID"));
                    if ("0".equals(moveType)) {
                        //转正【不启用】
//                        if(ObjectUtil.isNotEmpty(info)){
//                            sql = "UPDATE PERSONNEL SET `EMPLOY_NATURE` = '正式' where ID = "+ fileId;
//                            NodeRuntimeUtil.handleSqlExecution(sql);
//                        }
                    } else if ("2".equals(moveType)) {
                        //离职
                        //将用户设置为 禁用
                        if (ObjectUtil.isNotEmpty(info)) {
                            sql = "UPDATE PERSONNEL SET `STATUS` = '1' where ID = '" + fileId + "'";
                            NodeRuntimeUtil.handleSqlExecution(sql);
                            sql = "SELECT PHONE FROM PERSONNEL WHERE ID = '" + fileId + "'";
                            map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
                            if (ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("PHONE"))) {
                                sql = "UPDATE SYS_USER SET USER_STATUS = 'DISABLED' WHERE `ACCOUNT` = '" + map.get("PHONE") + "'";
                                NodeRuntimeUtil.handleSqlExecution(sql);
                            }
                        }
                    } else {
                        //调岗
                        //设置用户部门个职位到新的部门和职位中去
//                        if (ObjectUtil.isNotEmpty(info)) {
//                            Object variable = execution.getVariable("nowDeptId");
//                            Object nowDutyId = execution.getVariable("nowDutyId");
//                            if (ObjectUtil.isAllNotEmpty(variable, nowDutyId)) {
//                                sql = "SELECT PHONE FROM PERSONNEL WHERE ID = " + fileId;
//                                map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
//                                if (ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("PHONE"))) {
//                                    //更新系统中部门
//                                    sql = "UPDATE SYS_USER SET `ORG_ID` = '" + variable + "' ,POSITION_ID = '" + nowDutyId + "' WHERE `ACCOUNT` = '" + map.get("PHONE") + "'";
//                                    NodeRuntimeUtil.handleSqlExecution(sql);
//                                }
//                                //跟新档案表中部门和岗位
//                                sql = "UPDATE PERSONNEL SET `DEPT_ID` = '" + variable + "' ,`DUTY_ID` = '" + nowDutyId + "' where ID = '" + fileId + "'";
//                                log.debug(sql);
//                                NodeRuntimeUtil.handleSqlExecution(sql);
//                            }
//                        }
                    }
                }
            }
            log.info("人员异动操作-----");
        }catch (Exception e){
            log.info("人员异动操作-----"+e.getMessage());
        }
    }
}
