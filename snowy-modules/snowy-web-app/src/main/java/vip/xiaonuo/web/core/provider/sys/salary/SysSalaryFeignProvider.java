package vip.xiaonuo.web.core.provider.sys.salary;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.sys.feign.SysSalaryFeign;
import vip.xiaonuo.sys.modular.hr.salary.provider.SysSalaryApiProvider;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SysSalaryFeignProvider implements SysSalaryFeign {

    private final SysSalaryApiProvider sysSalaryApiProvider;

    /**
     * 计算 工龄工资
     * @return
     */
    @Override
    @RequestMapping("/feign/sys/salary/computeSeniorityGZ")
    public void computeSeniorityGZ() {
        sysSalaryApiProvider.computeSeniorityGZ();
    }

    /**
     * 计算 生日福利
     * @return
     */
    @Override
    @RequestMapping("/feign/sys/salary/computeBirthdayGZ")
    public void computeBirthdayGZ() {
        sysSalaryApiProvider.computeBirthdayGZ();
    }
}
