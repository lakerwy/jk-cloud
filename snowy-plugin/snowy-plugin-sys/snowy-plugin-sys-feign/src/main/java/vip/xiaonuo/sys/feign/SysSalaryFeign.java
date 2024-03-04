package vip.xiaonuo.sys.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.xiaonuo.common.consts.FeignConstant;

@FeignClient(name= FeignConstant.WEB_APP, contextId = "SysSalaryFeign")
public interface SysSalaryFeign {

    /**
     * 计算 工龄工资
     * @return
     */
    @RequestMapping("/feign/sys/salary/computeSeniorityGZ")
    void computeSeniorityGZ();

    /**
     * 计算 生日福利
     * @return
     */
    @RequestMapping("/feign/sys/salary/computeBirthdayGZ")
    void computeBirthdayGZ();

}
