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

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.salary.entity.Salary;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工资系统-工资表控制器
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
     * 获取工资系统-工资表分页
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-工资表分页")
//    @SaCheckPermission("/sys/hr/salary/page")
    @GetMapping("/sys/hr/salary/page")
    public CommonResult<Page<Salary>> page(SalaryPageParam salaryPageParam) {
        return CommonResult.data(salaryService.page(salaryPageParam));
    }


    /**
     * 删除工资系统-工资表
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-工资表")
    @CommonLog("删除工资系统-工资表")
//    @SaCheckPermission("/sys/hr/salary/delete")
    @PostMapping("/sys/hr/salary/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryIdParam> salaryIdParamList) {
        salaryService.delete(salaryIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-工资表详情
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-工资表详情")
//    @SaCheckPermission("/sys/hr/salary/detail")
    @GetMapping("/sys/hr/salary/detail")
    public CommonResult<Salary> detail(@Valid SalaryIdParam salaryIdParam) {
        return CommonResult.data(salaryService.detail(salaryIdParam));
    }


    /**
     * 获取工资系统-工资表分页(pageList)
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取工资系统-工资表分页(pageList)")
//    @SaCheckPermission("/sys/hr/salary/pageList")
    @GetMapping("/sys/hr/salary/pageList")
    public CommonResult<Page<Salary>> pageList(SalaryPageParam salaryPageParam) {
        return CommonResult.data(salaryService.pageList(salaryPageParam));
    }
}
