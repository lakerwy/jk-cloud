//package vip.xiaonuo.biz.modular.prj.por.controller;
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
//import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFeeItem;
//import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemAddParam;
//import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemEditParam;
//import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemIdParam;
//import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemPageParam;
//import vip.xiaonuo.biz.modular.prj.por.service.PorSiteFeeItemService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细控制器
// *
// * @author yy
// * @date  2025/06/03 09:47
// */
//@Api(tags = "项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class PorSiteFeeItemController {
//
//    @Resource
//    private PorSiteFeeItemService porSiteFeeItemService;
//
//    /**
//     * 获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细分页
//     *
//     * @author yy
//     * @date  2025/06/03 09:47
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细分页")
////    @SaCheckPermission("/biz/prj/por/site/fee/item/page")
//    @GetMapping("/biz/prj/por/site/fee/item/page")
//    public CommonResult<Page<PorSiteFeeItem>> page(PorSiteFeeItemPageParam porSiteFeeItemPageParam) {
//        return CommonResult.data(porSiteFeeItemService.page(porSiteFeeItemPageParam));
//    }
//
//    /**
//     * 添加项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
//     *
//     * @author yy
//     * @date  2025/06/03 09:47
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
//    @CommonLog("添加项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
////    @SaCheckPermission("/biz/prj/por/site/fee/item/add")
//    @PostMapping("/biz/prj/por/site/fee/item/add")
//    public CommonResult<String> add(@RequestBody @Valid PorSiteFeeItemAddParam porSiteFeeItemAddParam) {
//        porSiteFeeItemService.add(porSiteFeeItemAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
//     *
//     * @author yy
//     * @date  2025/06/03 09:47
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
//    @CommonLog("编辑项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
////    @SaCheckPermission("/biz/prj/por/site/fee/item/edit")
//    @PostMapping("/biz/prj/por/site/fee/item/edit")
//    public CommonResult<String> edit(@RequestBody @Valid PorSiteFeeItemEditParam porSiteFeeItemEditParam) {
//        porSiteFeeItemService.edit(porSiteFeeItemEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
//     *
//     * @author yy
//     * @date  2025/06/03 09:47
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
//    @CommonLog("删除项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细")
////    @SaCheckPermission("/biz/prj/por/site/fee/item/delete")
//    @PostMapping("/biz/prj/por/site/fee/item/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<PorSiteFeeItemIdParam> porSiteFeeItemIdParamList) {
//        porSiteFeeItemService.delete(porSiteFeeItemIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细详情
//     *
//     * @author yy
//     * @date  2025/06/03 09:47
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细详情")
////    @SaCheckPermission("/biz/prj/por/site/fee/item/detail")
//    @GetMapping("/biz/prj/por/site/fee/item/detail")
//    public CommonResult<PorSiteFeeItem> detail(@Valid PorSiteFeeItemIdParam porSiteFeeItemIdParam) {
//        return CommonResult.data(porSiteFeeItemService.detail(porSiteFeeItemIdParam));
//    }
//}
