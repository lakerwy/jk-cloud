package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * 董事长打分监听
 *
 * @author yong.ye
 * @date 2023/8/8 9:13
 */

@Slf4j
@Component
public class FlwBossScoreListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        try{
            //获取主键Id
            String id = String.valueOf(delegateTask.getVariable("taskId"));
            //获取boosScore
            String boosScore = String.valueOf(delegateTask.getVariable("boosScore"));
            //获取备注
            String boosMemo =  String.valueOf(delegateTask.getVariable("boosMemo"));
            if(StrUtil.isNotEmpty(id)){
                String sql = "UPDATE BIZ_TASK SET BOOS_SCORE = "+boosScore+",STATUS='100',BOOS_MEMO = '"+boosMemo+"' WHERE ID = '" + id + "'";
                CommonHandleUtil.handleSqlExecution(sql);
            }
            log.info("董事长打分任务更新业务表状态-----");
        }catch (Exception e){
            log.info("董事长打分任务更新业务表状态-----"+e.getMessage());
        }

    }
}
