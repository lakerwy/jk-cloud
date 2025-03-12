//package vip.xiaonuo.sys.modular.hr.sub.controller;
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
//import vip.xiaonuo.sys.modular.hr.sub.entity.SubFilingItem;
//import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemAddParam;
//import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemEditParam;
//import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemIdParam;
//import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemPageParam;
//import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingItemService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 分包单位备案明细控制器
// *
// * @author pzh
// * @date  2025/03/11 14:32
// */
//@Api(tags = "分包单位备案明细控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class SubFilingItemController {
//
//    @Resource
//    private SubFilingItemService subFilingItemService;
//
//    /**
//     * 获取分包单位备案明细分页
//     *
//     * @author pzh
//     * @date  2025/03/11 14:32
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取分包单位备案明细分页")
//    // @SaCheckPermission("/sys/hr/sub/filing/page")
//    @GetMapping("/sys/hr/sub/filing/page")
//    public CommonResult<Page<SubFilingItem>> page(SubFilingItemPageParam subFilingItemPageParam) {
//        return CommonResult.data(subFilingItemService.page(subFilingItemPageParam));
//    }
//
//    /**
//     * 添加分包单位备案明细
//     *
//     * @author pzh
//     * @date  2025/03/11 14:32
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加分包单位备案明细")
//    @CommonLog("添加分包单位备案明细")
//    // @SaCheckPermission("/sys/hr/sub/filing/add")
//    @PostMapping("/sys/hr/sub/filingitem/add")
//    public CommonResult<String> add(@RequestBody @Valid SubFilingItemAddParam subFilingItemAddParam) {
//        subFilingItemService.add(subFilingItemAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑分包单位备案明细
//     *
//     * @author pzh
//     * @date  2025/03/11 14:32
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑分包单位备案明细")
//    @CommonLog("编辑分包单位备案明细")
//    // @SaCheckPermission("/sys/hr/sub/filingitem/edit")
//    @PostMapping("/sys/hr/sub/filingitem/edit")
//    public CommonResult<String> edit(@RequestBody @Valid SubFilingItemEditParam subFilingItemEditParam) {
//        subFilingItemService.edit(subFilingItemEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除分包单位备案明细
//     *
//     * @author pzh
//     * @date  2025/03/11 14:32
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除分包单位备案明细")
//    @CommonLog("删除分包单位备案明细")
//    // @SaCheckPermission("/sys/hr/sub/filingitem/delete")
//    @PostMapping("/sys/hr/sub/filingitem/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<SubFilingItemIdParam> subFilingItemIdParamList) {
//        subFilingItemService.delete(subFilingItemIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取分包单位备案明细详情
//     *
//     * @author pzh
//     * @date  2025/03/11 14:32
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取分包单位备案明细详情")
//    // @SaCheckPermission("/sys/hr/sub/filingitem/detail")
//    @GetMapping("/sys/hr/sub/filingitem/detail")
//    public CommonResult<SubFilingItem> detail(@Valid SubFilingItemIdParam subFilingItemIdParam) {
//        return CommonResult.data(subFilingItemService.detail(subFilingItemIdParam));
//    }
//}
