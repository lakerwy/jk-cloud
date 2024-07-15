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
import vip.xiaonuo.sys.modular.hr.salary.param.customer.CostReportParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.CostReportSearch;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryCostService;
import vip.xiaonuo.sys.modular.hr.salary.vo.CostReportVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 工资系统 - 成本划分 - 月度个人统计控制器
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 */
@Api(tags = "工资系统 - 成本划分 - 月度个人统计控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryCostController {

    @Resource
    private SalaryCostService salaryCostService;

//    /**
//     * 获取工资系统 - 成本划分 - 月度个人统计分页
//     *
//     * @author yong.ye
//     * @date  2024/06/19 15:06
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取工资系统 - 成本划分 - 月度个人统计分页")
////    @SaCheckPermission("/sys/hr/salary/cost/page")
//    @GetMapping("/sys/hr/salary/cost/page")
//    public CommonResult<Page<SalaryCost>> page(SalaryCostPageParam salaryCostPageParam) {
//        return CommonResult.data(salaryCostService.page(salaryCostPageParam));
//    }
//
//    /**
//     * 添加工资系统 - 成本划分 - 月度个人统计
//     *
//     * @author yong.ye
//     * @date  2024/06/19 15:06
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加工资系统 - 成本划分 - 月度个人统计")
//    @CommonLog("添加工资系统 - 成本划分 - 月度个人统计")
////    @SaCheckPermission("/sys/hr/salary/cost/add")
//    @PostMapping("/sys/hr/salary/cost/add")
//    public CommonResult<String> add(@RequestBody @Valid SalaryCostAddParam salaryCostAddParam) {
//        salaryCostService.add(salaryCostAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑工资系统 - 成本划分 - 月度个人统计
//     *
//     * @author yong.ye
//     * @date  2024/06/19 15:06
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑工资系统 - 成本划分 - 月度个人统计")
//    @CommonLog("编辑工资系统 - 成本划分 - 月度个人统计")
////    @SaCheckPermission("/sys/hr/salary/cost/edit")
//    @PostMapping("/sys/hr/salary/cost/edit")
//    public CommonResult<String> edit(@RequestBody @Valid SalaryCostEditParam salaryCostEditParam) {
//        salaryCostService.edit(salaryCostEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除工资系统 - 成本划分 - 月度个人统计
//     *
//     * @author yong.ye
//     * @date  2024/06/19 15:06
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除工资系统 - 成本划分 - 月度个人统计")
//    @CommonLog("删除工资系统 - 成本划分 - 月度个人统计")
////    @SaCheckPermission("/sys/hr/salary/cost/delete")
//    @PostMapping("/sys/hr/salary/cost/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<SalaryCostIdParam> salaryCostIdParamList) {
//        salaryCostService.delete(salaryCostIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取工资系统 - 成本划分 - 月度个人统计详情
//     *
//     * @author yong.ye
//     * @date  2024/06/19 15:06
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取工资系统 - 成本划分 - 月度个人统计详情")
////    @SaCheckPermission("/sys/hr/salary/cost/detail")
//    @GetMapping("/sys/hr/salary/cost/detail")
//    public CommonResult<SalaryCost> detail(@Valid SalaryCostIdParam salaryCostIdParam) {
//        return CommonResult.data(salaryCostService.detail(salaryCostIdParam));
//    }




    /**
     * 【报表】- 生成某年月-成本划分数据
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("生成某年月-成本划分数据")
    @CommonLog("生成某年月-成本划分数据")
    @PostMapping("/sys/hr/salary/report/genCost")
    public CommonResult<String> genPerCost(@RequestBody @Valid CostReportParam param) {
        salaryCostService.genPerCost(param);
        return CommonResult.ok();
    }

    /**
     * 【报表】查询 社保|公积金|汇总 分析报表
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("查询 社保|公积金|汇总 分析报表")
    @GetMapping("/sys/hr/salary/report/genCostReport")
    public CommonResult<List<CostReportVo>> genCostReport(CostReportSearch param) {
        return CommonResult.data(salaryCostService.genCostReport(param));
    }
}
