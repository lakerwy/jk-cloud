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
//import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelCost;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostAddParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostEditParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostIdParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostPageParam;
//import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelCostService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 费用报销（二期）- 差旅费用报销单 - 成本明细控制器
// *
// * @author yong.ye
// * @date  2025/02/24 15:19
// */
//@Api(tags = "费用报销（二期）- 差旅费用报销单 - 成本明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class BizFormTravelCostController {
//
//    @Resource
//    private BizFormTravelCostService bizFormTravelCostService;
//
//    /**
//     * 获取费用报销（二期）- 差旅费用报销单 - 成本明细分页
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取费用报销（二期）- 差旅费用报销单 - 成本明细分页")
////    @SaCheckPermission("/biz/prj/exp/travel/cost/page")
//    @GetMapping("/biz/prj/exp/travel/cost/page")
//    public CommonResult<Page<BizFormTravelCost>> page(BizFormTravelCostPageParam bizFormTravelCostPageParam) {
//        return CommonResult.data(bizFormTravelCostService.page(bizFormTravelCostPageParam));
//    }
//
//    /**
//     * 添加费用报销（二期）- 差旅费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加费用报销（二期）- 差旅费用报销单 - 成本明细")
//    @CommonLog("添加费用报销（二期）- 差旅费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/travel/cost/add")
//    @PostMapping("/biz/prj/exp/travel/cost/add")
//    public CommonResult<String> add(@RequestBody @Valid BizFormTravelCostAddParam bizFormTravelCostAddParam) {
//        bizFormTravelCostService.add(bizFormTravelCostAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑费用报销（二期）- 差旅费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑费用报销（二期）- 差旅费用报销单 - 成本明细")
//    @CommonLog("编辑费用报销（二期）- 差旅费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/travel/cost/edit")
//    @PostMapping("/biz/prj/exp/travel/cost/edit")
//    public CommonResult<String> edit(@RequestBody @Valid BizFormTravelCostEditParam bizFormTravelCostEditParam) {
//        bizFormTravelCostService.edit(bizFormTravelCostEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除费用报销（二期）- 差旅费用报销单 - 成本明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除费用报销（二期）- 差旅费用报销单 - 成本明细")
//    @CommonLog("删除费用报销（二期）- 差旅费用报销单 - 成本明细")
////    @SaCheckPermission("/biz/prj/exp/travel/cost/delete")
//    @PostMapping("/biz/prj/exp/travel/cost/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<BizFormTravelCostIdParam> bizFormTravelCostIdParamList) {
//        bizFormTravelCostService.delete(bizFormTravelCostIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取费用报销（二期）- 差旅费用报销单 - 成本明细详情
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取费用报销（二期）- 差旅费用报销单 - 成本明细详情")
////    @SaCheckPermission("/biz/prj/exp/travel/cost/detail")
//    @GetMapping("/biz/prj/exp/travel/cost/detail")
//    public CommonResult<BizFormTravelCost> detail(@Valid BizFormTravelCostIdParam bizFormTravelCostIdParam) {
//        return CommonResult.data(bizFormTravelCostService.detail(bizFormTravelCostIdParam));
//    }
//}
