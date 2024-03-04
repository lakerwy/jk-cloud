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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalarySubsidyRecord;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalarySubsidyRecordService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工资系统-各类补贴-明细记录控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:37
 */
@Api(tags = "工资系统-各类补贴-明细记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalarySubsidyRecordController {

    @Resource
    private SalarySubsidyRecordService salarySubsidyRecordService;

    /**
     * 获取工资系统-各类补贴-明细记录分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:37
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-各类补贴-明细记录分页")
//    @SaCheckPermission("/sys/hr/salary/subsidy/page")
    @GetMapping("/sys/hr/salary/subsidy/page")
    public CommonResult<Page<SalarySubsidyRecord>> page(SalarySubsidyRecordPageParam salarySubsidyRecordPageParam) {
        return CommonResult.data(salarySubsidyRecordService.page(salarySubsidyRecordPageParam));
    }

    /**
     * 添加工资系统-各类补贴-明细记录
     *
     * @author yong.ye
     * @date  2024/02/26 09:37
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-各类补贴-明细记录")
    @CommonLog("添加工资系统-各类补贴-明细记录")
//    @SaCheckPermission("/sys/hr/salary/subsidy/add")
    @PostMapping("/sys/hr/salary/subsidy/add")
    public CommonResult<String> add(@RequestBody @Valid SalarySubsidyRecordAddParam salarySubsidyRecordAddParam) {
        salarySubsidyRecordService.add(salarySubsidyRecordAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-各类补贴-明细记录
     *
     * @author yong.ye
     * @date  2024/02/26 09:37
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-各类补贴-明细记录")
    @CommonLog("编辑工资系统-各类补贴-明细记录")
//    @SaCheckPermission("/sys/hr/salary/subsidy/edit")
    @PostMapping("/sys/hr/salary/subsidy/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalarySubsidyRecordEditParam salarySubsidyRecordEditParam) {
        salarySubsidyRecordService.edit(salarySubsidyRecordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-各类补贴-明细记录
     *
     * @author yong.ye
     * @date  2024/02/26 09:37
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-各类补贴-明细记录")
    @CommonLog("删除工资系统-各类补贴-明细记录")
//    @SaCheckPermission("/sys/hr/salary/subsidy/delete")
    @PostMapping("/sys/hr/salary/subsidy/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalarySubsidyRecordIdParam> salarySubsidyRecordIdParamList) {
        salarySubsidyRecordService.delete(salarySubsidyRecordIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-各类补贴-明细记录详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:37
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-各类补贴-明细记录详情")
//    @SaCheckPermission("/sys/hr/salary/subsidy/detail")
    @GetMapping("/sys/hr/salary/subsidy/detail")
    public CommonResult<SalarySubsidyRecord> detail(@Valid SalarySubsidyRecordIdParam salarySubsidyRecordIdParam) {
        return CommonResult.data(salarySubsidyRecordService.detail(salarySubsidyRecordIdParam));
    }


    /**
     * 工资系统-补贴 - 【复制上月注册补贴数据】
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("工资系统-补贴 - 【复制上月注册补贴数据】")
    @CommonLog("工资系统-补贴 - 【复制上月注册补贴数据】")
    @PostMapping("/sys/hr/salary/subsidy/copy")
    public CommonResult<String> copy() {
        salarySubsidyRecordService.copy();
        return CommonResult.ok();
    }
}
