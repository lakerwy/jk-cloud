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
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryGjjService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工资系统-公积金明细控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:28
 */
@Api(tags = "工资系统-公积金明细控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryGjjController {

    @Resource
    private SalaryGjjService salaryGjjService;

    /**
     * 获取工资系统-公积金明细分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:28
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-公积金明细分页")
//    @SaCheckPermission("/sys/hr/salary/gjj/page")
    @GetMapping("/sys/hr/salary/gjj/page")
    public CommonResult<Page<SalaryGjj>> page(SalaryGjjPageParam salaryGjjPageParam) {
        return CommonResult.data(salaryGjjService.page(salaryGjjPageParam));
    }

    /**
     * 添加工资系统-公积金明细
     *
     * @author yong.ye
     * @date  2024/02/26 09:28
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-公积金明细")
    @CommonLog("添加工资系统-公积金明细")
//    @SaCheckPermission("/sys/hr/salary/gjj/add")
    @PostMapping("/sys/hr/salary/gjj/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryGjjAddParam salaryGjjAddParam) {
        salaryGjjService.add(salaryGjjAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-公积金明细
     *
     * @author yong.ye
     * @date  2024/02/26 09:28
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-公积金明细")
    @CommonLog("编辑工资系统-公积金明细")
//    @SaCheckPermission("/sys/hr/salary/gjj/edit")
    @PostMapping("/sys/hr/salary/gjj/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryGjjEditParam salaryGjjEditParam) {
        salaryGjjService.edit(salaryGjjEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-公积金明细
     *
     * @author yong.ye
     * @date  2024/02/26 09:28
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-公积金明细")
    @CommonLog("删除工资系统-公积金明细")
//    @SaCheckPermission("/sys/hr/salary/gjj/delete")
    @PostMapping("/sys/hr/salary/gjj/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryGjjIdParam> salaryGjjIdParamList) {
        salaryGjjService.delete(salaryGjjIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-公积金明细详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:28
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-公积金明细详情")
//    @SaCheckPermission("/sys/hr/salary/gjj/detail")
    @GetMapping("/sys/hr/salary/gjj/detail")
    public CommonResult<SalaryGjj> detail(@Valid SalaryGjjIdParam salaryGjjIdParam) {
        return CommonResult.data(salaryGjjService.detail(salaryGjjIdParam));
    }
}
