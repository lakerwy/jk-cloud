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
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryTaxService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryTaxVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工资系统-专项扣除控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:38
 */
@Api(tags = "工资系统-专项扣除控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryTaxController {

    @Resource
    private SalaryTaxService salaryTaxService;

    /**
     * 获取工资系统-专项扣除分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:38
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-专项扣除分页")
//    @SaCheckPermission("/sys/hr/salary/tax/page")
    @GetMapping("/sys/hr/salary/tax/page")
    public CommonResult<Page<SalaryTaxVo>> page(SalaryTaxPageParam salaryTaxPageParam) {
        return CommonResult.data(salaryTaxService.page(salaryTaxPageParam));
    }

    /**
     * 添加工资系统-专项扣除
     *
     * @author yong.ye
     * @date  2024/02/26 09:38
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-专项扣除")
    @CommonLog("添加工资系统-专项扣除")
//    @SaCheckPermission("/sys/hr/salary/tax/add")
    @PostMapping("/sys/hr/salary/tax/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryTaxAddParam salaryTaxAddParam) {
        salaryTaxService.add(salaryTaxAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-专项扣除
     *
     * @author yong.ye
     * @date  2024/02/26 09:38
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-专项扣除")
    @CommonLog("编辑工资系统-专项扣除")
//    @SaCheckPermission("/sys/hr/salary/tax/edit")
    @PostMapping("/sys/hr/salary/tax/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryTaxEditParam salaryTaxEditParam) {
        salaryTaxService.edit(salaryTaxEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-专项扣除
     *
     * @author yong.ye
     * @date  2024/02/26 09:38
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-专项扣除")
    @CommonLog("删除工资系统-专项扣除")
//    @SaCheckPermission("/sys/hr/salary/tax/delete")
    @PostMapping("/sys/hr/salary/tax/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryTaxIdParam> salaryTaxIdParamList) {
        salaryTaxService.delete(salaryTaxIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-专项扣除详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:38
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-专项扣除详情")
//    @SaCheckPermission("/sys/hr/salary/tax/detail")
    @GetMapping("/sys/hr/salary/tax/detail")
    public CommonResult<SalaryTaxVo> detail(@Valid SalaryTaxIdParam salaryTaxIdParam) {
        return CommonResult.data(salaryTaxService.detail(salaryTaxIdParam));
    }
}
