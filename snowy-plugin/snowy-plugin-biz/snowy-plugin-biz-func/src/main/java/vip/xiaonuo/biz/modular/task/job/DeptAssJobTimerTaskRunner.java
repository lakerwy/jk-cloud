package vip.xiaonuo.biz.modular.task.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.service.BizTaskService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yong.ye
 * @date 2023/8/10 11:21
 */

@Slf4j
@Component
public class DeptAssJobTimerTaskRunner {
    @Resource
    private BizTaskService bizTaskService;

//    @Resource
//    private DevMessageApi devMessageApi;

//    /* 0 0 9 * * ?  */
//    @Scheduled(cron = "0/30 * * * * ? ")
    public void action() {
        try {
            // 本定时任务用于出来 部门绩效考核 - 部门任务分 - 进度分 - 进度率 自动计算
            bizTaskService.autoUpdateProgressRate();
            log.info("部门绩效考核-进度分系数-自动计算......");
        }catch (Exception e)
        {
            log.info("部门绩效考核-进度分系数-自动计算......"+e.getMessage());
        }
    }

//    @Scheduled(cron = "0 0 11 1/1 * ? ")
//    public void sendMsg(){
//        try {
//            // 动态站内信息
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String currentDateStr = sdf.format(new Date());
//            List<BizTask> bizTaskList = bizTaskService.list(new QueryWrapper<BizTask>().lambda().eq(BizTask::getStatus,"1")
//                            .eq(BizTask::getFinishStatus,null)
//                            .apply("date(END_DATE) = {0}", currentDateStr));
//            for (BizTask item:bizTaskList) {
//                List<String> receiverIdList = bizTaskList.stream().map(x->x.getOwerId()).collect(Collectors.toList());
//                String content = "年度任务："+item.getTaskName()+",完成时间："+item.getEndDate().toString()+"请及时填写完成进度，避免影响系统自动计算。";
//                devMessageApi.sendMessageWithContent(receiverIdList,"BIZ","【提醒】年度任务完成进度填写",content);
//            }
//            log.info("部门绩效考核-完成进度填写提醒......");
//        }catch (Exception e)
//        {
//            log.info("部门绩效考核-完成进度填写提醒......"+e.getMessage());
//        }
//    }

}
