package vip.xiaonuo.dev.modular.job.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.sys.api.SysSalaryApi;

import javax.annotation.Resource;

/**
 * 工资系统：工资工资|生日福利
 */
@Slf4j
@Component
public class GZJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    private SysSalaryApi sysSalaryApi;
    @Override
    public void action() {
        try {
            //工龄工资 自动生成（本月）
            sysSalaryApi.computeSeniorityGZ();
            //生日福利 自动生成（本月）
            sysSalaryApi.computeBirthdayGZ();
            log.info("工龄工资|生日福利 自动生成 成功");
        }catch (Exception e){
            log.debug("工龄工资|生日福利 自动生成" + e.getMessage());
        }

    }
}
