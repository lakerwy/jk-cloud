package vip.xiaonuo.flw.core.listener.sub;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**  申请补贴流程通过后监听，修改状态 */
@Slf4j
@Component
public class ApplyRegCertDataFlowListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateTask) throws Exception{
        String processInstanceId = delegateTask.getProcessInstanceId();
        try{
            String id = String.valueOf(delegateTask.getVariable("id"));
            /** 更新状态申请状态 */
            String sql1 = "UPDATE APPLY_REG_CERT_DATA_FLOW SET  STATUS = '1' WHERE PROCESS_INSTANCE_ID = '" + processInstanceId + "'";
            handleSqlExecution(sql1);
            if(StringUtils.isEmpty(id)){
                log.error("ApplyRegCertDataFlowListener:无法获取ID - processInstanceId："+processInstanceId);
            }
            if(StringUtils.isNotEmpty(id)){
                String sql2 = "UPDATE REG_CERT_STATUS_DATA SET  IS_PASS = '1' WHERE APPLY_REG_FLW_ID = '" + id + "'" +" and DELETE_FLAG = 'NOT_DELETE'";
                handleSqlExecution(sql2);
            }
        }catch (Exception e){
            log.error("【申请补贴流程】"+ e.getMessage());
        }
    }

    private void handleSqlExecution(String sql) {
        if(ObjectUtil.isNotEmpty(sql)) {
            Connection conn;
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            try {
                dbsApi.changeDataSource("master");
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                conn.setAutoCommit(false);
                StrUtil.split(sql.trim(), ";").forEach(sqlItem -> {
                    if(ObjectUtil.isNotEmpty(sqlItem)) {
                        try {
                            SqlExecutor.execute(conn, sqlItem);
                        } catch (SQLException e) {
                            dbsApi.changeDataSource("master");
                            throw new CommonException(e.getMessage());
                        }
                    }
                });
                conn.commit();
            } catch (SQLException sqlException) {
                dbsApi.changeDataSource("master");
                sqlException.printStackTrace();
            } finally {
                DbUtil.close(conn);
                dbsApi.changeDataSource("master");
            }
        }
    }
}
