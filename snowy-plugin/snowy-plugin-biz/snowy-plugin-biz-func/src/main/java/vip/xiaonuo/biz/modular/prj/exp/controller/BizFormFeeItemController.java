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
//import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeItem;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemAddParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemEditParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemIdParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemPageParam;
//import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeItemService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 费用报销（二期）- 费用报销单 - 报销明细控制器
// *
// * @author yong.ye
// * @date  2025/02/24 15:16
// */
//@Api(tags = "费用报销（二期）- 费用报销单 - 报销明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class BizFormFeeItemController {
//
//    @Resource
//    private BizFormFeeItemService bizFormFeeItemService;
//
//    /**
//     * 获取费用报销（二期）- 费用报销单 - 报销明细分页
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:16
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取费用报销（二期）- 费用报销单 - 报销明细分页")
////    @SaCheckPermission("/biz/prj/exp/fee/item/page")
//    @GetMapping("/biz/prj/exp/fee/item/page")
//    public CommonResult<Page<BizFormFeeItem>> page(BizFormFeeItemPageParam bizFormFeeItemPageParam) {
//        return CommonResult.data(bizFormFeeItemService.page(bizFormFeeItemPageParam));
//    }
//
//    /**
//     * 添加费用报销（二期）- 费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:16
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加费用报销（二期）- 费用报销单 - 报销明细")
//    @CommonLog("添加费用报销（二期）- 费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/fee/item/add")
//    @PostMapping("/biz/prj/exp/fee/item/add")
//    public CommonResult<String> add(@RequestBody @Valid BizFormFeeItemAddParam bizFormFeeItemAddParam) {
//        bizFormFeeItemService.add(bizFormFeeItemAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑费用报销（二期）- 费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:16
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑费用报销（二期）- 费用报销单 - 报销明细")
//    @CommonLog("编辑费用报销（二期）- 费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/fee/item/edit")
//    @PostMapping("/biz/prj/exp/fee/item/edit")
//    public CommonResult<String> edit(@RequestBody @Valid BizFormFeeItemEditParam bizFormFeeItemEditParam) {
//        bizFormFeeItemService.edit(bizFormFeeItemEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除费用报销（二期）- 费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:16
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除费用报销（二期）- 费用报销单 - 报销明细")
//    @CommonLog("删除费用报销（二期）- 费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/fee/item/delete")
//    @PostMapping("/biz/prj/exp/fee/item/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<BizFormFeeItemIdParam> bizFormFeeItemIdParamList) {
//        bizFormFeeItemService.delete(bizFormFeeItemIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取费用报销（二期）- 费用报销单 - 报销明细详情
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:16
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取费用报销（二期）- 费用报销单 - 报销明细详情")
////    @SaCheckPermission("/biz/prj/exp/fee/item/detail")
//    @GetMapping("/biz/prj/exp/fee/item/detail")
//    public CommonResult<BizFormFeeItem> detail(@Valid BizFormFeeItemIdParam bizFormFeeItemIdParam) {
//        return CommonResult.data(bizFormFeeItemService.detail(bizFormFeeItemIdParam));
//    }
//}
