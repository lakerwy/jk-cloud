package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 转正后监听事件处理
 */
@Slf4j
@Component
public class FlwTurnPositiveTaskListener implements TaskListener {
    @Resource
    FormRelationTableService formRelationTableService;
    @Override
    public void notify(DelegateTask delegateTask) {
        try {
            String processInstanceId = delegateTask.getProcessInstanceId();
            FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
            //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
            String moveType = String.valueOf(delegateTask.getVariable("moveType"));
            if(ObjectUtil.isNotEmpty(moveType)){
                String sql = "SELECT FILE_ID FROM "+ info.getTableName() + " WHERE ID = " + info.getId();
                Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
                if(ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("FILE_ID"))){
                    String fileId = String.valueOf(map.get("FILE_ID"));
                    if("0".equals(moveType)){
                        //转正
                        if(ObjectUtil.isNotEmpty(info)){
                            //SELECT * FROM PERSONNEL WHERE ID = (SELECT FILE_ID FROM PERSONNEL_FILE_MOVES WHERE ID = )
                            sql = "UPDATE PERSONNEL SET `EMPLOY_NATURE` = '正式' where ID = "+ fileId;
                            NodeRuntimeUtil.handleSqlExecution(sql);
                        }
                    }else if ("2".equals(moveType)){
                        //离职
                        //将用户设置为
                        if(ObjectUtil.isNotEmpty(info)){
                            //String sql = "update " + info.getTableName() + " set `EMPLOY_NATURE` = '离职' where ID = " + info.getId();
                            sql = "UPDATE PERSONNEL SET `EMPLOY_NATURE` = '离职' where ID = " + fileId;;
                            NodeRuntimeUtil.handleSqlExecution(sql);
                            sql = "SELECT PHONE FROM PERSONNEL WHERE ID = " + fileId;
                            map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
                            if(ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("PHONE"))){
                                sql = "UPDATE SYS_USER SET DELETE_FLAG = 'DELETE' WHERE `ACCOUNT` = '" + map.get("PHONE")+"'";
                                NodeRuntimeUtil.handleSqlExecution(sql);
                            }
                            //删除账号 ，如果查询的为空的话，会删除所有账号  有风险
//                        sql = "UPDATE SYS_USER SET `DELETE_FLAG` = 'DELETE'  WHERE ACCOUNT  =  (SELECT a.PHONE FROM PERSONNEL a JOIN PERSONNEL_FILE_MOVES b ON a.ID = b.FILE_ID AND b.ID =  " +info.getId() + ")";
//                        NodeRuntimeUtil.handleSqlExecution(sql);
                        }
                    }else{
                        //调岗
                        //设置用户部门个职位到新的部门和职位中去
                        if(ObjectUtil.isNotEmpty(info)){
                            Object variable = delegateTask.getVariable("nowDeptId");
                            if(ObjectUtil.isNotEmpty(variable)){
                                sql = "SELECT PHONE FROM PERSONNEL WHERE ID = " + fileId;
                                map = CommonHandleUtil.handleSqlQuery(sql, Boolean.TRUE);
                                if(ObjectUtil.isNotEmpty(map) && ObjectUtil.isNotEmpty(map.get("PHONE"))){
                                    //更新系统中部门
                                    sql = "UPDATE SYS_USER SET `ORG_ID` = " + variable +" WHERE `ACCOUNT` = '" + map.get("PHONE")+"'";
                                    NodeRuntimeUtil.handleSqlExecution(sql);
                                }
                                //跟新档案表中部门和岗位
                                sql = "UPDATE PERSONNEL SET `DEPT_ID` = " +variable + " where ID = '" + fileId + "'";
                                log.debug(sql);
                                NodeRuntimeUtil.handleSqlExecution(sql);
                            }
                        }
                    }
                }
            }


            log.info("转正后更新用工性质-----");
        }catch (Exception e) {
            log.info("转正后更新用工性质异常-----" + e.getMessage());
        }
    }
}
