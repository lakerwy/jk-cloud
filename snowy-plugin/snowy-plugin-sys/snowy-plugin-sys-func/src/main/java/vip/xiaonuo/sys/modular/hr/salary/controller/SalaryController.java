/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.salary.entity.Salary;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.TotalParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.UpdateBatch;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.UpdateFieldParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumSalaryTotal;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 【封存】工资系统-工资表控制器
 *
 * @author yong.ye
 * @date  2024/03/01 16:50
 */
@Api(tags = "工资系统-工资表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    /**
     * 【已封存的工资数据】获取工资系统-工资表分页
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("【已封存的工资数据】获取工资系统-工资表分页")
    @GetMapping("/sys/hr/salary/page")
    public CommonResult<Page<Salary>> page(SalaryPageParam salaryPageParam) {
        return CommonResult.data(salaryService.page(salaryPageParam));
    }

    /**
     * 【已封存的工资数据】获取工资系统-工资表详情
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("【已封存的工资数据】获取工资系统-工资表详情")
    @GetMapping("/sys/hr/salary/detail")
    public CommonResult<Salary> detail(@Valid SalaryIdParam salaryIdParam) {
        return CommonResult.data(salaryService.detail(salaryIdParam));
    }

    /**
     *  【已封存的工资数据】获取员工工资条-缓发状态不可查
     *
     * @author yong.ye
     * @date  2024/03/05 09:36
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("【已封存的工资数据】获取员工工资条-缓发状态不可查")
    @GetMapping("/sys/hr/salary/empSalary")
    public CommonResult<Salary> empSalary(SearchParam param) {
        return CommonResult.data(salaryService.empSalary(param));
    }


    /**
     * 【已封存的工资数据】 - 修改是否缓发状态
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("【已封存的工资数据】 - 修改是否缓发状态")
    @CommonLog("【已封存的工资数据】 - 修改是否缓发状态")
    @PostMapping("/sys/hr/salary/updateField")
    public CommonResult<String> updateField(@RequestBody @Valid UpdateFieldParam fieldParam) {
        salaryService.updateField(fieldParam);
        return CommonResult.ok();
    }


    /**
     * 批量更新-是否缓发
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("批量更新-是否缓发")
    @CommonLog("批量更新-是否缓发")
    @PostMapping("/sys/hr/salary/updateIsSlow")
    public CommonResult<String> updateIsSlow(@RequestBody @Valid UpdateBatch updateBatch) {
        salaryService.updateIsSlow(updateBatch);
        return CommonResult.ok();
    }


    /**
     *  【工资相关-统计值】根据类型，获取不同列表统计值（所有数值总额）
     *
     * @author yong.ye
     * @date  2024/03/05 09:36
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("【工资相关-统计值】根据类型，获取不同列表统计值（所有数值总额）")
    @GetMapping("/sys/hr/salary/total")
    public CommonResult<SumSalaryTotal> total(TotalParam param) {
        return CommonResult.data(salaryService.total(param));
    }

}
