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
package vip.xiaonuo.sys.modular.hr.sub.controller;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubSalary;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubSalaryService;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubSalaryVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * HR-分公司-工资条控制器
 *
 * @author yong.ye
 * @date  2023/11/22 11:20
 */
@Api(tags = "HR-分公司-工资条控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubSalaryController {

    @Resource
    private SubSalaryService subSalaryService;

    /**
     * 获取HR-分公司-工资条分页[有权限控制]
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取HR-分公司-工资条分页【有权限控制】")
    @SaCheckPermission("/sys/hr/sub/salary/page")
    @GetMapping("/sys/hr/sub/salary/page")
    public CommonResult<Page<SubSalary>> page(SubSalaryPageParam subSalaryPageParam) {
        return CommonResult.data(subSalaryService.page(subSalaryPageParam));
    }

    /**
     * 添加HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加HR-分公司-工资条")
    @CommonLog("添加HR-分公司-工资条")
//    @SaCheckPermission("/sys/hr/sub/salary/add")
    @PostMapping("/sys/hr/sub/salary/add")
    public CommonResult<String> add(@RequestBody @Valid SubSalaryAddParam subSalaryAddParam) {
        subSalaryService.add(subSalaryAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑HR-分公司-工资条")
    @CommonLog("编辑HR-分公司-工资条")
//    @SaCheckPermission("/sys/hr/sub/salary/edit")
    @PostMapping("/sys/hr/sub/salary/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubSalaryEditParam subSalaryEditParam) {
        subSalaryService.edit(subSalaryEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除HR-分公司-工资条")
    @CommonLog("删除HR-分公司-工资条")
//    @SaCheckPermission("/sys/hr/sub/salary/delete")
    @PostMapping("/sys/hr/sub/salary/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubSalaryIdParam> subSalaryIdParamList) {
        subSalaryService.delete(subSalaryIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取HR-分公司-工资条详情
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取HR-分公司-工资条详情")
//    @SaCheckPermission("/sys/hr/sub/salary/detail")
    @GetMapping("/sys/hr/sub/salary/detail")
    public CommonResult<SubSalary> detail(@Valid SubSalaryIdParam subSalaryIdParam) {
        return CommonResult.data(subSalaryService.detail(subSalaryIdParam));
    }

    /**
     * 获取HR-分公司-工资条分页[所有分公司范围是否填报工资单]
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取HR-分公司-工资条分页[所有分公司范围是否填报工资单]")
    @SaCheckPermission("/sys/hr/sub/salary/pageList")
    @GetMapping("/sys/hr/sub/salary/pageList")
    public CommonResult<Page<SubSalaryVo>> pageList(SubSalaryPageParam subSalaryPageParam) {
        return CommonResult.data(subSalaryService.pageList(subSalaryPageParam));
    }
}
