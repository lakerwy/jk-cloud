//package vip.xiaonuo.flw.core.listener.recruit;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.camunda.bpm.engine.delegate.DelegateTask;
//import org.camunda.bpm.engine.delegate.TaskListener;
//import org.springframework.stereotype.Component;
//import vip.xiaonuo.flw.core.util.CommonHandleUtil;
//
///**
// * @author yong.ye
// * @date 2023/8/30 9:43
// */
//
//@Slf4j
//@Component
//public class FlwResumeFirstPassListener implements TaskListener {
//    @Override
//    public void notify(DelegateTask delegateTask) {
//        try{
//            //获取主键Id
//            String id = String.valueOf(delegateTask.getVariable("taskId"));
//            if(StrUtil.isNotEmpty(id)){
//                //初试通过 - 初试通过
//                String sql = "UPDATE BIZ_RECRUIT_RESUME SET STATUS='4' WHERE ID = '" + id + "'";
//                CommonHandleUtil.handleSqlExecution(sql);
//            }
//            log.info("招聘-初试-通过【初试通过 4】-更新简历状态-----");
//        }catch (Exception e){
//            log.info("招聘-初试-通过【初试通过 4】-更新简历状态-----"+e.getMessage());
//        }
//    }
//}
