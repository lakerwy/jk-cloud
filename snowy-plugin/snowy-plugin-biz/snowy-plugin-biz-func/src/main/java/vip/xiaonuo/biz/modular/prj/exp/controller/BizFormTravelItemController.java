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
//import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelItem;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemAddParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemEditParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemIdParam;
//import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemPageParam;
//import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelItemService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 费用报销（二期）- 差旅费用报销单 - 报销明细控制器
// *
// * @author yong.ye
// * @date  2025/02/24 15:19
// */
//@Api(tags = "费用报销（二期）- 差旅费用报销单 - 报销明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class BizFormTravelItemController {
//
//    @Resource
//    private BizFormTravelItemService bizFormTravelItemService;
//
//    /**
//     * 获取费用报销（二期）- 差旅费用报销单 - 报销明细分页
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取费用报销（二期）- 差旅费用报销单 - 报销明细分页")
////    @SaCheckPermission("/biz/prj/exp/travel/item/page")
//    @GetMapping("/biz/prj/exp/travel/item/page")
//    public CommonResult<Page<BizFormTravelItem>> page(BizFormTravelItemPageParam bizFormTravelItemPageParam) {
//        return CommonResult.data(bizFormTravelItemService.page(bizFormTravelItemPageParam));
//    }
//
//    /**
//     * 添加费用报销（二期）- 差旅费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加费用报销（二期）- 差旅费用报销单 - 报销明细")
//    @CommonLog("添加费用报销（二期）- 差旅费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/travel/item/add")
//    @PostMapping("/biz/prj/exp/travel/item/add")
//    public CommonResult<String> add(@RequestBody @Valid BizFormTravelItemAddParam bizFormTravelItemAddParam) {
//        bizFormTravelItemService.add(bizFormTravelItemAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑费用报销（二期）- 差旅费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑费用报销（二期）- 差旅费用报销单 - 报销明细")
//    @CommonLog("编辑费用报销（二期）- 差旅费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/travel/item/edit")
//    @PostMapping("/biz/prj/exp/travel/item/edit")
//    public CommonResult<String> edit(@RequestBody @Valid BizFormTravelItemEditParam bizFormTravelItemEditParam) {
//        bizFormTravelItemService.edit(bizFormTravelItemEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除费用报销（二期）- 差旅费用报销单 - 报销明细
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除费用报销（二期）- 差旅费用报销单 - 报销明细")
//    @CommonLog("删除费用报销（二期）- 差旅费用报销单 - 报销明细")
////    @SaCheckPermission("/biz/prj/exp/travel/item/delete")
//    @PostMapping("/biz/prj/exp/travel/item/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<BizFormTravelItemIdParam> bizFormTravelItemIdParamList) {
//        bizFormTravelItemService.delete(bizFormTravelItemIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取费用报销（二期）- 差旅费用报销单 - 报销明细详情
//     *
//     * @author yong.ye
//     * @date  2025/02/24 15:19
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取费用报销（二期）- 差旅费用报销单 - 报销明细详情")
////    @SaCheckPermission("/biz/prj/exp/travel/item/detail")
//    @GetMapping("/biz/prj/exp/travel/item/detail")
//    public CommonResult<BizFormTravelItem> detail(@Valid BizFormTravelItemIdParam bizFormTravelItemIdParam) {
//        return CommonResult.data(bizFormTravelItemService.detail(bizFormTravelItemIdParam));
//    }
//}
