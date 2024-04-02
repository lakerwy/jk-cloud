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
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.IdsParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAddVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工资系统-其他费用扣加控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:23
 */
@Api(tags = "工资系统-其他费用扣加控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryAddController {

    @Resource
    private SalaryAddService salaryAddService;

    /**
     * 获取工资系统-其他费用扣加分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-其他费用扣加分页")
//    @SaCheckPermission("/sys/hr/salary/deduct/page")
    @GetMapping("/sys/hr/salary/deduct/page")
    public CommonResult<Page<SalaryAddVo>> page(SalaryAddPageParam salaryAddPageParam) {
        return CommonResult.data(salaryAddService.page(salaryAddPageParam));
    }

    /**
     * 添加工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-其他费用扣加")
    @CommonLog("添加工资系统-其他费用扣加")
//    @SaCheckPermission("/sys/hr/salary/deduct/add")
    @PostMapping("/sys/hr/salary/deduct/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryAddAddParam salaryAddAddParam) {
        salaryAddService.add(salaryAddAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-其他费用扣加")
    @CommonLog("编辑工资系统-其他费用扣加")
//    @SaCheckPermission("/sys/hr/salary/deduct/edit")
    @PostMapping("/sys/hr/salary/deduct/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryAddEditParam salaryAddEditParam) {
        salaryAddService.edit(salaryAddEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-其他费用扣加")
    @CommonLog("删除工资系统-其他费用扣加")
//    @SaCheckPermission("/sys/hr/salary/deduct/delete")
    @PostMapping("/sys/hr/salary/deduct/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryAddIdParam> salaryAddIdParamList) {
        salaryAddService.delete(salaryAddIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-其他费用扣加详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-其他费用扣加详情")
//    @SaCheckPermission("/sys/hr/salary/deduct/detail")
    @GetMapping("/sys/hr/salary/deduct/detail")
    public CommonResult<SalaryAddVo> detail(@Valid SalaryAddIdParam salaryAddIdParam) {
        return CommonResult.data(salaryAddService.detail(salaryAddIdParam));
    }


    /**
     * 获取工资系统-其他费用扣加 - 【复制上月（可指定复制）-为空，复制全部】
     *
     * @author yong.ye
     * @date  2024/04/26 09:36
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取工资系统-其他费用扣加 - 【复制上月（可指定复制）-为空，复制全部】")
    @CommonLog("获取工资系统-其他费用扣加 - 【复制上月（可指定复制）-为空，复制全部】")
    @PostMapping("/sys/hr/salary/deduct/copy")
    public CommonResult<String> copy(@RequestBody @Valid IdsParam idsParam) {
        salaryAddService.copy(idsParam);
        return CommonResult.ok();
    }
}
