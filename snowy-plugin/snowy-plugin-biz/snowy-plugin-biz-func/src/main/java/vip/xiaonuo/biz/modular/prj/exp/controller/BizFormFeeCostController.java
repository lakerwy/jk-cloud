//package vip.xiaonuo.biz.modular.prj.exp.controller;
//
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import com.github.xiaoymin.knife4j.annotations.ApiSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import vip.xiaonuo.common.annotation.CommonLog;
//import vip.xiaonuo.common.pojo.CommonResult;
//import vip.xiaonuo.common.pojo.CommonValidList;
//import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeCost;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostAddParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostEditParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostIdParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostPageParam;
//import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeCostService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 费用报销（二期）- 费用报销单 - 成本明细控制器
// *
// * @author yong.ye
// * @date  2025/02/24 15:15
// */
//@Api(tags = "费用报销（二期）- 费用报销单 - 成本明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class BizFormFeeCostController {
//
//    @Resource
//    private BizFormFeeCostService bizFormFeeCostService;
//
//    /**
//     * 获取费用报销（二期）- 费用报销单 - 成本明细分页
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:15
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取费用报销（二期）- 费用报销单 - 成本明细分页")
////    @SaCheckPermission("/biz/prj/exp/fee/cost/page")
//    @GetMapping("/biz/prj/exp/fee/cost/page")
//    public CommonResult<Page<BizFormFeeCost>> page(BizFormFeeCostPageParam bizFormFeeCostPageParam) {
//        return CommonResult.data(bizFormFeeCostService.page(bizFormFeeCostPageParam));
//    }
//
//    /**
//     * 添加费用报销（二期）- 费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:15
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加费用报销（二期）- 费用报销单 - 成本明细")
//    @CommonLog("添加费用报销（二期）- 费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/fee/cost/add")
//    @PostMapping("/biz/prj/exp/fee/cost/add")
//    public CommonResult<String> add(@RequestBody @Valid BizFormFeeCostAddParam bizFormFeeCostAddParam) {
//        bizFormFeeCostService.add(bizFormFeeCostAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑费用报销（二期）- 费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:15
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑费用报销（二期）- 费用报销单 - 成本明细")
//    @CommonLog("编辑费用报销（二期）- 费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/fee/cost/edit")
//    @PostMapping("/biz/prj/exp/fee/cost/edit")
//    public CommonResult<String> edit(@RequestBody @Valid BizFormFeeCostEditParam bizFormFeeCostEditParam) {
//        bizFormFeeCostService.edit(bizFormFeeCostEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除费用报销（二期）- 费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:15
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除费用报销（二期）- 费用报销单 - 成本明细")
//    @CommonLog("删除费用报销（二期）- 费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/fee/cost/delete")
//    @PostMapping("/biz/prj/exp/fee/cost/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<BizFormFeeCostIdParam> bizFormFeeCostIdParamList) {
//        bizFormFeeCostService.delete(bizFormFeeCostIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取费用报销（二期）- 费用报销单 - 成本明细详情
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:15
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取费用报销（二期）- 费用报销单 - 成本明细详情")
////    @SaCheckPermission("/biz/prj/exp/fee/cost/detail")
//    @GetMapping("/biz/prj/exp/fee/cost/detail")
//    public CommonResult<BizFormFeeCost> detail(@Valid BizFormFeeCostIdParam bizFormFeeCostIdParam) {
//        return CommonResult.data(bizFormFeeCostService.detail(bizFormFeeCostIdParam));
//    }
//}
