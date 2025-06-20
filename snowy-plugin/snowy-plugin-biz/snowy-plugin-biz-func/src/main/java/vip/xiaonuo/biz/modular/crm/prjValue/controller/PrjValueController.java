//package vip.xiaonuo.biz.modular.crm.prjValue.controller;
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
//import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValue;
//import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAddParam;
//import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueEditParam;
//import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueIdParam;
//import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValuePageParam;
//import vip.xiaonuo.biz.modular.crm.prjValue.service.PrjValueService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 生产院及子公司产值控制器
// *
// * @author pzh
// * @date  2025/06/05 10:04
// */
//@Api(tags = "生产院及子公司产值控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class PrjValueController {
//
//    @Resource
//    private PrjValueService prjValueService;
//
//    /**
//     * 获取生产院及子公司产值分页
//     *
//     * @author pzh
//     * @date  2025/06/05 10:04
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取生产院及子公司产值分页")
//    // @SaCheckPermission("/biz/crm/prjValue/page")
//    @GetMapping("/biz/crm/prjValue/page")
//    public CommonResult<Page<PrjValue>> page(PrjValuePageParam prjValuePageParam) {
//        return CommonResult.data(prjValueService.page(prjValuePageParam));
//    }
//
//    /**
//     * 添加生产院及子公司产值
//     *
//     * @author pzh
//     * @date  2025/06/05 10:04
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加生产院及子公司产值")
//    @CommonLog("添加生产院及子公司产值")
//    // @SaCheckPermission("/biz/crm/prjValue/add")
//    @PostMapping("/biz/crm/prjValue/add")
//    public CommonResult<String> add(@RequestBody @Valid PrjValueAddParam prjValueAddParam) {
//        prjValueService.add(prjValueAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑生产院及子公司产值
//     *
//     * @author pzh
//     * @date  2025/06/05 10:04
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑生产院及子公司产值")
//    @CommonLog("编辑生产院及子公司产值")
//    // @SaCheckPermission("/biz/crm/prjValue/edit")
//    @PostMapping("/biz/crm/prjValue/edit")
//    public CommonResult<String> edit(@RequestBody @Valid PrjValueEditParam prjValueEditParam) {
//        prjValueService.edit(prjValueEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除生产院及子公司产值
//     *
//     * @author pzh
//     * @date  2025/06/05 10:04
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除生产院及子公司产值")
//    @CommonLog("删除生产院及子公司产值")
//    // @SaCheckPermission("/biz/crm/prjValue/delete")
//    @PostMapping("/biz/crm/prjValue/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<PrjValueIdParam> prjValueIdParamList) {
//        prjValueService.delete(prjValueIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取生产院及子公司产值详情
//     *
//     * @author pzh
//     * @date  2025/06/05 10:04
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取生产院及子公司产值详情")
//    // @SaCheckPermission("/biz/crm/prjValue/detail")
//    @GetMapping("/biz/crm/prjValue/detail")
//    public CommonResult<PrjValue> detail(@Valid PrjValueIdParam prjValueIdParam) {
//        return CommonResult.data(prjValueService.detail(prjValueIdParam));
//    }
//}
