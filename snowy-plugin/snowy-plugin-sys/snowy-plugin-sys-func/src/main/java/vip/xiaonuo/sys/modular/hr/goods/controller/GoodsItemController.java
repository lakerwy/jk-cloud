//package vip.xiaonuo.sys.modular.hr.goods.controller;
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
//import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsItem;
//import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemAddParam;
//import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemEditParam;
//import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemIdParam;
//import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemPageParam;
//import vip.xiaonuo.sys.modular.hr.goods.service.GoodsItemService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 物资领用申请（附表） - 物资清单+退回清单 控制器
// *
// * @author yy
// * @date  2025/05/23 14:35
// */
//@Api(tags = "物资领用申请（附表） - 物资清单+退回清单 控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class GoodsItemController {
//
//    @Resource
//    private GoodsItemService goodsItemService;
//
//    /**
//     * 获取物资领用申请（附表） - 物资清单+退回清单 分页
//     *
//     * @author yy
//     * @date  2025/05/23 14:35
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取物资领用申请（附表） - 物资清单+退回清单 分页")
////    @SaCheckPermission("/sys/hr/goods/item/page")
//    @GetMapping("/sys/hr/goods/item/page")
//    public CommonResult<Page<GoodsItem>> page(GoodsItemPageParam goodsItemPageParam) {
//        return CommonResult.data(goodsItemService.page(goodsItemPageParam));
//    }
//
//    /**
//     * 添加物资领用申请（附表） - 物资清单+退回清单
//     *
//     * @author yy
//     * @date  2025/05/23 14:35
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加物资领用申请（附表） - 物资清单+退回清单 ")
//    @CommonLog("添加物资领用申请（附表） - 物资清单+退回清单 ")
////    @SaCheckPermission("/sys/hr/goods/item/add")
//    @PostMapping("/sys/hr/goods/item/add")
//    public CommonResult<String> add(@RequestBody @Valid GoodsItemAddParam goodsItemAddParam) {
//        goodsItemService.add(goodsItemAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑物资领用申请（附表） - 物资清单+退回清单
//     *
//     * @author yy
//     * @date  2025/05/23 14:35
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑物资领用申请（附表） - 物资清单+退回清单 ")
//    @CommonLog("编辑物资领用申请（附表） - 物资清单+退回清单 ")
////    @SaCheckPermission("/sys/hr/goods/item/edit")
//    @PostMapping("/sys/hr/goods/item/edit")
//    public CommonResult<String> edit(@RequestBody @Valid GoodsItemEditParam goodsItemEditParam) {
//        goodsItemService.edit(goodsItemEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除物资领用申请（附表） - 物资清单+退回清单
//     *
//     * @author yy
//     * @date  2025/05/23 14:35
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除物资领用申请（附表） - 物资清单+退回清单 ")
//    @CommonLog("删除物资领用申请（附表） - 物资清单+退回清单 ")
////    @SaCheckPermission("/sys/hr/goods/item/delete")
//    @PostMapping("/sys/hr/goods/item/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<GoodsItemIdParam> goodsItemIdParamList) {
//        goodsItemService.delete(goodsItemIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取物资领用申请（附表） - 物资清单+退回清单 详情
//     *
//     * @author yy
//     * @date  2025/05/23 14:35
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取物资领用申请（附表） - 物资清单+退回清单 详情")
////    @SaCheckPermission("/sys/hr/goods/item/detail")
//    @GetMapping("/sys/hr/goods/item/detail")
//    public CommonResult<GoodsItem> detail(@Valid GoodsItemIdParam goodsItemIdParam) {
//        return CommonResult.data(goodsItemService.detail(goodsItemIdParam));
//    }
//}
