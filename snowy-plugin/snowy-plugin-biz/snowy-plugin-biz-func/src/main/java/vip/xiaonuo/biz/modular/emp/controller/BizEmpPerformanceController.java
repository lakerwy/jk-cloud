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
package vip.xiaonuo.biz.modular.emp.controller;

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
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpPerformanceVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-员工绩效考核-季度评分审批流程控制器
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 */
@Api(tags = "业务表-员工绩效考核-季度评分审批流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizEmpPerformanceController {

    @Resource
    private BizEmpPerformanceService bizEmpPerformanceService;

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-员工绩效考核-季度评分审批流程分页")
//    @SaCheckPermission("/biz/emp/performance/page")
    @GetMapping("/biz/emp/performance/page")
    public CommonResult<Page<BizEmpPerformance>> page(BizEmpPerformancePageParam bizEmpPerformancePageParam) {
        return CommonResult.data(bizEmpPerformanceService.page(bizEmpPerformancePageParam));
    }

    /**
     * 添加业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-员工绩效考核-季度评分审批流程")
    @CommonLog("添加业务表-员工绩效考核-季度评分审批流程")
//    @SaCheckPermission("/biz/emp/performance/add")
    @PostMapping("/biz/emp/performance/add")
    public CommonResult<String> add(@RequestBody @Valid BizEmpPerformanceAddParam bizEmpPerformanceAddParam) {
        bizEmpPerformanceService.add(bizEmpPerformanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-员工绩效考核-季度评分审批流程")
    @CommonLog("编辑业务表-员工绩效考核-季度评分审批流程")
//    @SaCheckPermission("/biz/emp/performance/edit")
    @PostMapping("/biz/emp/performance/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizEmpPerformanceEditParam bizEmpPerformanceEditParam) {
        bizEmpPerformanceService.edit(bizEmpPerformanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-员工绩效考核-季度评分审批流程")
    @CommonLog("删除业务表-员工绩效考核-季度评分审批流程")
//    @SaCheckPermission("/biz/emp/performance/delete")
    @PostMapping("/biz/emp/performance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizEmpPerformanceIdParam> bizEmpPerformanceIdParamList) {
        bizEmpPerformanceService.delete(bizEmpPerformanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-员工绩效考核-季度评分审批流程详情")
//    @SaCheckPermission("/biz/emp/performance/detail")
    @GetMapping("/biz/emp/performance/detail")
    public CommonResult<BizEmpPerformance> detail(@Valid BizEmpPerformanceIdParam bizEmpPerformanceIdParam) {
        return CommonResult.data(bizEmpPerformanceService.detail(bizEmpPerformanceIdParam));
    }


    /**
     * 根据 用户Id+考核年度+考核季度 校验当前员工本年本季度是否已经提交过绩效评估
     *
     * true : 本人本年本季度已提交过绩效评估（审核中+审核通过）
     * false: 未提交过
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("根据 用户Id+考核年度+考核季度 校验当前员工本年本季度是否已经提交过绩效评估")
//    @SaCheckPermission("/biz/emp/task/detail")
    @GetMapping("/biz/emp/performance/check")
    public CommonResult<Boolean> check(@Valid EmpCheckParam empCheckParam) {
        return CommonResult.data(bizEmpPerformanceService.check(empCheckParam));
    }


    /**
     * 获取业务表-员工绩效考核-季度评分审批流程分页(绩效考评统计表)
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-员工绩效考核-季度评分审批流程分页")
//    @SaCheckPermission("/biz/emp/performance/page")
    @GetMapping("/biz/emp/performance/pageList")
    public CommonResult<Page<BizEmpPerformanceVo>> pageList(BizEmpPerformancePageParam bizEmpPerformancePageParam) {
        return CommonResult.data(bizEmpPerformanceService.pageList(bizEmpPerformancePageParam));
    }

}
