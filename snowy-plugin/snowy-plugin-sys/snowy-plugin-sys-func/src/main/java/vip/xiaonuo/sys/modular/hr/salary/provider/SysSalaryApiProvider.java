package vip.xiaonuo.sys.modular.hr.salary.provider;

import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.SysSalaryApi;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryPayService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * 工资系统 对外暴露接口 实现方法
 */

@Service
public class SysSalaryApiProvider implements SysSalaryApi {

    @Resource
    private SalaryPayService salaryPayService;

    @Override
    public void computeSeniorityGZ() {
        //获取当前年月  格式化当前日期的年月部分
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        salaryPayService.computeSeniorityGZ(yearMonth);
    }

    @Override
    public void computeBirthdayGZ() {
        //获取当前年月  格式化当前日期的年月部分
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        salaryPayService.computeBirthdayGZ(yearMonth);
    }
}
