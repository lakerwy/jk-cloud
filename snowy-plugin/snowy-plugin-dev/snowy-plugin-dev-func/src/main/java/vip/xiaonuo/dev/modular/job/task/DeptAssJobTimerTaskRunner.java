package vip.xiaonuo.dev.modular.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.modular.job.service.BizTaskJobService;

import javax.annotation.Resource;

/**
 * @author yong.ye
 * @date 2023/8/10 11:21
 */

@Slf4j
@Component
public class DeptAssJobTimerTaskRunner implements CommonTimerTaskRunner {

    @Resource
    private BizTaskJobService bizTaskJobService;
    @Override
    public void action() {
        try {
            // 本定时任务用于出来 部门绩效考核 - 部门任务分 - 进度分 - 进度率 自动计算
            bizTaskJobService.autoUpdateProgressRate();
            log.info("部门绩效考核-进度分系数-自动计算......");
        }catch (Exception e)
        {
            log.info("部门绩效考核-进度分系数-自动计算......"+e.getMessage());
        }
    }
}
